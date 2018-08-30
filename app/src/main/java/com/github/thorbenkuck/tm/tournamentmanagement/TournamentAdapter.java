package com.github.thorbenkuck.tm.tournamentmanagement;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.thorbenkuck.tm.shared.TournamentInformation;

import java.util.Arrays;
import java.util.List;

public class TournamentAdapter extends RecyclerView.Adapter<TournamentAdapter.TournamentAdapterViewHolder> {

    private final List<TournamentInformation> tournaments;

    private final Fragment fragment;

    private int selected_position;

    public TournamentAdapter(Fragment fragment, List<TournamentInformation> tournaments) {
        this.fragment = fragment;
        this.tournaments = tournaments;
    }

    @NonNull
    @Override
    public TournamentAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.tournament_list_row, viewGroup, false);

        return new TournamentAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TournamentAdapterViewHolder tournamentAdapterViewHolder, int i) {
        TournamentInformation tournamentInformation = tournaments.get(i);
        tournamentAdapterViewHolder.itemView.setTag(tournamentInformation);
        tournamentAdapterViewHolder.name.setText(tournamentInformation.getName());
        tournamentAdapterViewHolder.owner.setText(tournamentInformation.getOwnerUserName());
        tournamentAdapterViewHolder.participants.setText(tournamentInformation.getParticipants() + "");
        tournamentAdapterViewHolder.itemView.setBackgroundColor(i == selected_position ? Color.parseColor("#eeeeee") : Color.TRANSPARENT);
    }

    @Override
    public int getItemCount() {
        return tournaments.size();
    }

    public class TournamentAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView name;

        private TextView participants;

        private TextView owner;

        public TournamentAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tournament_name);
            owner = itemView.findViewById(R.id.tournament_owner_name);
            participants = itemView.findViewById(R.id.tournament_participants);

            itemView.setOnClickListener(this);

            itemView.setOnLongClickListener(view -> {
                DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            //Yes button clicked
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getContext());
                builder.setMessage("Möchtest du dem Tournament joinen?").setPositiveButton("Jau", dialogClickListener)
                        .setNegativeButton("NeEeE", dialogClickListener).show();
                return true;
            });
        }

        @Override
        public void onClick(View view) {
            notifyItemChanged(selected_position);
            selected_position = getAdapterPosition();
            notifyItemChanged(selected_position);
        }
    }



}
