package com.example.pinan.otoutiao.base;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by pinan on 2017/11/15.
 */

public class BaseApplication extends Application {
    
    public static Context sContext;
    
    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }
    
    public static OkHttpClient getOkHttpClick() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(myInterceptor)
            .build();
        return okHttpClient;
    }
    
    private static final Interceptor myInterceptor = new Interceptor() {
        
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            String s = request.toString();
            Log.d("11111111111", "intercept: "+s);
            Response originalResponse = chain.proceed(request);
            // 有网络时 设置缓存为默认值
            String cacheControl = request.cacheControl().toString();
            return originalResponse.newBuilder()
                .header("Cache-Control", cacheControl)
                .removeHeader("Pragma") // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                .build();
        }
    };
}
