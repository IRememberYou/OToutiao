package com.example.pinan.otoutiao.model;

/**
 * @author pinan
 * @date 2017/11/17
 */

public interface BasePresenter<T extends BaseView> {
    /**
     * 加载数据
     */
    void doRefresh();
}
