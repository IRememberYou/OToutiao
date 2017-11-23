package com.example.pinan.otoutiao.function.newstab;

import android.view.View;
import android.widget.TextView;

import com.example.pinan.otoutiao.R;
import com.example.pinan.otoutiao.base.init.BaseFragment;

/**
 * @author pinan
 * @date 2017/11/16
 */

public class JokeContentFragment extends BaseFragment {
    private String TAG = "JokeContentFragment";
    TextView tv;
    
    public static JokeContentFragment getInstance() {
        return new JokeContentFragment();
    }
    
    @Override
    protected int attachLayoutId() {
        return R.layout.joke_content_fragment;
    }
    
    @Override
    protected void initView(View view) {
        tv = (TextView) view.findViewById(R.id.tv);
        tv.setText(TAG + "3333333");
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
