package com.example.pinan.otoutiao.function.newstab.model;

/**
 * @author pinan
 * @date 2017/12/1
 */

public class VideoContentModel {
    public interface View extends NewsCommentModel.View{
        /**
         * 设置播放器
         */
        void onSetVideoPlay(String url);
        /**
         * 请求数据
         */
        void onLoadData();
    }
    
    public interface Presenter extends NewsCommentModel.Presenter {
        /**
         * 请求数据
         */
        void doLoadVideoData(String videoid);
        void doSetVideoPlay(String url);
    }
}
