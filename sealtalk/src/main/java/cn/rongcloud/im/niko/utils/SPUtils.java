package cn.rongcloud.im.niko.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtils {

    private static final String NAME = "seal";

    private static final String LOGIN="login";
    private static final String USER_TOKEN="user_token";


    private static SharedPreferences getPreference(Context context) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp;
    }


    public static void setLogin(Context context, boolean value) {
        SharedPreferences sp = getPreference(context);
        sp.edit().putBoolean(LOGIN, value).commit();
    }


    public static boolean getLogin(Context context) {
        SharedPreferences sp = getPreference(context);
        return sp.getBoolean(LOGIN, false);
    }

    public static void setUserToken(Context context, String value) {
        SharedPreferences sp = getPreference(context);
        sp.edit().putString(USER_TOKEN, value).commit();
    }


    public static String getUserToken(Context context) {
        SharedPreferences sp = getPreference(context);
        return sp.getString(USER_TOKEN, "");
    }


}
