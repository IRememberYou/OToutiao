package com.example.pinan.otoutiao.newstab;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pinan.otoutiao.R;
import com.example.pinan.otoutiao.base.BaseFragment;
import com.example.pinan.otoutiao.newstab.adapter.NewstabAdapter;
import com.example.pinan.otoutiao.newstab.bean.MultiNewsArticleDataBean;
import com.example.pinan.otoutiao.newstab.model.INewstabModel;
import com.example.pinan.otoutiao.newstab.persenter.NewsTabPresenter;

import java.util.List;

import butterknife.Bind;

/**
 * @author pinan
 * @date 2017/11/16
 */

public class NewsArticleFragment1111 extends BaseFragment implements INewstabModel.View {
    private static final String CHANNEL_ID = "channelid";
    private String TAG = "NewsArticleFragment";
    private NewsTabPresenter mPresenter;
    @Bind(R.id.rv)
    RecyclerView mRecyclerView;
    
    public static NewsArticleFragment1111 newInstance(String channelId) {
        NewsArticleFragment1111 fragment = new NewsArticleFragment1111();
        Bundle bundle = new Bundle();
        bundle.putString(CHANNEL_ID, channelId);
        fragment.setArguments(bundle);
        return fragment;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.joke_content_fragment, container, false);
    }
    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String channelId = getArguments().getString(CHANNEL_ID);
        mPresenter = new NewsTabPresenter(this);
        mPresenter.doLoadData(channelId);
    
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
    
    @Override
    public void onLoadData() {
    
    }
    
    @Override
    public void onRefresh() {
    
    }
    
    @Override
    public void setAdapter(List<MultiNewsArticleDataBean> list) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(new NewstabAdapter(list));
    }
}
