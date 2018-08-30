package com.github.thorbenkuck.tm.tournamentmanagement.login;

import com.github.thorbenkuck.tm.tournamentmanagement.BasePresenter;
import com.github.thorbenkuck.tm.tournamentmanagement.BaseView;

public interface LoginContract {

    interface View extends BaseView<Presenter> {

        void showError(String error);

        void showUsernamePasswordEmptyError();

        void showServerUnreachableError();

        void showMainMenu();

        void showLoginError();

        void showLoginAndRegistrationError();
    }

    interface Presenter extends BasePresenter {

        void login(String username, String password, String server);

    }

}
