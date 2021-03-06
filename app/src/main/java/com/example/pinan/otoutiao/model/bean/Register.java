package com.example.pinan.otoutiao.model.bean;

import android.support.annotation.NonNull;

import com.example.pinan.otoutiao.function.newstab.bean.MediaProfileBean;
import com.example.pinan.otoutiao.function.newstab.bean.MediaWendaBean;
import com.example.pinan.otoutiao.function.newstab.bean.MultiMediaArticleBean;
import com.example.pinan.otoutiao.function.newstab.bean.MultiNewsArticleDataBean;
import com.example.pinan.otoutiao.function.newstab.bean.NewsCommentBean;
import com.example.pinan.otoutiao.model.binder.LoadingEndViewBinder;
import com.example.pinan.otoutiao.model.binder.LoadingViewBinder;
import com.example.pinan.otoutiao.model.binder.MediaArticleHeaderViewBinder;
import com.example.pinan.otoutiao.model.binder.MediaArticleImgViewBinder;
import com.example.pinan.otoutiao.model.binder.MediaArticleTextViewBinder;
import com.example.pinan.otoutiao.model.binder.MediaArticleVideoViewBinder;
import com.example.pinan.otoutiao.model.binder.MediaWendaViewBinder;
import com.example.pinan.otoutiao.model.binder.NewsArticleImgViewBinder;
import com.example.pinan.otoutiao.model.binder.NewsArticleTextViewBinder;
import com.example.pinan.otoutiao.model.binder.NewsArticleVideoViewBinder;
import com.example.pinan.otoutiao.model.binder.NewsCommentViewBinder;
import com.example.pinan.otoutiao.model.binder.PhotoBinder;

import me.drakeet.multitype.ClassLinker;
import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * @author pinan
 */

public class Register {
    public static void registerNewsArticleItem(@NonNull MultiTypeAdapter adapter) {
        // 一个类型对应多个 ItemViewBinder
        adapter.register(MultiNewsArticleDataBean.class)
            .to(new NewsArticleImgViewBinder(),
                new NewsArticleVideoViewBinder(),
                new NewsArticleTextViewBinder())
            .withClassLinker(new ClassLinker<MultiNewsArticleDataBean>() {
                @NonNull
                @Override
                public Class<? extends ItemViewBinder<MultiNewsArticleDataBean, ?>> index(int position, @NonNull MultiNewsArticleDataBean item) {
                    if (item.has_video) {
                        return NewsArticleVideoViewBinder.class;
                    }
                    if (null != item.image_list && item.image_list.size() > 0) {
                        return NewsArticleImgViewBinder.class;
                    }
                    return NewsArticleTextViewBinder.class;
                }
            });
        adapter.register(LoadingBean.class, new LoadingViewBinder());
        adapter.register(LoadingEndBean.class, new LoadingEndViewBinder());
    }
    
    
    public static void registerPhotoItem(@NonNull MultiTypeAdapter adapter) {
        adapter.register(PhotoBean.class, new PhotoBinder());
        adapter.register(LoadingBean.class, new LoadingViewBinder());
        adapter.register(LoadingEndBean.class, new LoadingEndViewBinder());
    }
    
    public static void registerNewsCommendItem(MultiTypeAdapter adapter) {
        adapter.register(NewsCommentBean.DataBean.CommentBean.class, new NewsCommentViewBinder());
        adapter.register(LoadingBean.class, new LoadingViewBinder());
        adapter.register(LoadingEndBean.class, new LoadingEndViewBinder());
    }
    
    public static void registerMediaArticleItem(MultiTypeAdapter adapter) {
        adapter.register(MultiMediaArticleBean.DataBean.class)
            .to(new MediaArticleImgViewBinder(),
                new MediaArticleVideoViewBinder(),
                new MediaArticleTextViewBinder())
            .withClassLinker(new ClassLinker<MultiMediaArticleBean.DataBean>() {
                @NonNull
                @Override
                public Class<? extends ItemViewBinder<MultiMediaArticleBean.DataBean, ?>> index(int position, @NonNull MultiMediaArticleBean.DataBean bean) {
                    if (bean.has_video) {
                        return MediaArticleVideoViewBinder.class;
                    }
                    if (bean.image_list != null && bean.image_list.size() > 0) {
                        return MediaArticleImgViewBinder.class;
                    }
                    return MediaArticleTextViewBinder.class;
                }
            });
        adapter.register(MediaProfileBean.DataBean.class, new MediaArticleHeaderViewBinder());
        adapter.register(LoadingBean.class, new LoadingViewBinder());
        adapter.register(LoadingEndBean.class, new LoadingEndViewBinder());
    }
    
    public static void registerMediaWendaItem(@NonNull MultiTypeAdapter adapter) {
        adapter.register(MediaWendaBean.AnswerQuestionBean.class, new MediaWendaViewBinder());
        adapter.register(LoadingBean.class, new LoadingViewBinder());
        adapter.register(LoadingEndBean.class, new LoadingEndViewBinder());
    }
}
