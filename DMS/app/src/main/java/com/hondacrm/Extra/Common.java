package com.hondacrm.Extra;

import android.content.Context;
import android.content.SharedPreferences;

import com.loopj.android.http.AsyncHttpClient;


/**
 * Created by Prasad
 */

public class Common {

    public static final String SHARED_PREF = "userData";
    public static AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

    public static void saveUserData(Context context, String key, String value) {

        try {

            SharedPreferences pref = context.getSharedPreferences(SHARED_PREF, 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(key, value);
            editor.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String getSavedUserData(Context context, String key) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREF, 0);
        return pref.getString(key, "");

    }

}
