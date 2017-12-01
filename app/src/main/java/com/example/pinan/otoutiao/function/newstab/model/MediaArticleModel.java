package com.example.pinan.otoutiao.function.newstab.model;

import com.example.pinan.otoutiao.base.presenter.IBaseListView;
import com.example.pinan.otoutiao.base.presenter.IBasePresenter;
import com.example.pinan.otoutiao.function.newstab.bean.MediaWendaBean;
import com.example.pinan.otoutiao.function.newstab.bean.MultiMediaArticleBean;

import java.util.List;

/**
 * @author pinan
 * @date 2017/11/29
 */

public interface MediaArticleModel {
    interface View extends IBaseListView<Presenter> {
        /**
         * 请求数据
         */
        void onLoadData();
        
    }
    
    interface Presenter extends IBasePresenter {
        void doLoadArticle(String... mediaId);
        
        void doLoadVideo(String... mediaId);
        
        void doLoadWenda(String... mediaId);
        
        void doLoadMoreData(int type);
        
        void doShowNoMore();
        
        void doSetWendaAdapter(List<MediaWendaBean.AnswerQuestionBean> list);
        
        void doSetAdapter(List<MultiMediaArticleBean.DataBean> list);
        
        void doRefresh(int type);
    }
}
