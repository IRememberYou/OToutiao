package com.example.pinan.otoutiao.function.newstab.model;

import com.example.pinan.otoutiao.base.presenter.IBasePresenter;
import com.example.pinan.otoutiao.base.presenter.IBaseView;
import com.example.pinan.otoutiao.function.newstab.bean.MediaProfileBean;

/**
 * @author pinan
 * @date 2017/11/29
 */

public interface MediaHomeModel {
    interface View extends IBaseView<Presenter> {
        void onError();
    
        void onUpData(MediaProfileBean bean);
    }
    
    interface Presenter extends IBasePresenter {
        void doLoadData(String mediaId);
    }
}
