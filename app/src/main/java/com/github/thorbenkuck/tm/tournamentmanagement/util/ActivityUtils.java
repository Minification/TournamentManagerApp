package com.github.thorbenkuck.tm.tournamentmanagement.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class ActivityUtils {

    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int resource) {
        fragmentManager.beginTransaction().add(resource, fragment).commitNow();
    }

}
