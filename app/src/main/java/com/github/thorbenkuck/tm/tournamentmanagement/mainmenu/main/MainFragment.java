package com.github.thorbenkuck.tm.tournamentmanagement.mainmenu.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.thorbenkuck.tm.tournamentmanagement.R;
import com.github.thorbenkuck.tm.tournamentmanagement.databinding.FragmentMainBinding;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainFragment extends Fragment implements MainContract.View {

    private MainContract.Presenter presenter;

    @BindView(R.id.tournament_name_text)
    TextInputLayout tournamentName;

    @BindView(R.id.tournaments_recycler)
    RecyclerView recyclerView;

    private Unbinder unbinder;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        FragmentMainBinding fragmentMainBinding = DataBindingUtil.bind(root);
        fragmentMainBinding.setFragment(this);

        unbinder = ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = presenter;
    }

    public void onJoinTournament(View view) {

    }

    public void onCreateTournament(View view) {
        String tournament = tournamentName.getEditText().getText().toString();
        presenter.createTournament(tournament);
    }

    @Override
    public void showTournamentNotCreated() {
        showError(R.string.tournament_creation_failure);
    }

    private void showError(int resource) {
        Snackbar.make(getView(), getString(resource), Snackbar.LENGTH_LONG).show();
    }
}
