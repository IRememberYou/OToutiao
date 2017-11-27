package com.example.pinan.otoutiao.function.newstab.persenter;

import android.text.TextUtils;

import com.example.pinan.otoutiao.base.http.RetrofitUtils;
import com.example.pinan.otoutiao.function.newstab.api.INewstabApi;
import com.example.pinan.otoutiao.function.newstab.bean.MultiNewsArticleDataBean;
import com.example.pinan.otoutiao.function.newstab.bean.NewsContentBean;
import com.example.pinan.otoutiao.function.newstab.model.NewContentModel;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;


/**
 * @author pinan
 * @date 2017/11/27
 */

public class NewContentPresenter implements NewContentModel.Presenter {
    
    private NewContentModel.View mView;
    
    public NewContentPresenter(NewContentModel.View view) {
        mView = view;
    }
    
    @Override
    public void doRefresh() {
    
    }
    
    @Override
    public void doShowNetError() {
        mView.onHideLoading();
        mView.onShowNetError();
    }
    
    @Override
    public void doLoadData(MultiNewsArticleDataBean bean) {
        long group_id = bean.group_id;
        long item_id = bean.item_id;
        final String url = bean.url;
        
        
        Observable
            .create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> e) throws Exception {
                    try {
                        Response<ResponseBody> response = RetrofitUtils.getRetrofit().create(INewstabApi.class)
                            .getNewsContentRedirectUrl(url)
                            .execute();
                        // 获取重定向后的 URL 用于拼凑API
                        if (response.isSuccessful()) {
                            String httpUrl = response.raw().request().url().toString();
                            if (!TextUtils.isEmpty(httpUrl) && httpUrl.contains("toutiao")) {
                                String api = httpUrl + "info/";
                                e.onNext(api);
                            } else {
                                e.onError(new Throwable());
                            }
                        } else {
                            e.onError(new Throwable());
                        }
                    } catch (IOException e1) {
                        e.onError(new Throwable());
                        e1.printStackTrace();
                    }
                }
            })
            .subscribeOn(Schedulers.io())
            .switchMap(new Function<String, ObservableSource<NewsContentBean>>() {
                
                @Override
                public ObservableSource<NewsContentBean> apply(String s) throws Exception {
                    return RetrofitUtils.getRetrofit().create(INewstabApi.class).getNewsContent(s);
                }
            })
            .map(new Function<NewsContentBean, String>() {
                @Override
                public String apply(NewsContentBean bean) throws Exception {
                    return getHtml(bean);
                }
            })
            .observeOn(AndroidSchedulers.mainThread())
            .compose(mView.<String>bindToLife())
            .subscribe(new Observer<String>() {
                @Override
                public void onSubscribe(Disposable d) {
                
                }
                
                @Override
                public void onNext(String s) {
                    mView.onSetWebView(s, true);
                    
                }
                
                @Override
                public void onError(Throwable e) {
                    mView.onSetWebView(null, false);
                    e.printStackTrace();
                }
                
                @Override
                public void onComplete() {
                    doShowNetError();
                }
            });
        
    }
    
    private String getHtml(NewsContentBean bean) {
        String title = bean.data.title;
        String content = bean.data.content;
        if (content != null) {
            String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/toutiao_light.css\" type=\"text/css\">";
//            if (SettingUtil.getInstance().getIsNightMode()) {
//                css = css.replace("toutiao_light", "toutiao_dark");
//            }
            String html = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">" +
                css +
                "<body>\n" +
                "<article class=\"article-container\">\n" +
                "    <div class=\"article__content article-content\">" +
                "<h1 class=\"article-title\">" +
                title +
                "</h1>" +
                content +
                "    </div>\n" +
                "</article>\n" +
                "</body>\n" +
                "</html>";
            
            return html;
        } else {
            return null;
        }
    }
}
