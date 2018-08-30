package com.github.thorbenkuck.tm.tournamentmanagement.mainmenu.main;

import com.github.thorbenkuck.tm.shared.tournament.TournamentResponse;
import com.github.thorbenkuck.tm.tournamentmanagement.network.Dispatcher;
import com.github.thorbenkuck.tm.tournamentmanagement.network.Network;

import org.greenrobot.eventbus.Subscribe;

public class MainPresenter implements MainContract.Presenter {

    private Dispatcher network = Network.getInstance();

    private MainContract.View view;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void start() {
        network.register(this);
    }

    @Override
    public void createTournament(String tournament) {
        network.requestNewTournament(tournament);
    }

    @Subscribe
    public void onTournamentCreated(TournamentResponse tournamentResponse) {
        if (!tournamentResponse.isSuccessful()) {
            view.showTournamentNotCreated();
        }
    }

}
