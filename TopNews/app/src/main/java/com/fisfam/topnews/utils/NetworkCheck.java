package com.fisfam.topnews.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

public class NetworkCheck {

    private static final String TAG = NetworkCheck.class.getSimpleName();

    public static boolean isNetworkAvailable(Context context) {

        if (context == null) {
            Log.e(TAG, "isNetworkAvailable: context is null");
            return false;
        }

        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            Log.e(TAG, "isNetworkAvailable: connectivityManager is null");
            return false;
        }

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            NetworkCapabilities capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                        || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    return true;
                }
            }
        } else {
            try {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null && (activeNetworkInfo.isConnected() || activeNetworkInfo.isConnectedOrConnecting())) {
                    return true;
                }
            } catch (Exception e) {
                Log.e(TAG, "exception: " + e.getMessage());
            }
        }

        return false;
    }
}
