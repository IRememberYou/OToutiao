package com.example.pinan.otoutiao.base;

import com.example.pinan.otoutiao.BuildConfig;

import io.reactivex.functions.Consumer;

/**
 * @author pinan
 * @date 2017/12/6
 */

public class ErrorAction {
    public static Consumer error() {
        return new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (BuildConfig.DEBUG) {
                    throwable.printStackTrace();
                }
            }
        };
    }
}
