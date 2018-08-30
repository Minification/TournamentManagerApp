package com.github.thorbenkuck.tm.tournamentmanagement.util;

import android.os.Handler;
import android.os.Looper;

public class Utils {

    public static void onUI(Runnable r) {
        new Handler(Looper.getMainLooper()).post(r);
    }

}
