package com.example.pinan.otoutiao.function.phototab;

import android.view.View;

import com.example.pinan.otoutiao.R;
import com.example.pinan.otoutiao.base.init.BaseFragment;

/**
 * @author pinan
 * @date 2017/11/15
 */

public class PhotoTabFragment extends BaseFragment {
    public static PhotoTabFragment getInstance() {
        return new PhotoTabFragment();
    }
    
    @Override
    protected int attachLayoutId() {
        return R.layout.photo_tab_fragment;
    }
    
    @Override
    protected void initView(View view) {
    
    }
    
    @Override
    protected void initData() throws NullPointerException {
    
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
    public void setPresenter(Object presenter) {
    
    }
}
