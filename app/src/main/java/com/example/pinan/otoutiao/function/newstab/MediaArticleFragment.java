package com.example.pinan.otoutiao.function.newstab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.pinan.otoutiao.base.init.BaseListFragment;
import com.example.pinan.otoutiao.function.newstab.bean.MediaProfileBean;
import com.example.pinan.otoutiao.function.newstab.model.MediaArticleModel;
import com.example.pinan.otoutiao.function.newstab.persenter.MediaArticlePresenter;
import com.example.pinan.otoutiao.model.bean.LoadingBean;
import com.example.pinan.otoutiao.model.bean.LoadingEndBean;
import com.example.pinan.otoutiao.model.bean.OnLoadMoreListener;
import com.example.pinan.otoutiao.model.bean.Register;

import java.util.List;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

import static com.example.pinan.otoutiao.function.newstab.persenter.MediaArticlePresenter.TYPE_ARTICLE;

/**
 * @author pinan
 * @date 2017/11/29
 */

public class MediaArticleFragment extends BaseListFragment<MediaArticleModel.Presenter> implements MediaArticleModel.View {
    private static final String KEY_MEDIA_PROFILE_BEAN = "mediaprofilebean";
    private MediaProfileBean.DataBean mBean;
    
    public static Fragment newInstance(MediaProfileBean.DataBean bean) {
        MediaArticleFragment fragment = new MediaArticleFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_MEDIA_PROFILE_BEAN, bean);
        fragment.setArguments(bundle);
        return fragment;
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
                    presenter.doLoadMoreData(TYPE_ARTICLE);
                }
            }
        });
    }
    
    @Override
    protected void initData() {
        mBean = (MediaProfileBean.DataBean) getArguments().getSerializable(KEY_MEDIA_PROFILE_BEAN);
        if (mBean == null) {
            onShowNetError();
        }
    }
    
    @Override
    public void fetchData() {
        onLoadData();
    }
    
    @Override
    public void onLoadData() {
        presenter.doLoadArticle(mBean.media_id);
        
    }
    
    @Override
    public void setPresenter(MediaArticleModel.Presenter presenter) {
        if (presenter == null) {
            this.presenter = new MediaArticlePresenter(this);
        }
    }
    
    @Override
    public void onSetAdapter(List<?> list) {
        if (oldItems.size() > 0) {
            Object lastItem = oldItems.get(oldItems.size() - 1);
            if (lastItem instanceof LoadingBean || lastItem instanceof LoadingEndBean) {
                oldItems.remove(lastItem);
            }
            Object firstItem = oldItems.get(0);
            if (firstItem instanceof MediaProfileBean.DataBean) {
                oldItems.remove(firstItem);
            }
        }
        Items newsItems = new Items(list);
        oldItems.add(0, mBean);
        oldItems.addAll(newsItems);
        oldItems.add(oldItems.size(), new LoadingBean());
        adapter.notifyDataSetChanged();
        canLoadMore = true;
    }
    
    @Override
    public void onRefresh() {
        onShowLoading();
        presenter.doRefresh(TYPE_ARTICLE);
    }
}
