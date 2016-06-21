package com.example.xyzreader.utils;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by AdonisArifi on 12.4.2016 - 2016 . xyzreader
 */
public class SupportMethod {

    private Context myContext;

    private static SupportMethod supportMethodInstance;

    public SupportMethod getSupportMethodInstance(Context context) {
        if (supportMethodInstance == null) {
            supportMethodInstance = new SupportMethod(context);
        }

        return supportMethodInstance;
    }

    public SupportMethod(Context context) {
        myContext = context;
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static String getPackageNameOfAplication() {
        return "com.example.xyzreader";
    }
}
