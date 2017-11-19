package com.example.pinan.otoutiao.newstab.model;


import com.example.pinan.otoutiao.model.BasePresenter;
import com.example.pinan.otoutiao.model.BaseView;
import com.example.pinan.otoutiao.newstab.bean.MultiNewsArticleDataBean;

import java.util.List;

/**
 *
 * @author pinan
 * @date 2017/11/17
 */

public interface TestTagModel {
      interface View extends BaseView<Presenter> {
        /**
         * 加载数据
         */
        void onLoadData();
        
        /**
         * 刷新数据
         */
        void onRefresh();
        
        void setAdapter(List<MultiNewsArticleDataBean> list);
        
    }
    
       interface Presenter extends BasePresenter {
        /**
         * 请求数据
         */
        void doLoadData(String... category);
        
        /**
         * 请求更多数据
         */
        void doLoadMoreData();
        
        /**
         * 设置适配器
         */
        void doSetAdapter(List<MultiNewsArticleDataBean> dataBeen);
        
        /**
         * 加载完毕
         */
        void doShowNoMore();
        
        /**
         * 网络加载错误
         */
        void doShowNetError();
    }
    
}
