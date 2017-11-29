package com.example.pinan.otoutiao.function.newstab.api;

import com.example.pinan.otoutiao.base.Constant;
import com.example.pinan.otoutiao.function.newstab.bean.MultiNewsArticleBean;
import com.example.pinan.otoutiao.function.newstab.bean.NewsCommentBean;
import com.example.pinan.otoutiao.function.newstab.bean.NewsContentBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.Url;

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
    
    
    /**
     * 获取新闻内容的API
     */
    @GET
    @Headers("User-Agent:" + Constant.USER_AGENT_MOBILE)
    Call<ResponseBody> getNewsContentRedirectUrl(@Url String url);
    /**
     * 获取新闻HTML内容
     * http://m.toutiao.com/i6364969235889783298/info/
     */
    @GET
    Observable<NewsContentBean> getNewsContent(@Url String url);
    
    
    
    /**
     * 获取新闻评论
     * 按热度排序
     * http://is.snssdk.com/article/v53/tab_comments/?group_id=6314103921648926977&offset=0&tab_index=0
     * 按时间排序
     * http://is.snssdk.com/article/v53/tab_comments/?group_id=6314103921648926977&offset=0&tab_index=1
     *
     * @param groupId 新闻ID
     * @param offset  偏移量
     */
    @GET("http://is.snssdk.com/article/v53/tab_comments/")
    Observable<NewsCommentBean> getNewsComment(
        @Query("group_id") String groupId,
        @Query("offset") int offset);
}
