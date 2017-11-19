package com.example.pinan.otoutiao.model;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by pinan on 2017/11/17.
 */

public interface BaseView<T> {
    
    /**
     * 显示加载动画
     */
    void onShowLoading();
    
    /**
     * 隐藏加载
     */
    void onHideLoading();
    
    /**
     * 显示网络错误
     */
    void onShowNetError();
    
    /**
     * 设置 presenter
     */
    void setPresenter(T presenter);
    
    /**
     * 绑定生命周期
     */
    <T> LifecycleTransformer<T> bindToLife();
    
    
    
}
