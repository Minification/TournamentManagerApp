package com.github.thorbenkuck.tm.tournamentmanagement.presenters;

import android.support.v4.app.NotificationCompat;

import com.github.thorbenkuck.tm.shared.Notification;
import com.github.thorbenkuck.tm.shared.user.LoginRequest;
import com.github.thorbenkuck.tm.shared.user.LoginResponse;
import com.github.thorbenkuck.tm.tournamentmanagement.network.Dispatcher;
import com.github.thorbenkuck.tm.tournamentmanagement.network.Network;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class NativeLoginPresenter implements LoginPresenter {

    private EventBus eventBus;

    private Dispatcher network = Network.getInstance();

    private String username;

    private String password;

    @Override
    public void login(String username, String password, String server) {
        if(username.isEmpty() || password.isEmpty()) {
            eventBus.post(new Notification("Username and password are required!"));
            return;
        }

        if (!network.isReady()) {
            if(!network.prepare(server)) {
                eventBus.post(new Notification("Server unreachable"));
            } else {
                //TODO: Logging
            }
        }

        this.username = username;
        this.password = password;
        network.login(username, password);
    }

    @Subscribe
    public void receivedLogin(LoginResponse loginResponse) {
        if(!loginResponse.isLoginOkay()) {
            network.register(username, password);
        } else {

        }
    }

}
