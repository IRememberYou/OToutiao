package com.example.pinan.otoutiao.function.newstab;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.pinan.otoutiao.R;
import com.example.pinan.otoutiao.base.init.BaseFragment;
import com.example.pinan.otoutiao.function.newstab.model.NewsCommentModel;
import com.example.pinan.otoutiao.function.newstab.persenter.NewsCommentPresenter;
import com.example.pinan.otoutiao.model.bean.LoadingBean;
import com.example.pinan.otoutiao.model.bean.LoadingEndBean;
import com.example.pinan.otoutiao.model.bean.OnLoadMoreListener;
import com.example.pinan.otoutiao.model.bean.Register;

import java.util.List;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * @author pinan
 * @date 2017/11/28
 */

public class NewsCommentFragment extends BaseFragment<NewsCommentModel.Presenter> implements NewsCommentModel.View, SwipeRefreshLayout.OnRefreshListener {
    private static final String KEY_GROUP_ID = "groupid";
    private static final String KEY_ITEM_ID = "itemid";
    private Items mOldItems = new Items();
    private MultiTypeAdapter mAdapter;
    private SwipeRefreshLayout mRefreshLayout;
    private boolean canLoadMore = false;
    
    public static NewsCommentFragment newInstance(String groupId, String itemId) {
        NewsCommentFragment fragment = new NewsCommentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_GROUP_ID, groupId);
        bundle.putString(KEY_ITEM_ID, itemId);
        fragment.setArguments(bundle);
        return fragment;
    }
    
    @Override
    protected int attachLayoutId() {
        return R.layout.news_comment_fragment;
    }
    
    @Override
    public void setPresenter(NewsCommentModel.Presenter presenter) {
        if (presenter == null) {
            this.presenter = new NewsCommentPresenter(this);
        }
    }
    
    @Override
    protected void initView(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        mRefreshLayout = view.findViewById(R.id.refresh_layout);
        RecyclerView recyclerView = view.findViewById(R.id.recycle_view);
        initToolBar(toolbar, true, "welcome to our world");
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new MultiTypeAdapter(mOldItems);
        Register.registerNewsCommendItem(mAdapter);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnScrollListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (canLoadMore) {
                    canLoadMore = false;
                    presenter.doLoadMore();
                }
            }
        });
        mRefreshLayout.setRefreshing(true);
        mRefreshLayout.setOnRefreshListener(this);
    }
    
    @Override
    protected void initData() throws NullPointerException {
        String itemId = getArguments().getString(KEY_ITEM_ID);
        String groupId = getArguments().getString(KEY_GROUP_ID);
        presenter.doLoadData(groupId, itemId);
    }
    
    @Override
    public void onShowLoading() {
        mRefreshLayout.setRefreshing(true);
    
    }
    
    @Override
    public void onHideLoading() {
        mRefreshLayout.setRefreshing(false);
    }
    
    @Override
    public void onShowNetError() {
    
    }
    
    @Override
    public void onSetAdapter(List<?> beans) {
        canLoadMore = true;
        Items newlist = new Items(beans);
        newlist.add(new LoadingBean());
//        DiffCallback.notifyDataSetChanged(mOldItems,newlist,);
        mOldItems.clear();
        mOldItems.addAll(newlist);
        mAdapter.notifyDataSetChanged();
        onHideLoading();
    }
    
    @Override
    public void onShowNoMore() {
        if (mOldItems.size() > 0) {
            if (mOldItems.get(mOldItems.size() - 1) instanceof LoadingBean || mOldItems.get(mOldItems.size() - 1) instanceof LoadingEndBean) {
                mOldItems.remove(mOldItems.size() - 1);
            }
        }
        mOldItems.add(new LoadingEndBean());
        mAdapter.notifyDataSetChanged();
        onHideLoading();
    }
    
    @Override
    public void onRefresh() {
        canLoadMore = true;
        onShowLoading();
        presenter.doRefresh();
        Toast.makeText(mContext, "重新刷新", Toast.LENGTH_SHORT).show();
    }
}
