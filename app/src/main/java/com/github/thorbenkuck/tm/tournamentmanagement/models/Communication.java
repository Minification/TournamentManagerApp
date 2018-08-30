package com.github.thorbenkuck.tm.tournamentmanagement.models;

import android.app.Activity;

public interface Communication {

    void setup(Activity activity);

    void send();

    void shutdown();

}
