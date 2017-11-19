package com.example.pinan.otoutiao.model;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.pinan.otoutiao.R;
import com.example.pinan.otoutiao.base.rx.RxBus;
import com.example.pinan.otoutiao.model.other.LoadingEndBean;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * @author pinan
 * @date 2017/11/17
 */
public abstract class BaseListFragment<T extends BasePresenter> extends LazyLoadFragment<T> implements BaseListView<T>, SwipeRefreshLayout.OnRefreshListener {
    /**
     * 设置为tag
     */
    private static final String TAG = "BaseListFragment";
    /**
     * 获取RecycleView
     */
    protected RecyclerView mRecyclerView;
    /**
     * 获取SwipeRefreshLayout
     */
    protected SwipeRefreshLayout mRefreshLayout;
    /**
     * 获取Observable
     */
    private Observable<Integer> mObservable;
    /**
     * 是否能刷新
     */
    protected boolean mCanLoadMore = false;
    /**
     * 简化版适配器
     */
    protected MultiTypeAdapter mAdapter;
    
    /**
     *
     */
    protected Items mOldItems = new Items();
    
    @Override
    protected int attachLayoutId() {
        return R.layout.base_list_fragment;
    }
    
    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRefreshLayout = view.findViewById(R.id.refresh_layout);
        
        mRecyclerView.setHasFixedSize(true);
        mRefreshLayout.setColorSchemeColors(mContext.getResources().getColor(R.color.colorPrimary));
        mRefreshLayout.setOnRefreshListener(this);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        mRefreshLayout.setColorSchemeColors(mContext.getResources().getColor(R.color.colorPrimary));
    }
    
    @Override
    public void onDestroyView() {
        RxBus.getInstance().unregister(BaseListFragment.TAG, mObservable);
        super.onDestroyView();
    }
    
    @Override
    protected void fetchData() {
        mObservable = RxBus.getInstance().register(BaseListFragment.TAG);
        mObservable.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                mAdapter.notifyDataSetChanged();
            }
        });
    }
    
    @Override
    public void onRefresh() {
        int firstVisibleItemPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        if (firstVisibleItemPosition == 0) {
            presenter.doRefresh();
            return;
        }
        mRecyclerView.scrollToPosition(5);
        mRecyclerView.smoothScrollToPosition(0);
    }
    
    @Override
    public void onHideLoaging() {
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
            }
        });
    }
    
    @Override
    public void onShowLoading() {
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
            }
        });
    }
    
    @Override
    public void onLoadFinish() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mOldItems.size()>0) {
                    Items newItems = new Items(mOldItems);
                    newItems.remove(mOldItems.size() - 1);
                    newItems.add(new LoadingEndBean());
                    mAdapter.setItems(newItems);
                    mAdapter.notifyDataSetChanged();
                }else if(mOldItems.size() == 0){
                    mOldItems.add(new LoadingEndBean());
                    mAdapter.setItems(mOldItems);
                    mAdapter.notifyDataSetChanged();
                }
                mCanLoadMore = false;
            }
        });
    }
    
    @Override
    public void onShowNetError() {
        Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.setItems(new Items());
                mAdapter.notifyDataSetChanged();
                mCanLoadMore = false;
            }
        });
    
    }
}
