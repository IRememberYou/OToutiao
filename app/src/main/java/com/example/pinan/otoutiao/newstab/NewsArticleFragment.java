package com.example.pinan.otoutiao.newstab;


import android.os.Bundle;
import android.view.View;

import com.example.pinan.otoutiao.model.BaseListFragment;
import com.example.pinan.otoutiao.model.other.DiffCallback;
import com.example.pinan.otoutiao.model.other.LoadingBean;
import com.example.pinan.otoutiao.model.other.OnLoadMoreListener;
import com.example.pinan.otoutiao.model.other.Register;
import com.example.pinan.otoutiao.newstab.bean.MultiNewsArticleDataBean;
import com.example.pinan.otoutiao.newstab.model.TestTagModel;
import com.example.pinan.otoutiao.newstab.persenter.TestTagPresenter;

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
        mAdapter = new MultiTypeAdapter(mOldItems);
        Register.registerNewsArticleItem(mAdapter);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mCanLoadMore) {
                    mCanLoadMore = false;
                    //加载更多
//                    presenter.doLoadData(channelId);
                }
            }
        });
    }
    
    @Override
    protected void fetchData() {
        super.fetchData();
        onLoadData();
        
    }
    
    @Override
    public void onHideLoading() {
        onHideLoaging();
    }
    
    @Override
    public void setPresenter(TestTagModel.Presenter presenter) {
        if (null == presenter) {
            this.presenter = new TestTagPresenter(this);
        }
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
        DiffCallback.notifyDataSetChanged(mOldItems, newItems, DiffCallback.MUlTI_NEWS, mAdapter);
        mOldItems.clear();
        mOldItems.addAll(newItems);
        mCanLoadMore = true;
        onHideLoaging();
    }
}
