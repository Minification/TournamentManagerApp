package com.github.thorbenkuck.tm.tournamentmanagement.login;

import android.util.Log;

import com.github.thorbenkuck.tm.shared.user.LoginResponse;
import com.github.thorbenkuck.tm.shared.user.RegistrationResponse;
import com.github.thorbenkuck.tm.tournamentmanagement.network.Dispatcher;
import com.github.thorbenkuck.tm.tournamentmanagement.network.Network;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;

    private Dispatcher network = Network.getInstance();

    private String username;

    private String password;

    private EventBus eventBus;

    public LoginPresenter(LoginContract.View view, EventBus eventBus) {
        this.view = view;
        this.eventBus = eventBus;
        view.setPresenter(this);
    }

    @Override
    public void login(String username, String password, String server) {
        if(username.isEmpty() || password.isEmpty()) {
            view.showUsernamePasswordEmptyError();
            return;
        }

        if (!network.isReady()) {
            if(!network.prepare(server)) {
                view.showServerUnreachableError();
                return;
            } else {
                //TODO: Logging
            }
        }

        this.username = username;
        this.password = password;
        network.login(username, password);
    }

    @Override
    public void start() {
        eventBus.register(this);
    }

    @Subscribe
    public void receivedLogin(LoginResponse loginResponse) {
        Log.wtf("meh", "ok");
        if(!loginResponse.isLoginOkay()) {
            view.showLoginError();
            network.register(username, password);
        } else {
            view.showMainMenu();
        }
    }

    @Subscribe
    public void receivedRegistration(RegistrationResponse registrationResponse) {
        if (!registrationResponse.isSuccessful()) {
            view.showLoginAndRegistrationError();
        } else {
            network.login(username, password);
        }
    }

}
