package com.example.pinan.otoutiao.function.newstab.model;

import com.example.pinan.otoutiao.base.presenter.IBasePresenter;
import com.example.pinan.otoutiao.base.presenter.IBaseView;

/**
 * @author pinan
 * @date 2017/12/1
 */

public class VideoContentModel {
    public interface View extends IBaseView<Presenter> {
        /**
         * 设置播放器
         */
        void onSetVideoPlay(String url);
    }
    
    public interface Presenter extends IBasePresenter {
        /**
         * 请求数据
         */
        void doLoadVideoData(String videoid);
    }
}
