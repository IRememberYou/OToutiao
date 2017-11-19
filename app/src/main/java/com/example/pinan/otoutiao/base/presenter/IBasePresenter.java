package com.example.pinan.otoutiao.base.presenter;

/**
 *
 * @author pinan
 * @date 2017/11/15
 */

public interface IBasePresenter<T extends IBaseView> {
    void attachView(T t);
    
    void detachView();
}
