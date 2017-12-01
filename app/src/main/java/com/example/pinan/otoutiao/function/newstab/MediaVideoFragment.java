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

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

;

/**
 * @author pinan
 * @date 2017/12/1
 */

public class MediaVideoFragment extends BaseListFragment<MediaArticleModel.Presenter> implements MediaArticleModel.View {
    private static final String KEY_MEDIA_VIDEO = "mediavideo";
    private String mediaId;
    
    public static Fragment newInstance(String media_id) {
        MediaVideoFragment fragment = new MediaVideoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_MEDIA_VIDEO, media_id);
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
        Register.registerMediaArticleItem(adapter);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (canLoadMore) {
                    canLoadMore = false;
                    presenter.doLoadMoreData(MediaArticlePresenter.TYPE_VIDEO);
                }
            }
        });
    }
    
    
    @Override
    protected void initData() throws NullPointerException {
        mediaId = getArguments().getString(KEY_MEDIA_VIDEO);
    }
    
    @Override
    public void fetchData() {
        onLoadData();
    }
    
    @Override
    public void onLoadData() {
        onShowLoading();
        presenter.doLoadVideo(mediaId);
    }
    
    @Override
    public void onRefresh() {
        presenter.doRefresh(MediaArticlePresenter.TYPE_VIDEO);
    }
    
    @Override
    public void onSetAdapter(List<?> list) {
        if (oldItems.size() > 0) {
            Object lastItem = oldItems.get(oldItems.size() - 1);
            if (lastItem instanceof LoadingEndBean || lastItem instanceof LoadingBean) {
                oldItems.remove(lastItem);
            }
        }
        Items newItems = new Items(list);
        oldItems.addAll(newItems);
        oldItems.add(new LoadingBean());
        adapter.notifyDataSetChanged();
        canLoadMore = true;
    }
}
