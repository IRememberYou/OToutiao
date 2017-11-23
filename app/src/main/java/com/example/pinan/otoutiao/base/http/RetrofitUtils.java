package com.example.pinan.otoutiao.base.http;

import android.support.annotation.NonNull;

import com.example.pinan.otoutiao.base.init.BaseApplication;
import com.example.pinan.otoutiao.utils.LogUtils;
import com.example.pinan.otoutiao.utils.NetWorkUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author pinan
 */
public class RetrofitUtils {
    /**
     * 接口地址
     */
    private static final String BASE_API = "http://toutiao.com/";
    /**
     * 连接超时时长x秒
     */
    private static final int CONNECT_TIME_OUT = 30;
    /**
     * 读数据超时时长x秒
     */
    private static final int READ_TIME_OUT = 30;
    /**
     * 写数据接超时时长x秒
     */
    private static final int WRITE_TIME_OUT = 30;
    private static Retrofit mRetrofit = null;
    
    /**
     * 获取Retrofit
     */
    public synchronized static Retrofit getRetrofit() {
        if (mRetrofit == null) {
            synchronized (RetrofitUtils.class) {
                if (mRetrofit == null) {
                    mRetrofit = new Retrofit.Builder()
                        .client(okHttpClient())
                        .baseUrl(BASE_API)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();
                }
            }
        }
        return mRetrofit;
    }
    
    /**
     * 设置okHttp
     */
    private static OkHttpClient okHttpClient() {
        //开启Log
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NonNull String message) {
                    LogUtils.e("okHttp:" + message);
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return new OkHttpClient.Builder()
            .addInterceptor(cacheControlInterceptor)
            .addInterceptor(logging)
            .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
            .build();
    }
    
    /**
     * 设置头，过滤一些信息
     */
    private static Interceptor cacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetWorkUtil.isNetworkConnected(BaseApplication.sContext)) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response response = chain.proceed(request);
            if (NetWorkUtil.isNetworkConnected(BaseApplication.sContext)) {
                // 有网络时 设置缓存为默认值
                String cacheControl = request.cacheControl().toString();
                return response.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma") // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .build();
            } else {
                // 无网络时 设置超时为1周
                int maxStale = 60 * 60 * 24 * 7;
                return response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("Pragma")
                    .build();
            }
        }
    };
}