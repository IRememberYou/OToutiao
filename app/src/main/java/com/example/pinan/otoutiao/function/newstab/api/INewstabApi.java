package com.example.pinan.otoutiao.function.newstab.api;

import com.example.pinan.otoutiao.function.newstab.bean.MultiNewsArticleBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 *
 * @author pinan
 * @date 2017/11/16
 */

public interface INewstabApi {
    
    @GET("http://is.snssdk.com/api/news/feed/v62/?iid=5034850950&device_id=6096495334&refer=1&count=20&aid=13")
    Observable<MultiNewsArticleBean> getNewsArticle(
        @Query("category") String category,
        @Query("max_behot_time") String maxBehotTime);
    
    @GET("http://lf.snssdk.com/api/news/feed/v62/?iid=12507202490&device_id=37487219424&refer=1&count=20&aid=13")
    Observable<MultiNewsArticleBean> getNewsArticle2(
        @Query("category") String category,
        @Query("max_behot_time") String maxBehotTime);
}
