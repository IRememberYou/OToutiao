package com.example.pinan.otoutiao.function.newstab.model;

import com.example.pinan.otoutiao.base.presenter.IBasePresenter;
import com.example.pinan.otoutiao.base.presenter.IBaseView;
import com.example.pinan.otoutiao.function.newstab.bean.MultiNewsArticleDataBean;

/**
 * @author pinan
 * @date 2017/11/27
 */

public interface NewContentModel {
    interface View extends IBaseView<Presenter> {
        void onSetWebView(String url, boolean flag);
    }
    
    interface Presenter extends IBasePresenter {
        void doLoadData(MultiNewsArticleDataBean bean);
    }
}
