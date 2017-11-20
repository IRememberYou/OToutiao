package com.example.pinan.otoutiao.function.newstab.model;


import com.example.pinan.otoutiao.base.presenter.IBaseListView;
import com.example.pinan.otoutiao.base.presenter.IBasePresenter;
import com.example.pinan.otoutiao.function.newstab.bean.MultiNewsArticleDataBean;

import java.util.List;

/**
 * @author pinan
 * @date 2017/11/17
 */

public interface NewsArticleModel {
    interface View extends IBaseListView<Presenter> {
        /**
         * 请求数据
         */
        void onLoadData();
    
        /**
         * 刷新
         */
        void onRefresh();
    }
    
    interface Presenter extends IBasePresenter {
        /**
         * 请求数据
         *
         * @param category category的Id
         */
        void doLoadData(String... category);
        
        /**
         * 请求更多数据
         */
        void doLoadMoreData();
        
        /**
         * 设置适配器
         *
         * @param dataBeen 数据
         */
        void doSetAdapter(List<MultiNewsArticleDataBean> dataBeen);
        
        /**
         * 加载完毕
         */
        void doShowNoMore();
    }
}
