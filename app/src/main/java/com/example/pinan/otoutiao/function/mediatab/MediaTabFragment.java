package com.example.pinan.otoutiao.function.mediatab;

import android.view.View;

import com.example.pinan.otoutiao.R;
import com.example.pinan.otoutiao.base.init.BaseFragment;

/**
 * @author pinan
 * @date 2017/11/15
 */

public class MediaTabFragment extends BaseFragment {
    
    public static MediaTabFragment getInstance() {
        return new MediaTabFragment();
    }
    
    @Override
    protected int attachLayoutId() {
        return R.layout.media_tab_fragment;
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
