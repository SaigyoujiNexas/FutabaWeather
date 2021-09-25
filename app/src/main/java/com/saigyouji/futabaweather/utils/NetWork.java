package com.saigyouji.futabaweather.utils;

import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class NetWork
{
    private static final String TAG = "NetWork";
    private static volatile String retval = null;
    public synchronized static String GetHttpText(String address)
    {
        retval = null;
        ThreadPool.addTask(() -> {
            StringBuilder retString = new StringBuilder();
            Scanner in = null;
            HttpURLConnection connection = null;
            try{
                URL url = new URL(address);
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(80000);
                connection.setReadTimeout(80000);
                connection.setRequestMethod("GET");
                InputStream inputStream = connection.getInputStream();
                in = new Scanner(inputStream);
                while(in.hasNextLine())
                    retString.append(in.nextLine());
                retval = retString.toString();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                retval = "failed";
            }
            finally {
                if(in != null)
                    in.close();
                if(connection != null)
                    connection.disconnect();
            }
        });
        while(retval == null)
            continue;
        Log.d(TAG, "GetHttpText: the retval is" + retval);
        return retval;
    }
}
