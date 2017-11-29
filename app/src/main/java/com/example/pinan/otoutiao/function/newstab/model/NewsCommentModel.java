package com.example.pinan.otoutiao.function.newstab.model;

import com.example.pinan.otoutiao.base.presenter.IBaseListView;
import com.example.pinan.otoutiao.base.presenter.IBasePresenter;
import com.example.pinan.otoutiao.function.newstab.bean.NewsCommentBean;

import java.util.List;

/**
 * @author pinan
 * @date 2017/11/28
 */

public interface NewsCommentModel {
    interface View extends IBaseListView<Presenter> {
    }
    
    interface Presenter extends IBasePresenter {
        void doLoadData(String groupId , String itemId);
    
        void doSetAdapter(List<NewsCommentBean.DataBean.CommentBean> beans);
    
        void doShowNoMore();
    
        void doLoadMore();
    }
}
