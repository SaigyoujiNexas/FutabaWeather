package com.saigyouji.futabaweather.utils;

public class ContentUtil
{
    //用户id
    private static final String PUBLIC_ID = "HE2108131721041665";
    //用户key
    private static final String APK_KEY = "b81902b4063543d1b543448dde9d7dd3";

    //当前所在位置
    private static float NOW_LON = SpUtils.getFloat(MyApplication.getApplication(), "now_lon", (float)116.4);
    private static float NOW_LAT = SpUtils.getFloat(MyApplication.getApplication(), "now_lat", (float)39.9);
    private static String NOW_COUNTRY_NAME = SpUtils.getString(MyApplication.getApplication(), "now_country_name", "北京");


    public static String getNowCountryName() {
        return NOW_COUNTRY_NAME;
    }

    public static void setNowCountryName(String countryName)
    {
        NOW_COUNTRY_NAME = countryName;
        SpUtils.putString(MyApplication.getApplication(), "now_country_name", countryName);
    }


    public static String getPublicId() {
        return PUBLIC_ID;
    }

    public static String getApkKey() {
        return APK_KEY;
    }

    public static float getNowLat() {
        return NOW_LAT;
    }

    public static void setNowLat(float nowLat) {
        SpUtils.putFloat(MyApplication.getApplication(), "now_lat", nowLat);
        NOW_LAT = nowLat;
    }

    public static float getNowLon() {
        return NOW_LON;
    }
    public static void setNowLon(float nowLon) {
        SpUtils.putFloat(MyApplication.getApplication(), "now_lon", nowLon);
        NOW_LON = nowLon;
    }

    public static boolean FIRST_OPEN = SpUtils.getBoolean(MyApplication.getContext(), "first_open", true);

    //应用设置里的文字
    public static String SYS_LANG = "zh";
    public static String APP_SETTING_LANG = SpUtils.getString(MyApplication.getContext(), "language", "sys");
    public static String APP_SETTING_UNIT = SpUtils.getString(MyApplication.getContext(), "unit", "she");
    public static String APP_SETTING_TESI = SpUtils.getString(MyApplication.getContext(), "size", "mid");
    public static String APP_PRI_TESI = SpUtils.getString(MyApplication.getContext(), "size", "mid");
    public static String APP_SETTING_THEME = SpUtils.getString(MyApplication.getContext(), "theme", "浅色");


    public static boolean UNIT_CHANGE = false;
    public static boolean CHANGE_LANG = false;
    public static boolean CITY_CHANGE = false;

}
