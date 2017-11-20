package com.example.pinan.otoutiao.function.newstab;


import android.os.Bundle;
import android.view.View;

import com.example.pinan.otoutiao.base.init.BaseListFragment;
import com.example.pinan.otoutiao.function.newstab.bean.MultiNewsArticleDataBean;
import com.example.pinan.otoutiao.function.newstab.model.TestTagModel;
import com.example.pinan.otoutiao.function.newstab.persenter.TestTagPresenter;
import com.example.pinan.otoutiao.model.other.DiffCallback;
import com.example.pinan.otoutiao.model.other.LoadingBean;
import com.example.pinan.otoutiao.model.other.OnLoadMoreListener;
import com.example.pinan.otoutiao.model.other.Register;

import java.util.List;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * @author pinan
 * @date 2017/11/16
 */

public class NewsArticleFragment extends BaseListFragment<TestTagModel.Presenter> implements TestTagModel.View{
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mChannelId = getArguments().getString(CHANNEL_ID);
        this.presenter = new TestTagPresenter(this);
        adapter = new MultiTypeAdapter(oldItems);
        Register.registerNewsArticleItem(adapter);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (canLoadMore) {
                    canLoadMore = false;
                    //加载更多
//                    presenter.doLoadData(channelId);
                }
            }
        });
    }
    
    @Override
    public void fetchData() {
        super.fetchData();
        onLoadData();
        
    }
    
    @Override
    public void onHideLoading() {
        super.onHideLoading();
    }
    
    @Override
    public void setPresenter(TestTagModel.Presenter presenter) {
        if (null == presenter) {
            this.presenter = new TestTagPresenter(this);
        }
    }
    
    @Override
    public void onSetAdapter(List<?> list) {
    
    }
    
    
    @Override
    public void onLoadData() {
        onShowLoading();
        presenter.doLoadData(mChannelId);
    }
    
    
    @Override
    public void setAdapter(List<MultiNewsArticleDataBean> list) {
        Items newItems = new Items(list);
        newItems.add(new LoadingBean());
        DiffCallback.notifyDataSetChanged(oldItems, newItems, DiffCallback.MUlTI_NEWS, adapter);
        oldItems.clear();
        oldItems.addAll(newItems);
        canLoadMore = true;
        onHideLoading();
    }
    
    @Override
    protected void initData() throws NullPointerException {
    
    }
}
