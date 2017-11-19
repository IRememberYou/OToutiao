package com.example.pinan.otoutiao.root;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.example.pinan.otoutiao.base.BaseActivity;

/**
 *
 * @author pinan
 * @date 2017/11/13
 */

public class SplashActivity extends BaseActivity {
    Handler mHandler = new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler.removeCallbacksAndMessages(null);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
            startActivity(new Intent(SplashActivity.this,RootActivity.class));
            finish();
            }
        }, 2000);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }
}
