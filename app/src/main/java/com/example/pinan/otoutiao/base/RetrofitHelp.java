package com.example.pinan.otoutiao.base;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pinan on 2017/11/16.
 */

public class RetrofitHelp {
    
    private static Retrofit sRetrofit;
    
    public static Retrofit getRetrofit() {
        synchronized (RetrofitHelp.class) {
            if (sRetrofit == null) {
                sRetrofit = new Retrofit.Builder()
                    .baseUrl(Constant.baseUrl)
                    .client(BaseApplication.getOkHttpClick())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            }
            return sRetrofit;
        }
    }
}
