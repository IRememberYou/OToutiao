package com.example.pinan.otoutiao.base.init;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * Created by Meiji on 2016/12/12.
 */

public abstract class BaseActivity extends RxAppCompatActivity {
    
    /**
     * 初始化 Toolbar
     */
    protected void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
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
        // Fragment 逐个出栈
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }
    
    public void startActivity(Class clzss) {
        startActivity(new Intent(this,clzss));
    }
}
