package com.github.thorbenkuck.tm.tournamentmanagement.models;

import android.app.Activity;
import android.content.Intent;

public class NetCom2Communication implements Communication {

    public NetCom2Communication() {

    }

    @Override
    public void setup(Activity activity) {
        Intent intent = new Intent(activity, NetworkService.class);
        activity.startService(intent);
    }

    @Override
    public void send() {

    }

    @Override
    public void shutdown() {

    }

}
