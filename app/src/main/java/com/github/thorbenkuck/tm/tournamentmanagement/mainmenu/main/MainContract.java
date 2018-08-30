package com.github.thorbenkuck.tm.tournamentmanagement.mainmenu.main;

import com.github.thorbenkuck.tm.tournamentmanagement.BasePresenter;
import com.github.thorbenkuck.tm.tournamentmanagement.BaseView;

public interface MainContract {

    interface View extends BaseView<MainContract.Presenter> {

        void showTournamentNotCreated();
    }

    interface Presenter extends BasePresenter {

        void createTournament(String tournament);

    }

}
