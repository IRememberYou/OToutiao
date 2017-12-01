package com.example.pinan.otoutiao.function.newstab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.pinan.otoutiao.base.init.BaseListFragment;
import com.example.pinan.otoutiao.function.newstab.model.MediaArticleModel;
import com.example.pinan.otoutiao.function.newstab.persenter.MediaArticlePresenter;
import com.example.pinan.otoutiao.model.bean.LoadingBean;
import com.example.pinan.otoutiao.model.bean.LoadingEndBean;
import com.example.pinan.otoutiao.model.bean.OnLoadMoreListener;
import com.example.pinan.otoutiao.model.bean.Register;

import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * @author pinan
 * @date 2017/12/1
 */

public class MediaWendaFragment extends BaseListFragment<MediaArticleModel.Presenter> implements MediaArticleModel.View {
    private static final String KEY_MEDIA_ID = "mediaid";
    private String mMediaId;
    
    public static Fragment newInstance(String mediaId) {
        MediaWendaFragment fragment = new MediaWendaFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_MEDIA_ID, mediaId);
        fragment.setArguments(bundle);
        return fragment;
        
    }
    
    @Override
    public void setPresenter(MediaArticleModel.Presenter presenter) {
        if (presenter == null) {
            this.presenter = new MediaArticlePresenter(this);
        }
    }
    
    @Override
    protected void initView(View view) {
        super.initView(view);
        adapter = new MultiTypeAdapter(oldItems);
        Register.registerMediaWendaItem(adapter);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (canLoadMore) {
                    canLoadMore = false;
                    presenter.doLoadMoreData(MediaArticlePresenter.TYPE_WENDA);
                }
            }
        });
        
    }
    
    @Override
    public void fetchData() {
        onLoadData();
    }
    
    @Override
    public void onLoadData() {
        onShowLoading();
        presenter.doLoadWenda(mMediaId);
    }
    
    @Override
    protected void initData() throws NullPointerException {
        mMediaId = getArguments().getString(KEY_MEDIA_ID);
    }
    
    @Override
    public void onRefresh() {
        presenter.doRefresh(MediaArticlePresenter.TYPE_WENDA);
    }
    
    @Override
    public void onSetAdapter(List list) {
        removeLastItem();
        oldItems.addAll(list);
        oldItems.add(new LoadingBean());
        adapter.notifyDataSetChanged();
        canLoadMore = true;
    }
    
    private void removeLastItem() {
        if (oldItems.size() > 0) {
            Object o = oldItems.get(oldItems.size() - 1);
            if (o instanceof LoadingEndBean || o instanceof LoadingBean) {
                oldItems.remove(o);
            }
        }
    }
    
    
}
