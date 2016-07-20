package com.example.xyzreader.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by AdonisArifi on 18.1.2016 - 2016 . alexandria
 */
public class XYZSharedPref {

    private SharedPreferences sharedPreferences;
    private SharedPreferences sharedPreferencesDefault;
    private Context myContext;
    public static XYZSharedPref xyzSharedPref;

    public static XYZSharedPref getxyzSharedPref(Context context) {
        if (xyzSharedPref == null) {
            xyzSharedPref = new XYZSharedPref(context);
        }
        return xyzSharedPref;
    }

    public XYZSharedPref(Context context) {
        sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferencesDefault = PreferenceManager.getDefaultSharedPreferences(context);
        myContext = context;
    }

    public void saveMiniCardIsChecked(boolean isminicard) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Constants.SHARED_PREF_IS_MINICARD, isminicard);
        editor.apply();
    }

    public boolean geMiniCardIsChecked() {
        return sharedPreferences.getBoolean(Constants.SHARED_PREF_IS_MINICARD, true);
    }


    public void saveIsAddDataFromDropBox(boolean isadd) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Constants.SHARED_PREF_IS_DATAADDFROMDROPBOX, isadd);
        editor.apply();
    }

    public boolean getIsAddDataFromDropBox() {
        return sharedPreferences.getBoolean(Constants.SHARED_PREF_IS_DATAADDFROMDROPBOX, false);
    }

}
