package com.example.pinan.otoutiao.function.newstab;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.Toolbar;

import com.example.pinan.otoutiao.R;
import com.example.pinan.otoutiao.base.adapter.BasePagerAdapter;
import com.example.pinan.otoutiao.base.init.BaseActivity;
import com.example.pinan.otoutiao.base.init.BaseApplication;
import com.example.pinan.otoutiao.function.newstab.bean.MediaProfileBean;
import com.example.pinan.otoutiao.function.newstab.model.MediaHomeModel;
import com.example.pinan.otoutiao.function.newstab.persenter.MediaHomePresenter;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pinan
 * @date 2017/11/29
 */

public class MediaHomeActivity extends BaseActivity implements MediaHomeModel.View {
    private MediaHomeModel.Presenter presenter;
    private static final String KEY_MEDIA_ID = "mediaid";
    private Toolbar mToolbar;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();
    private ViewPager mViewPager;
    private ContentLoadingProgressBar mProgressBar;
    
    public static void launcher(String mediaId) {
        BaseApplication.sContext.startActivity(new Intent(BaseApplication.sContext, MediaHomeActivity.class)
            .putExtra(KEY_MEDIA_ID, mediaId)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_home_activity);
        setPresenter(presenter);
        initView();
        initData();
    }
    
    private void initData() {
        String mediaId = getIntent().getStringExtra(KEY_MEDIA_ID);
        presenter.doLoadData(mediaId);
    }
    
    private void initView() {
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);
        mProgressBar = findViewById(R.id.pb_progress);
        mToolbar = findViewById(R.id.toolbar);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setBackgroundColor(getColor(R.color.colorPrimary));
    }
    
    @Override
    public void onShowLoading() {
    
    }
    
    @Override
    public void onHideLoading() {
    
    }
    
    @Override
    public void onShowNetError() {
    
    }
    
    @Override
    public void setPresenter(MediaHomeModel.Presenter presenter) {
        if (presenter == null) {
            this.presenter = new MediaHomePresenter(this);
        }
    }
    
    @Override
    public void onError() {
        mProgressBar.hide();
        Snackbar.make(mProgressBar, getString(R.string.error), Snackbar.LENGTH_INDEFINITE).show();
    }
    
    @Override
    public void onUpData(MediaProfileBean bean) {
        String name = bean.data.name;
        initToolBar(mToolbar, true, name);
        List<MediaProfileBean.DataBean.TopTabBean> topTab = bean.data.top_tab;
        for (MediaProfileBean.DataBean.TopTabBean tabBean : topTab) {
            if ("all".equals(tabBean.type)) {
                fragmentList.add(MediaArticleFragment.newInstance(bean.data));
                titleList.add(tabBean.show_name);
            } else if ("video".equals(tabBean.type)) {
//                fragmentList.add(MediaArticleFragment.newInstance(bean.data));
//                titleList.add(tabBean.show_name);
            } else if ("wenda".equals(tabBean.show_name)) {
//                fragmentList.add(MediaArticleFragment.newInstance(bean));
//                titleList.add(tabBean.show_name);
            }
        }
        mViewPager.setAdapter(new BasePagerAdapter(getSupportFragmentManager(), fragmentList, titleList));
    }
    
    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return bindUntilEvent(ActivityEvent.DESTROY);
    }
}
