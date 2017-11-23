package com.example.pinan.otoutiao.function.phototab;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.pinan.otoutiao.R;
import com.example.pinan.otoutiao.base.init.BaseActivity;
import com.example.pinan.otoutiao.base.init.BaseFragment;
import com.example.pinan.otoutiao.model.bean.DiffCallback;
import com.example.pinan.otoutiao.model.bean.LoadingBean;
import com.example.pinan.otoutiao.model.bean.LoadingEndBean;
import com.example.pinan.otoutiao.model.bean.OnLoadMoreListener;
import com.example.pinan.otoutiao.model.bean.PhotoBean;
import com.example.pinan.otoutiao.model.bean.Register;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * @author pinan
 * @date 2017/11/15
 */

public class PhotoTabFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    private Items mOldList = new Items();
    private MultiTypeAdapter mAdapter;
    private boolean canLoadMore;
    
    public static PhotoTabFragment getInstance() {
        return new PhotoTabFragment();
    }
    
    @Override
    public void setPresenter(Object presenter) {
    
    }
    
    @Override
    protected int attachLayoutId() {
        return R.layout.photo_tab_fragment;
    }
    
    @Override
    protected void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        mAdapter = new MultiTypeAdapter(mOldList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        Register.registerPhotoItem(mAdapter);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (canLoadMore) {
                    List olds = new ArrayList(mOldList);
                    List<PhotoBean> list = doLoadMoreData();
                    if (list != null) {
                        olds.addAll(list);
                        setAdapter(olds);
                    } else {
                        canLoadMore = false;
                        if (mOldList.size() > 0) {
                            Items newItems = new Items(mOldList);
                            newItems.remove(newItems.size() - 1);
                            newItems.add(new LoadingEndBean());
                        } else if (mOldList.size() == 0) {
                            mOldList.add(new LoadingEndBean());
                        }
                        mAdapter.setItems(mOldList);
                        mRecyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ((BaseActivity) mContext).runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                mAdapter.notifyDataSetChanged();
                                            }
                                        });
                                    }
                                }).start();
                                
                            }
                        });
                        
                    }
                }
            }
        });
        mRefreshLayout.setOnRefreshListener(this);
        onRefresh();
    }
    
    @Override
    protected void initData() throws NullPointerException {
    
    
    }
    
    @Override
    public void onShowLoading() {
    }
    
    @Override
    public void onHideLoading() {
    }
    
    @Override
    public void onShowNetError() {
    }
    
    private int count = 0;
    
    public List<PhotoBean> doLoadMoreData() {
        if (count >= 4) {
            return null;
        }
        List<PhotoBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new PhotoBean("侠 " + count + " 岚" + i, (64 + i)));
        }
        count++;
        return list;
    }
    
    public List<PhotoBean> doLoadData() {
        List<PhotoBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new PhotoBean("侠岚" + ((char) (65 + i)), (65 + i)));
        }
        return list;
    }
    
    @Override
    public void onRefresh() {
        Log.d("PhotoTabFragment", "我执行了");
        count = 0;
        setAdapter(doLoadData());
    }
    
    public void setAdapter(List list) {
        mRefreshLayout.setRefreshing(false);
        Items newList = new Items(list);
        newList.add(new LoadingBean());
        DiffCallback.notifyDataSetChanged(mOldList, newList, DiffCallback.PHOTO, mAdapter);
        mOldList.clear();
        mOldList.addAll(newList);
        canLoadMore = true;
    }
}
