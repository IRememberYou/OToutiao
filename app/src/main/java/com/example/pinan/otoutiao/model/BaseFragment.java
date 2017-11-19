package com.example.pinan.otoutiao.model;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * @author pinan
 * @date 2017/11/17
 */

public abstract class BaseFragment<T extends BasePresenter> extends RxFragment implements BaseView<T> {
    protected T presenter;
    protected Context mContext;
    
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater,   ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(attachLayoutId(), container, false);
        return view;
    }
    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    
    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }
    
    /**
     * 绑定布局
     *
     * @return
     */
    protected abstract int attachLayoutId();
    
    /**
     * 刷新toolbar
     */
    protected void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        ((BaseActivity) mContext).initToolBar(toolbar, homeAsUpEnabled, title);
    }
    
    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return bindUntilEvent(FragmentEvent.DESTROY);
    }
}
