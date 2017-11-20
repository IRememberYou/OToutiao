package com.example.pinan.otoutiao.base.init;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by pinan on 2017/11/20.
 */

public class BaseApplication extends Application {
    
    public static Context sContext;
    private static final String TAG = "BaseApplication";
    
    
    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }
    
    private static final Interceptor cacheControlInterceptor = new Interceptor() {
        
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Log.d(TAG, "intercept: " + request.url() + "\n");
            Log.d(TAG, "intercept: " + request.body().toString());
            Response originalResponse = chain.proceed(request);
            // 有网络时 设置缓存为默认值
            String cacheControl = request.cacheControl().toString();
            return originalResponse.newBuilder()
                .header("Cache-Control", cacheControl)
                .removeHeader("Pragma") // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                .build();
        }
    };
    
    
    public static OkHttpClient getOkHttpClick() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
            .addInterceptor(cacheControlInterceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true);
        return new OkHttpClient();
    }
}
