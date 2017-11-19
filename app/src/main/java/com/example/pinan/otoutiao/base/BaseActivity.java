package com.example.pinan.otoutiao.base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by pinan on 2017/11/13.
 */

public class BaseActivity extends AppCompatActivity {
    
    
    protected void initToolbar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
    }
    
    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(view);
    }
    
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        ButterKnife.bind(view);
    }
    
    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        ButterKnife.bind(view);
    }
    
    protected void startActivity(Class clazz) {
        startActivity(new Intent(this, clazz));
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onBackPressed() {
        //fragment 逐个出栈
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }
}
