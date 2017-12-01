package com.example.pinan.otoutiao.function.newstab.api;

import com.example.pinan.otoutiao.base.Constant;
import com.example.pinan.otoutiao.function.newstab.bean.MediaProfileBean;
import com.example.pinan.otoutiao.function.newstab.bean.MediaWendaBean;
import com.example.pinan.otoutiao.function.newstab.bean.MultiMediaArticleBean;
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
    
    
    
    /**
     * 头条号主页信息
     * https://is.snssdk.com/user/profile/homepage/v3/json/?media_id=4377795668&to_html=0&source=article_top_author&refer=all
     *
     * @param mediaId 头条号ID
     */
    @GET("https://is.snssdk.com/user/profile/homepage/v3/json/?to_html=0&source=article_top_author&refer=all&aid=13")
    Observable<MediaProfileBean> getMediaProfile(
        @Query("media_id") String mediaId);
    
    /**
     * 获取头条号文章
     * https://is.snssdk.com/pgc/ma/?page_type=1&max_behot_time=1495181160&media_id=52445544609&output=json&is_json=1&count=10&from=user_profile_app&version=2&as=479BB4B7254C150&cp=585DB1871ED64E1
     *
     * @param mediaId      头条号ID
     * @param maxBehotTime 时间轴
     */
    @GET("https://is.snssdk.com/pgc/ma/?page_type=1&output=json&is_json=1&count=20&from=user_profile_app&version=2")
    Observable<MultiMediaArticleBean> getMediaArticle(
        @Query("media_id") String mediaId,
        @Query("max_behot_time") String maxBehotTime,
        @Query("as") String as,
        @Query("cp") String cp);
    
    
    /**
     * 获取头条号问答
     * https://is.snssdk.com/wenda/profile/wendatab/brow/?other_id=6619635172&format=json&from_channel=media_channel
     *
     * @param mediaId 头条号ID
     */
    @GET("https://is.snssdk.com/wenda/profile/wendatab/brow/?format=json&from_channel=media_channel")
    Observable<MediaWendaBean> getMediaWenda(
        @Query("other_id") String mediaId);
    
    /**
     * 获取头条号问答(加载更多)
     * http://is.snssdk.com/wenda/profile/wendatab/loadmore/?other_id=53294853357&format=json&from_channel=media_channel&cursor=6428177292098273538&count=10&offset=undefined
     *
     * @param mediaId 头条号ID
     * @param cursor  偏移量
     */
    @GET("http://is.snssdk.com/wenda/profile/wendatab/loadmore/?format=json&from_channel=media_channel&count=10&offset=undefined")
    Observable<MediaWendaBean> getMediaWendaLoadMore(
        @Query("other_id") String mediaId,
        @Query("cursor") String cursor);
    
    
}
