package com.example.pinan.otoutiao.function.newstab.persenter;

import android.text.TextUtils;

import com.example.pinan.otoutiao.base.http.RetrofitUtils;
import com.example.pinan.otoutiao.function.newstab.api.INewstabApi;
import com.example.pinan.otoutiao.function.newstab.bean.MediaProfileBean;
import com.example.pinan.otoutiao.function.newstab.model.MediaHomeModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author pinan
 * @date 2017/11/29
 */

public class MediaHomePresenter implements MediaHomeModel.Presenter {
    
    private final MediaHomeModel.View mView;
    
    public MediaHomePresenter(MediaHomeModel.View view) {
        mView = view;
    }
    
    
    @Override
    public void doRefresh() {
    
    }
    
    @Override
    public void doShowNetError() {
    
    }
    
    @Override
    public void doLoadData(String mediaId) {
        if (TextUtils.isEmpty(mediaId)) {
            mView.onError();
            return;
        }
        RetrofitUtils.getRetrofit()
            .create(INewstabApi.class)
            .getMediaProfile(mediaId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(mView.<MediaProfileBean>bindToLife())
            .subscribe(new Consumer<MediaProfileBean>() {
                @Override
                public void accept(MediaProfileBean bean) throws Exception {
                    mView.onUpData(bean);
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    mView.onError();
                    throwable.printStackTrace();
                }
            });
    }
}
