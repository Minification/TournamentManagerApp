package com.github.thorbenkuck.tm.tournamentmanagement.network;

import com.github.thorbenkuck.tm.shared.tournament.InviteRequest;

public interface Dispatcher {

    void register(Object o);

    void unregister(Object o);

    boolean prepare(String server);

    boolean isReady();

    void login(String username, String password);

    void register(String username, String password);

    void requestNewTournament(String name);

    void requestTournamentList();

    void logout();

    void joinTournament(String name);

    void invite(String tournamentName, String invitedName);

    void startTournament(String name);

    void proceedInRound(String name);

    void closeTournament(String name);

    void acceptInvite(InviteRequest inviteRequest);

    void declineInvite(InviteRequest inviteRequest);

    String getCurrentServer();

}
