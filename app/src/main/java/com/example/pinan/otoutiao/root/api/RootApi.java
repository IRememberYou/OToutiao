package com.example.pinan.otoutiao.root.api;

import com.example.pinan.otoutiao.root.bean.SearchRecommentBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author pinan
 * @date 2017/12/6
 */

public interface RootApi {
    /**
     * 获取搜索推荐
     * http://is.snssdk.com/search/suggest/wap/initial_page/?from=feed&sug_category=__all__&iid=10344168417&device_id=36394312781&format=json
     */
    @GET("http://is.snssdk.com/search/suggest/wap/initial_page/?from=feed&sug_category=__all__&iid=10344168417&device_id=36394312781&format=json")
    Observable<SearchRecommentBean> getSearchRecomment();
}
