package com.example.pinan.otoutiao.utils;

import android.util.Log;

import com.example.pinan.otoutiao.BuildConfig;

/**
 * @author pinan
 * @date 2017/11/20
 */

public class LogUtils {
    private static boolean isDebug = BuildConfig.DEBUG;
    private static String TAG = "LogUtils";
    
    public static void d(String message) {
        if (isDebug) {
            Log.d(TAG, message);
        }
    }
    
    public static void e(String message) {
        if (isDebug) {
            Log.e(TAG, message);
        }
    }
    
    
    public static void d(String tag, String message) {
        if (isDebug) {
            Log.d(tag, message);
        }
    }
    
    public static void e(String tag, String message) {
        if (isDebug) {
            Log.e(tag, message);
        }
    }
}
