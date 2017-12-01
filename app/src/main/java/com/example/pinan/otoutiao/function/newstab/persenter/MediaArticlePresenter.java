package com.example.pinan.otoutiao.function.newstab.persenter;

import com.example.pinan.otoutiao.base.Constant;
import com.example.pinan.otoutiao.base.http.RetrofitUtils;
import com.example.pinan.otoutiao.function.newstab.api.INewstabApi;
import com.example.pinan.otoutiao.function.newstab.bean.MediaWendaBean;
import com.example.pinan.otoutiao.function.newstab.bean.MultiMediaArticleBean;
import com.example.pinan.otoutiao.function.newstab.model.MediaArticleModel;
import com.example.pinan.otoutiao.utils.TimeUtil;
import com.example.pinan.otoutiao.utils.ToutiaoUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author pinan
 * @date 2017/11/29
 */

public class MediaArticlePresenter implements MediaArticleModel.Presenter {
    public static final int TYPE_ARTICLE = 0;
    public static final int TYPE_VIDEO = 1;
    public static final int TYPE_WENDA = 2;
    private String articleTime;
    private String videoTime;
    private MediaArticleModel.View mView;
    private String mMediaId;
    private String mWendaCursor;
    private int mWendatotal;
    
    private List<MultiMediaArticleBean.DataBean> articleList = new ArrayList<>();
    private List<MediaWendaBean.AnswerQuestionBean> wendaList = new ArrayList<>();
    
    
    public MediaArticlePresenter(MediaArticleModel.View view) {
        mView = view;
        this.articleTime = TimeUtil.getCurrentTimeStamp();
        this.videoTime = TimeUtil.getCurrentTimeStamp();
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
    public void doLoadArticle(String... mediaId) {
        if (mMediaId == null) {
            mMediaId = mediaId[0];
        }
        Map<String, String> asCp = ToutiaoUtil.getAsCp();
        
        RetrofitUtils.getRetrofit().create(INewstabApi.class)
            .getMediaArticle(mMediaId, articleTime, asCp.get(Constant.AS), asCp.get(Constant.CP))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(mView.<MultiMediaArticleBean>bindToLife())
            .subscribe(new Consumer<MultiMediaArticleBean>() {
                @Override
                public void accept(MultiMediaArticleBean bean) throws Exception {
                    articleTime = bean.next.max_behot_time;
                    if (bean.data != null && bean.data.size() > 0) {
                        doSetAdapter(bean.data);
                    } else {
                        doShowNoMore();
                    }
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
    public void doLoadVideo(String... mediaId) {
    
    }
    
    @Override
    public void doLoadWenda(String... mediaId) {
        if (mMediaId == null) {
            mMediaId = mediaId[0];
        }
        RetrofitUtils.getRetrofit().create(INewstabApi.class)
            .getMediaWenda(mMediaId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(mView.<MediaWendaBean>bindToLife())
            .subscribe(new Consumer<MediaWendaBean>() {
                @Override
                public void accept(MediaWendaBean mediaWendaBean) throws Exception {
                    mWendatotal = mediaWendaBean.total;
                    mWendaCursor = mediaWendaBean.cursor;
                    List<MediaWendaBean.AnswerQuestionBean> answer_question = mediaWendaBean.answer_question;
                    if (answer_question != null && answer_question.size() > 0) {
                        mView.onSetAdapter(answer_question);
                    } else {
                        mView.onShowNoMore();
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    mView.onShowNetError();
                }
            });
    }
    
    @Override
    public void doLoadMoreData(int type) {
        switch (type) {
            case TYPE_ARTICLE:
                doLoadArticle();
                break;
            case TYPE_VIDEO:
                doLoadVideo();
                break;
            case TYPE_WENDA:
                if (wendaList.size() < mWendatotal) {
                    RetrofitUtils.getRetrofit().create(INewstabApi.class)
                        .getMediaWendaLoadMore(mMediaId,mWendaCursor)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(mView.<MediaWendaBean>bindToLife())
                        .subscribe(new Consumer<MediaWendaBean>() {
                            @Override
                            public void accept(MediaWendaBean mediaWendaBean) throws Exception {
                                mWendatotal = mediaWendaBean.total;
                                mWendaCursor = mediaWendaBean.cursor;
                                List<MediaWendaBean.AnswerQuestionBean> answer_question = mediaWendaBean.answer_question;
                                if (answer_question != null && answer_question.size() > 0) {
                                    mView.onSetAdapter(answer_question);
                                }else {
                                    mView.onShowNoMore();
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                mView.onShowNetError();
                            }
                        });
                }
                break;
            default:
                break;
        }
        
    }
    
    @Override
    public void doShowNoMore() {
        mView.onShowNoMore();
        mView.onHideLoading();
    }
    
    @Override
    public void doSetWendaAdapter(List<MediaWendaBean.AnswerQuestionBean> list) {
        wendaList.addAll(list);
        mView.onSetAdapter(wendaList);
        mView.onHideLoading();
    }
    
    @Override
    public void doSetAdapter(List<MultiMediaArticleBean.DataBean> list) {
        articleList.addAll(list);
        mView.onSetAdapter(articleList);
        mView.onHideLoading();
    }
    
    @Override
    public void doRefresh(int type) {
        switch (type) {
            case TYPE_ARTICLE:
                if (articleList.size() > 0) {
                    articleList.clear();
                    articleTime = TimeUtil.getCurrentTimeStamp();
                }
                doLoadArticle();
                break;
            case TYPE_VIDEO:
//                if (videoList.size() > 0) {
//                    videoList.clear();
//                    videoTime = TimeUtil.getCurrentTimeStamp();
//                }
//                doLoadVideo();
                break;
            case TYPE_WENDA:
                if (wendaList.size() > 0) {
                    wendaList.clear();
                }
                doLoadWenda();
                break;
            default:
                break;
        }
    }
}
