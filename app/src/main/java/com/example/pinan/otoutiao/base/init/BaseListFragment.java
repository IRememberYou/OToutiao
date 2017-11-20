package com.example.pinan.otoutiao.base.init;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.pinan.otoutiao.R;
import com.example.pinan.otoutiao.base.presenter.IBaseListView;
import com.example.pinan.otoutiao.base.presenter.IBasePresenter;
import com.example.pinan.otoutiao.base.rx.RxBus;
import com.example.pinan.otoutiao.model.other.LoadingEndBean;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * @author pinan
 * @date 2017/11/20
 */

public abstract class BaseListFragment<T extends IBasePresenter> extends LazyLoadFragment<T> implements IBaseListView<T>, SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = "BaseListFragment";
    protected RecyclerView recyclerView;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected MultiTypeAdapter adapter;
    protected Items oldItems = new Items();
    protected boolean canLoadMore = false;
    protected Observable<Integer> observable;
    
    @Override
    protected int attachLayoutId() {
        return R.layout.base_list_fragment;
    }
    
    @Override
    protected void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        
        swipeRefreshLayout = view.findViewById(R.id.refresh_layout);
        // TODO: 2017/11/20 修改
        swipeRefreshLayout.setColorSchemeColors(mContext.getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(this);
    }
    
    @Override
    public void onShowLoading() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }
    
    @Override
    public void onHideLoading() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    
    @Override
    public void fetchData() {
        observable = RxBus.getInstance().register(BaseListFragment.TAG);
        observable.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                adapter.notifyDataSetChanged();
            }
        });
    }
    
    @Override
    public void onShowNetError() {
        Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.setItems(new Items());
                adapter.notifyDataSetChanged();
                canLoadMore = false;
            }
        });
    }
    
    @Override
    public void onResume() {
        super.onResume();
        // 设置下拉刷新的按钮的颜色
        // TODO: 2017/11/20 修改
        swipeRefreshLayout.setColorSchemeColors(mContext.getResources().getColor(R.color.colorPrimary));
    }
    
    @Override
    public void onShowNoMore() {
        Toast.makeText(BaseApplication.sContext, R.string.no_more_data, Toast.LENGTH_SHORT).show();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (oldItems.size() > 0) {
                    Items newItems = new Items(oldItems);
                    newItems.remove(newItems.size() - 1);
                    newItems.add(new LoadingEndBean());
                    adapter.setItems(newItems);
                    adapter.notifyDataSetChanged();
                } else if (oldItems.size() == 0) {
                    oldItems.add(new LoadingEndBean());
                    adapter.setItems(oldItems);
                    adapter.notifyDataSetChanged();
                }
                canLoadMore = false;
            }
        });
    }
    
    @Override
    public void onRefresh() {
        int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        if (firstVisibleItemPosition <= 0) {
            presenter.doRefresh();
            return;
        }
        recyclerView.scrollToPosition(5);
        recyclerView.smoothScrollToPosition(0);
    }
    
    @Override
    public void onDestroy() {
        RxBus.getInstance().unregister(BaseListFragment.TAG, observable);
        super.onDestroy();
    }
}
