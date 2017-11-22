package com.example.pinan.otoutiao.model.bean;

import android.support.annotation.NonNull;

import com.example.pinan.otoutiao.function.newstab.bean.MultiNewsArticleDataBean;
import com.example.pinan.otoutiao.function.newstab.bean.NewsCommentBean;
import com.example.pinan.otoutiao.model.binder.NewsArticleImgViewBinder;
import com.example.pinan.otoutiao.model.binder.NewsArticleTextViewBinder;
import com.example.pinan.otoutiao.model.binder.NewsArticleVideoViewBinder;
import com.example.pinan.otoutiao.model.binder.NewsCommentViewBinder;

import me.drakeet.multitype.ClassLinker;
import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Meiji on 2017/6/9.
 */

public class Register {
    
    public static void registerNewsArticleItem(@NonNull MultiTypeAdapter adapter) {
        // 一个类型对应多个 ItemViewBinder
        adapter.register(MultiNewsArticleDataBean.class)
            .to(new NewsArticleImgViewBinder(), new NewsArticleVideoViewBinder(), new NewsArticleTextViewBinder())
            .withClassLinker(new ClassLinker<MultiNewsArticleDataBean>() {
                @NonNull
                @Override
                public Class<? extends ItemViewBinder<MultiNewsArticleDataBean, ?>> index(@NonNull MultiNewsArticleDataBean item) {
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
    
    public static void registerNewsCommentItem(@NonNull MultiTypeAdapter adapter) {
        adapter.register(NewsCommentBean.DataBean.CommentBean.class, new NewsCommentViewBinder());
        adapter.register(LoadingBean.class, new LoadingViewBinder());
        adapter.register(LoadingEndBean.class, new LoadingEndViewBinder());
    }
}
