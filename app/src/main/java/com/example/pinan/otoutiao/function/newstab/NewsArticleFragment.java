package com.example.pinan.otoutiao.function.newstab;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.pinan.otoutiao.base.init.BaseApplication;
import com.example.pinan.otoutiao.base.init.BaseListFragment;
import com.example.pinan.otoutiao.function.newstab.model.NewsArticleModel;
import com.example.pinan.otoutiao.function.newstab.persenter.NewsArticlePresenter;
import com.example.pinan.otoutiao.model.other.DiffCallback;
import com.example.pinan.otoutiao.model.other.LoadingBean;
import com.example.pinan.otoutiao.model.other.OnLoadMoreListener;
import com.example.pinan.otoutiao.model.other.Register;
import com.example.pinan.otoutiao.utils.LogUtils;

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
        if (TextUtils.isEmpty(channelId)) {
            Toast.makeText(BaseApplication.sContext, channelId + "11111111   为空", Toast.LENGTH_SHORT).show();
        }
        bundle.putString(CHANNEL_ID, channelId);
        fragment.setArguments(bundle);
        return fragment;
    }
    
    @Override
    public void setPresenter(NewsArticleModel.Presenter presenter) {
        Log.d(TAG, "setPresenter != null " + "外面呢");
        if (null == presenter) {
            Log.d(TAG, "setPresenter: ");
            this.presenter = new NewsArticlePresenter(this);
        }
    }
    
    @Override
    protected void initView(View view) {
        super.initView(view);
        LogUtils.d(TAG, "initView: ");
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
        LogUtils.d(TAG, "initData: ");
        mChannelId = getArguments().getString(CHANNEL_ID);
    }
    
    @Override
    public void fetchData() {
        super.fetchData();
        LogUtils.d(TAG, "fetchData: ");
        onLoadData();
    }
    
    @Override
    public void onLoadData() {
        Log.d(TAG, "onLoadData: ");
        onShowLoading();
        if (TextUtils.isEmpty(mChannelId)) {
            Toast.makeText(mContext, "mChannelId is null", Toast.LENGTH_SHORT).show();
        }
        presenter.doLoadData(mChannelId);
    }
    
    @Override
    public void onSetAdapter(List<?> list) {
        Log.d(TAG, "onSetAdapter: ");
        Items newItems = new Items(list);
        newItems.add(new LoadingBean());
        DiffCallback.notifyDataSetChanged(oldItems, newItems, DiffCallback.MUlTI_NEWS, adapter);
        oldItems.clear();
        oldItems.addAll(newItems);
        canLoadMore = true;
        onHideLoading();
    }
    
    @Override
    public void onHideLoading() {
        Log.d(TAG, "onHideLoading: ");
        super.onHideLoading();
    }
}
