package com.github.thorbenkuck.tm.tournamentmanagement.views.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.github.thorbenkuck.tm.shared.TournamentInformation;
import com.github.thorbenkuck.tm.shared.tournament.JoinTournamentResponse;
import com.github.thorbenkuck.tm.shared.tournament.ListTournamentsResponse;
import com.github.thorbenkuck.tm.shared.tournament.TournamentResponse;
import com.github.thorbenkuck.tm.tournamentmanagement.views.MainFragmentView;
import com.github.thorbenkuck.tm.tournamentmanagement.R;
import com.github.thorbenkuck.tm.tournamentmanagement.TournamentAdapter;
import com.github.thorbenkuck.tm.tournamentmanagement.util.Utils;
import com.github.thorbenkuck.tm.tournamentmanagement.databinding.FragmentMainBinding;
import com.github.thorbenkuck.tm.tournamentmanagement.network.Network;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements MainFragmentView {

    private Network network = Network.getInstance();

    private final List<TournamentInformation> tournamentInformationList = new ArrayList<>();

    private TournamentAdapter tournamentAdapter;

    private EditText tournamentNameText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        network.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        network.unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        FragmentMainBinding fragmentMainBinding = DataBindingUtil.bind(view);
        //fragmentMainBinding.setFragment(this);

        tournamentAdapter = new TournamentAdapter(this, tournamentInformationList);

        RecyclerView recyclerView = view.findViewById(R.id.tournaments_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(tournamentAdapter);

        TextInputLayout textInputLayout = view.findViewById(R.id.tournament_name_text);
        tournamentNameText = textInputLayout.getEditText();

        network.requestTournamentList();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onEnterButtonClicked(View view) {
        TournamentInformation tournamentInformation = (TournamentInformation) view.getTag();
        network.joinTournament(tournamentInformation.getName());
        Toast.makeText(getContext(), "Es hat geklappt", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreateButtonClicked(View view) {
        String newName = tournamentNameText.getText().toString();
        network.requestNewTournament(newName);
    }

    @Subscribe
    public void onTournamentCreateResponse(TournamentResponse tournamentResponse) {
        if(tournamentResponse.isSuccessful()) {
            Utils.onUI(() -> {
                Toast.makeText(getContext(),"Tournament " + tournamentResponse.getName() , Toast.LENGTH_LONG).show();
                tournamentNameText.setText("");
            });
        } else {
            Utils.onUI(() -> {
                Toast.makeText(getContext(),"Tournament " + tournamentResponse.getName() + " konnte nicht erstellt werden.", Toast.LENGTH_LONG).show();
            });
        }
    }

    @Subscribe
    public void onPopulate(ListTournamentsResponse listTournamentsResponse) {
        Utils.onUI(() -> {
            tournamentInformationList.clear();
            tournamentInformationList.addAll(listTournamentsResponse.getList());
            tournamentAdapter.notifyDataSetChanged();
        });
    }

    @Subscribe
    public void onJoin(JoinTournamentResponse joinTournamentResponse) {
        if(joinTournamentResponse.isSuccessful()) {
            //TODO
        } else {
            Utils.onUI(() -> {
                Toast.makeText(getContext(), "Da ist wohl was schief gelaufen", Toast.LENGTH_LONG).show();
            });
        }
    }

}
