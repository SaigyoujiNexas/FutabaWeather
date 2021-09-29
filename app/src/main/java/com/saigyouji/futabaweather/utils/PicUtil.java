package com.saigyouji.futabaweather.utils;

import android.os.SystemClock;

import com.saigyouji.futabaweather.R;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class PicUtil
{
    public static int getWeatherIcon(String code)
    {
        switch(code)
        {
            case "100":
                return R.mipmap.icon_100;
            case "101":
            case "151":
                return R.mipmap.icon_101;
            case "102":
            case "152":
                return R.mipmap.icon_102;
            case "103":
                return R.mipmap.icon_103;
            case "104":
                return R.mipmap.icon_104;
            case "150":
                return R.mipmap.icon_150;
            case "153":
                return R.mipmap.icon_153;
            case "154":
                return R.mipmap.icon_154;
            case "300":
                return R.mipmap.icon_300;
            case "301":
                return R.mipmap.icon_301;
            case "302":
                return R.mipmap.icon_302;
            case "303":
                return R.mipmap.icon_303;
            case "304":
                return R.mipmap.icon_304;
            case "305":
                return R.mipmap.icon_305;
            case "306":
                return R.mipmap.icon_306;
            case "307":
                return R.mipmap.icon_307;
            case "308":
                return R.mipmap.icon_308;
            case "309":
                return R.mipmap.icon_309;
            case "310":
                return R.mipmap.icon_310;
            case "311":
                return R.mipmap.icon_311;
            case "312":
                return R.mipmap.icon_312;
            case "313":
                return R.mipmap.icon_313;
            case "314":
                return R.mipmap.icon_314;
            case "315":
                return R.mipmap.icon_315;
            case "316":
                return R.mipmap.icon_316;
            case "317":
                return R.mipmap.icon_317;
            case "318":
                return R.mipmap.icon_318;
            case "350":
                return R.mipmap.icon_350;
            case "351":
                return R.mipmap.icon_351;
            case "399":
                return R.mipmap.icon_399;
            case "400":
                return R.mipmap.icon_400;
            case "401":
                return R.mipmap.icon_401;
            case "402":
                return R.mipmap.icon_402;
            case "403":
                return R.mipmap.icon_403;
            case "404":
                return R.mipmap.icon_404;
            case "405":
                return R.mipmap.icon_405;
            case "406":
                return R.mipmap.icon_406;
            case "407":
                return R.mipmap.icon_407;
            case "408":
                return R.mipmap.icon_408;
            case "409":
                return R.mipmap.icon_409;
            case "410":
                return R.mipmap.icon_410;
            case "456":
                return R.mipmap.icon_456;
            case "457":
                return R.mipmap.icon_457;
            case "499":
                return R.mipmap.icon_499;
            case "500":
                return R.mipmap.icon_500;
            case "501":
                return R.mipmap.icon_501;
            case "502":
                return R.mipmap.icon_502;
            case "503":
                return R.mipmap.icon_503;
            case "504":
                return R.mipmap.icon_504;
            case "507":
                return R.mipmap.icon_507;
            case "508":
                return R.mipmap.icon_508;
            case "509":
                return R.mipmap.icon_509;
            case "510":
                return R.mipmap.icon_510;
            case "511":
                return R.mipmap.icon_511;
            case "512":
                return R.mipmap.icon_512;
            case "513":
                return R.mipmap.icon_513;
            case "514":
                return R.mipmap.icon_514;
            case "515":
                return R.mipmap.icon_515;
            case "900":
                return R.mipmap.icon_900;
            case "901":
                return R.mipmap.icon_901;
            case "999":
                return R.mipmap.icon_999;
            default:
                return R.mipmap.icon_100;
        }
    }
    public static int getBackground(String code)
    {
        var now = Calendar.getInstance();
        var hour = now.get(Calendar.HOUR_OF_DAY);
        if(hour > 19)
            return getNightBackground(code);
        else
            return getDayBackground(code);
    }

    private static int getNightBackground(String code)
    {
        switch (code)
        {
            case "100":
            case "150":
            case "900":
                return R.mipmap.back_100n;
            case "101":
            case "151":
            case "102":
            case "152":
            case "103":
            case "153":
                return R.mipmap.back_101n;
            case "104":
            case "154":
                return R.mipmap.back_104n;
            case "300":
            case "350":
            case "301":
            case "351":
            case "305":
            case "306":
            case "307":
            case "308":
            case "309":
            case "310":
            case "311":
            case "312":
            case "313":
            case "314":
            case "315":
            case "316":
            case "317":
            case "318":
            case "399":
                return R.mipmap.back_300n;

            case "302":
            case "303":
            case "304":
                return R.mipmap.back_302n;
            case "400":
            case "401":
            case "402":
            case "403":
            case "404":
            case "405":
            case "406":
            case "456":
            case "407":
            case "457":
            case "408":
            case "409":
            case "410":
            case "499":
                return R.mipmap.back_400n;
            case "500":
            case "501":
            case "509":
            case "510":
            case "514":
            case "515":
                return R.mipmap.back_500n;
            case "502":
            case "511":
            case "512":
            case "513":
                return R.mipmap.back_502n;
            case "503":
            case "504":
            case "507":
            case "508":
                return R.mipmap.back_503n;
            case "901":
                return R.mipmap.back_901n;
            default:
                return R.mipmap.back_100n;
        }
    }
    private static int getDayBackground(String code)
    {
        switch (code)
        {
            case "100":
            case "150":
                return R.mipmap.back_100d;
            case "101":
            case "151":
            case "102":
            case "152":
            case "103":
            case "153":
                return R.mipmap.back_101d;
            case "104":
            case "154":
                return R.mipmap.back_104d;
            case "300":
            case "350":
            case "301":
            case "351":
            case "305":
            case "306":
            case "307":
            case "308":
            case "309":
            case "310":
            case "311":
            case "312":
            case "313":
            case "314":
            case "315":
            case "316":
            case "317":
            case "318":
            case "399":
                return R.mipmap.back_300d;

            case "302":
            case "303":
            case "304":
                return R.mipmap.back_302d;
            case "400":
            case "401":
            case "402":
            case "403":
            case "404":
            case "405":
            case "406":
            case "456":
            case "407":
            case "457":
            case "408":
            case "409":
            case "410":
            case "499":
                return R.mipmap.back_400d;
            case "500":
            case "501":
            case "509":
            case "510":
            case "514":
            case "515":
                return R.mipmap.back_500d;
            case "502":
            case "511":
            case "512":
            case "513":
                return R.mipmap.back_502d;
            case "503":
            case "504":
            case "507":
            case "508":
                return R.mipmap.back_503d;
            case "900":
                return R.mipmap.back_900d;
            case "901":
                return R.mipmap.back_901d;
            default:
                return R.mipmap.back_100d;
        }
    }
}
