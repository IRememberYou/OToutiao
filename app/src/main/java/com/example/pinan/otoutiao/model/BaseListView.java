package com.example.pinan.otoutiao.model;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * @author pinan
 * @date 2017/11/17
 */

public interface BaseListView<T extends BasePresenter> extends BaseView<T> {
    /**
     * 隐藏加载动画
     */
    void onHideLoaging();
    
    /**
     * 显示加载动画
     */
    @Override
    void onShowLoading();
    
    /**
     * 加载完成
     */
    void onLoadFinish();
    
    /**
     * 网络出错
     */
    @Override
    void onShowNetError();
    
    /**
     * 设置presenter
     */
    @Override
    void setPresenter(T presenter);
    
    
    /**
     * 绑定生命周期
     */
    @Override
    <T> LifecycleTransformer<T> bindToLife();
}