package com.example.pinan.otoutiao.base;

import android.support.annotation.NonNull;

import com.example.pinan.otoutiao.BuildConfig;

import io.reactivex.functions.Consumer;

/**
 * Created by pinan on 2017/11/16.
 */

public class BaseErrorLog {
    @NonNull
    public static Consumer<Throwable> error() {
        return new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                if (BuildConfig.DEBUG) {
                    throwable.printStackTrace();
                }
            }
        };
    }
    
    public static void print(@NonNull Throwable throwable) {
        if (BuildConfig.DEBUG) {
            throwable.printStackTrace();
        }
    }
}
