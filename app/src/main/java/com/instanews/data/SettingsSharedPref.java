package com.instanews.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.instanews.application.InstaNewsApplication;
import com.instanews.application.InstaNewsConstants;

/**
 * Created by Mohamed Elgendy on 23/5/2017.
 */

public class SettingsSharedPref {

    public static final String PREFERENCE_DESIRED_CATEGORY_KEY = "desiredCategory";
    private static final String PREFERENCE_DESIRED_CATEGORY_DEFAULT = "world";

    private SettingsSharedPref() {
        // prevent any instance from this class
    }

    public static String getDesiredCategory(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(PREFERENCE_DESIRED_CATEGORY_KEY, PREFERENCE_DESIRED_CATEGORY_DEFAULT);
    }

    public static void setDesiredCategory(Context context, String desiredCategory) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREFERENCE_DESIRED_CATEGORY_KEY, desiredCategory);
        editor.apply();
    }
}
