package com.example.pinan.otoutiao.function.newstab;


import android.os.Bundle;
import android.view.View;

import com.example.pinan.otoutiao.base.init.BaseListFragment;
import com.example.pinan.otoutiao.function.newstab.model.NewsArticleModel;
import com.example.pinan.otoutiao.function.newstab.persenter.NewsArticlePresenter;
import com.example.pinan.otoutiao.model.bean.DiffCallback;
import com.example.pinan.otoutiao.model.bean.LoadingBean;
import com.example.pinan.otoutiao.model.bean.OnLoadMoreListener;
import com.example.pinan.otoutiao.model.bean.Register;

import java.util.List;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * @author pinan
 * @date 2017/11/16
 */

public class NewsArticleFragment extends BaseListFragment<NewsArticleModel.Presenter> implements NewsArticleModel.View {
    private String TAG = "NewsArticleFragment";
    private static final String CHANNEL_ID = "channelid";
    private String mChannelId;
    
    public static NewsArticleFragment newInstance(String channelId) {
        NewsArticleFragment fragment = new NewsArticleFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CHANNEL_ID, channelId);
        fragment.setArguments(bundle);
        return fragment;
    }
    
    @Override
    public void setPresenter(NewsArticleModel.Presenter presenter) {
        if (null == presenter) {
            this.presenter = new NewsArticlePresenter(this);
        }
    }
    
    @Override
    protected void initView(View view) {
        super.initView(view);
        adapter = new MultiTypeAdapter(oldItems);
        Register.registerNewsArticleItem(adapter);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (canLoadMore) {
                    canLoadMore = false;
                    presenter.doLoadMoreData();
                }
            }
        });
    }
    
    @Override
    protected void initData() throws NullPointerException {
        mChannelId = getArguments().getString(CHANNEL_ID);
    }
    
    @Override
    public void fetchData() {
        super.fetchData();
        onLoadData();
    }
    
    @Override
    public void onLoadData() {
        onShowLoading();
        presenter.doLoadData(mChannelId);
    }
    
    @Override
    public void onSetAdapter(List<?> list) {
        Items newItems = new Items(list);
        newItems.add(new LoadingBean());
        DiffCallback.notifyDataSetChanged(oldItems, newItems, DiffCallback.MUlTI_NEWS, adapter);
        oldItems.clear();
        oldItems.addAll(newItems);
        canLoadMore = true;
    }
}
