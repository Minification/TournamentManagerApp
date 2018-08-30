package com.github.thorbenkuck.tm.tournamentmanagement.network;

import android.os.AsyncTask;

import com.github.thorbenkuck.netcom2.exceptions.StartFailedException;
import com.github.thorbenkuck.netcom2.network.client.ClientStart;
import com.github.thorbenkuck.tm.shared.tournament.CloseTournamentRequest;
import com.github.thorbenkuck.tm.shared.tournament.InvitePlayerRequest;
import com.github.thorbenkuck.tm.shared.tournament.InviteRequest;
import com.github.thorbenkuck.tm.shared.tournament.InviteResponse;
import com.github.thorbenkuck.tm.shared.tournament.JoinTournament;
import com.github.thorbenkuck.tm.shared.tournament.ListTournamentsRequest;
import com.github.thorbenkuck.tm.shared.tournament.ProceedInRoundRequest;
import com.github.thorbenkuck.tm.shared.tournament.StartTournamentRequest;
import com.github.thorbenkuck.tm.shared.tournament.TournamentRequest;
import com.github.thorbenkuck.tm.shared.user.LoginRequest;
import com.github.thorbenkuck.tm.shared.user.LogoutRequest;
import com.github.thorbenkuck.tm.shared.user.RegistrationRequest;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.ExecutionException;

public class Network implements Dispatcher {

    private final EventBus eventBus = EventBus.getDefault();
    private ClientStart clientStart;
    private String currentServer;
    private static Network network;

    private Network() {

    }

    private void requireConnected() {
        if (!isReady()) {
            throw new IllegalStateException("Not Launched!");
        }
    }

    private void send(Object object) {
        requireConnected();
        clientStart.send().objectToServer(object);
    }

    @Override
    public void register(Object o) {
        eventBus.register(o);
    }

    @Override
    public void unregister(Object o) {
        eventBus.unregister(o);
    }

    public synchronized static Network getInstance() {
        if(network == null) {
            network = new Network();
        }
        return network;
    }

    @Override
    public boolean prepare(String server) {
        if (isReady()) {
            return true;
        }

        clientStart = ClientStart.at(server, 2048);
        System.out.println("Opening ClientStart at " + server + ":" + 2048);
        clientStart.getCommunicationRegistration()
                .addDefaultCommunicationHandler(eventBus::post);

        AsyncTask<String, Void, Boolean> meh = new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... strings) {
                try {
                    clientStart.launch();
                    currentServer = server;
                    return true;
                } catch (StartFailedException e) {
                    clientStart = null;e.printStackTrace();
                    return false;
                }
            }
        };
        try {
            return meh.execute(server).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isReady() {
        return clientStart != null;
    }

    @Override
    public void login(String username, String password) {
        send(new LoginRequest(username, password));
    }

    @Override
    public void register(String username, String password) {
        send(new RegistrationRequest(username, password));
    }

    @Override
    public void requestNewTournament(String name) {
        send(new TournamentRequest(name));
    }

    @Override
    public void requestTournamentList() {
        send(new ListTournamentsRequest());
    }

    @Override
    public void logout() {
        send(new LogoutRequest());
    }

    @Override
    public void joinTournament(String name) {
        send(new JoinTournament(name));
    }

    @Override
    public void invite(String tournamentName, String invitedName) {
        send(new InvitePlayerRequest(invitedName, tournamentName));
    }

    @Override
    public void startTournament(String name) {
        send(new StartTournamentRequest(name));
    }

    @Override
    public void proceedInRound(String name) {
        send(new ProceedInRoundRequest(name));
    }

    @Override
    public void closeTournament(String name) {
        send(new CloseTournamentRequest(name));
    }

    @Override
    public void acceptInvite(InviteRequest inviteRequest) {
        send(new InviteResponse(true, inviteRequest));
    }

    @Override
    public void declineInvite(InviteRequest inviteRequest) {
        send(new InviteResponse(false, inviteRequest));
    }

    @Override
    public String getCurrentServer() {
        return currentServer;
    }
}
