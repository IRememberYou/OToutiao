package com.example.pinan.otoutiao.utils;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;

import com.example.pinan.otoutiao.base.init.BaseApplication;

public class SettingUtil {
    private SharedPreferences setting = PreferenceManager.getDefaultSharedPreferences(BaseApplication.sContext);
    
    public int getColor() {
        return Color.RED;
    }
    
    private static final class SettingsUtilInstance {
        private static final SettingUtil instance = new SettingUtil();
    }
    
    public static SettingUtil getInstance() {
        return SettingsUtilInstance.instance;
    }
}
