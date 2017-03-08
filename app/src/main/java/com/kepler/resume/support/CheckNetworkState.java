package com.kepler.resume.support;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckNetworkState {

    public static boolean isNetworkAvailable(Context context) {
        boolean outcome = false;
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo[] networkInfos = cm.getAllNetworkInfo();
            for (NetworkInfo tempNetworkInfo : networkInfos) {
                if (tempNetworkInfo.isConnected()) {
                    outcome = true;
                    break;
                }
            }
        }

        return outcome;
    }
}
