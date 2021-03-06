package com.example.pinan.otoutiao.function.newstab.persenter;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.example.pinan.otoutiao.base.http.RetrofitUtils;
import com.example.pinan.otoutiao.function.newstab.api.INewstabApi;
import com.example.pinan.otoutiao.function.newstab.bean.VideoContentBean;
import com.example.pinan.otoutiao.function.newstab.model.VideoContentModel;

import java.util.Random;
import java.util.zip.CRC32;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author pinan
 * @date 2017/12/1
 */

public class VideoContentPresenter extends NewsCommentPresenter implements VideoContentModel.Presenter {
    
    
    private final VideoContentModel.View mView;
    
    public VideoContentPresenter(VideoContentModel.View view) {
        super(view);
        mView = view;
    }
    
    @Override
    public void doRefresh() {
    
    }
    
    @Override
    public void doShowNetError() {
        mView.onShowNetError();
        mView.onHideLoading();
    }
    
    @Override
    public void doLoadVideoData(String videoid) {
        String url = getVideoContentApi(videoid);
        RetrofitUtils.getRetrofit().create(INewstabApi.class)
            .getVideoContent(url)
            .subscribeOn(Schedulers.io())
            .map(new Function<VideoContentBean, String>() {
                @Override
                public String apply(VideoContentBean videoContentBean) throws Exception {
                    VideoContentBean.DataBean.VideoListBean video_list = videoContentBean.data.video_list;
                    String main_url = "";
                    if (video_list.video_1 != null) {
                        main_url = video_list.video_1.main_url;
                    } else if (video_list.video_2 != null) {
                        main_url = video_list.video_2.main_url;
                    }
                    return TextUtils.isEmpty(main_url) ? null : new String(Base64.decode(main_url.getBytes(), Base64.DEFAULT));
                }
            })
            .compose(mView.<String>bindToLife())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<String>() {
                @Override
                public void accept(String url) throws Exception {
                    Log.d("VideoContentPresenter", url);
                    doSetVideoPlay(url);
                    
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    doShowNetError();
                    
                    throwable.printStackTrace();
                }
            });
    }
    
    @Override
    public void doSetVideoPlay(String url) {
        mView.onSetVideoPlay(url);
    }
    
    private String getVideoContentApi(String videoid) {
        String VIDEO_HOST = "http://ib.365yg.com";
        String VIDEO_URL = "/video/urls/v/1/toutiao/mp4/%s?r=%s";
        String r = getRandom();
        String s = String.format(VIDEO_URL, videoid, r);
        CRC32 crc32 = new CRC32();
        crc32.update(s.getBytes());
        String crcString = crc32.getValue() + "";
        String url = VIDEO_HOST + s + "&s=" + crcString;
        return url;
    }
    
    public String getRandom() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}