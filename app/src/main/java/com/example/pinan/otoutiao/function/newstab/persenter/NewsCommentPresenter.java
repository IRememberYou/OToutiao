package com.example.pinan.otoutiao.function.newstab.persenter;

import com.example.pinan.otoutiao.base.http.RetrofitUtils;
import com.example.pinan.otoutiao.function.newstab.api.INewstabApi;
import com.example.pinan.otoutiao.function.newstab.bean.NewsCommentBean;
import com.example.pinan.otoutiao.function.newstab.model.NewsCommentModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author pinan
 * @date 2017/11/28
 */

public class NewsCommentPresenter implements NewsCommentModel.Presenter {
    
    private final NewsCommentModel.View mView;
    private int offset = 0;
    private List<NewsCommentBean.DataBean.CommentBean> commentsBeanList = new ArrayList<>();
    private String mGroupId;
    private String mItemId;
    
    public NewsCommentPresenter(NewsCommentModel.View view) {
        mView = view;
    }
    
    
    @Override
    public void doRefresh() {
        commentsBeanList.clear();
        offset = 0;
        doLoadData(mGroupId, mItemId);
    }
    
    @Override
    public void doShowNetError() {
        mView.onShowNetError();
    }
    
    @Override
    public void doLoadData(String groupId, String itemId) {
        mGroupId = groupId;
        mItemId = itemId;
        RetrofitUtils.getRetrofit().create(INewstabApi.class)
            .getNewsComment(mGroupId, offset)
            .subscribeOn(Schedulers.io())
            .map(new Function<NewsCommentBean, List<NewsCommentBean.DataBean.CommentBean>>() {
                @Override
                public List<NewsCommentBean.DataBean.CommentBean> apply(NewsCommentBean newsCommentBean) throws Exception {
                    List<NewsCommentBean.DataBean.CommentBean> data = new ArrayList<>();
                    for (NewsCommentBean.DataBean bean : newsCommentBean.data) {
                        data.add(bean.comment);
                    }
                    return data;
                }
            })
            .compose(mView.<List<NewsCommentBean.DataBean.CommentBean>>bindToLife())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<List<NewsCommentBean.DataBean.CommentBean>>() {
                @Override
                public void accept(List<NewsCommentBean.DataBean.CommentBean> commentBeans) throws Exception {
                    if (commentBeans != null && commentBeans.size() > 0) {
                        doSetAdapter(commentBeans);
                    } else {
                        doShowNoMore();
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    doShowNetError();
                }
            });
    }
    
    @Override
    public void doSetAdapter(List<NewsCommentBean.DataBean.CommentBean> beans) {
        commentsBeanList.addAll(beans);
        mView.onSetAdapter(commentsBeanList);
    }
    
    @Override
    public void doShowNoMore() {
        mView.onShowNoMore();
    }
    
    @Override
    public void doLoadMore() {
        offset += 20;
        doLoadData(mGroupId, mItemId);
    }
}
