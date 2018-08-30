package com.github.thorbenkuck.tm.tournamentmanagement.models;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.github.thorbenkuck.netcom2.network.client.ClientStart;

public class NetworkService extends Service {
    public NetworkService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        ClientStart clientStart = ClientStart.at(intent.getStringExtra("server"), 2048);

        return Service.START_STICKY;
    }
    
    @Override
    public IBinder onBind(Intent intent) {

        throw null;
    }
}
