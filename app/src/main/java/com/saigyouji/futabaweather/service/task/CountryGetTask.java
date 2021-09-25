package com.saigyouji.futabaweather.service.task;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.saigyouji.futabaweather.db.country.Country;
import com.saigyouji.futabaweather.db.country.CountryDatabase;
import com.saigyouji.futabaweather.service.listener.CountryGetListener;
import com.saigyouji.futabaweather.utils.CountryGetter;
import com.saigyouji.futabaweather.utils.MyApplication;
import com.saigyouji.futabaweather.utils.ThreadPool;

import java.util.List;

public class CountryGetTask
{
    public static final int TYPE_COMPLETE  = 0;

    public static final int PROGRESS_UPDATE = 0;
    public static final int POST_EXECUTE = 1;
    private CountryGetListener listener;
    private int lastProgress;
    private Handler handler;

    public CountryGetTask(CountryGetListener listener)
    {
        this.listener = listener;
        handler = new Handler(Looper.myLooper())
        {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what)
                {
                    case POST_EXECUTE:
                        onPostExecute(msg.arg1);
                        break;
                    default:
                        break;
                }
            }
        };
    }

    public void startGet()
    {
        ThreadPool.addTask(() -> {
            int ret = doInBackground();
            Message message = new Message();
            message.what = POST_EXECUTE;
            handler.sendMessage(message);
        });
    }
    protected Integer doInBackground()
    {
        List<Country> list = CountryGetter.getAllCountries();
        CountryDatabase database = CountryDatabase.getDatabase(MyApplication.getContext());
        var dao = database.countryDao();
        dao.insert(list);
        return TYPE_COMPLETE;
    };
    protected void onPostExecute(Integer status)
    {
        switch (status)
        {
            case TYPE_COMPLETE:
                listener.onComplete();
        }
    }
}
