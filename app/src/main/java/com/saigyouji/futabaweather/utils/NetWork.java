package com.saigyouji.futabaweather.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class NetWork
{
    private static final String TAG = "NetWork";
    public static boolean checkNetWorkIsConnected()
    {
        var context = MyApplication.getContext();
        var connectiveManage =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean wifiIsConnected = false;
        boolean isMobileConn = false;
        for (Network network : connectiveManage.getAllNetworks())
        {
            NetworkInfo netWorkInfo = connectiveManage.getNetworkInfo(network);
            if(netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI)
                wifiIsConnected |= netWorkInfo.isConnected();
            if(netWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
                isMobileConn |= netWorkInfo.isConnected();
        }
        Log.d(TAG, "Wifi connected: " + wifiIsConnected);
        Log.d(TAG, "Mobile connected: " + isMobileConn);
        return wifiIsConnected || isMobileConn;
    }
}
