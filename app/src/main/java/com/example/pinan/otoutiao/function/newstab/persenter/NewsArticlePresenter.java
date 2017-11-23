package com.example.pinan.otoutiao.function.newstab.persenter;

import android.text.TextUtils;

import com.example.pinan.otoutiao.base.http.RetrofitUtils;
import com.example.pinan.otoutiao.function.newstab.api.INewstabApi;
import com.example.pinan.otoutiao.function.newstab.bean.MultiNewsArticleBean;
import com.example.pinan.otoutiao.function.newstab.bean.MultiNewsArticleDataBean;
import com.example.pinan.otoutiao.function.newstab.model.NewsArticleModel;
import com.example.pinan.otoutiao.utils.TimeUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * @author pinan
 * @date 2017/11/17
 */

public class NewsArticlePresenter implements NewsArticleModel.Presenter {
    private NewsArticleModel.View mView;
    private String category;
    private String time;
    private Gson gson = new Gson();
    private Random random = new Random();
    private List<MultiNewsArticleDataBean> dataList = new ArrayList();
    
    public NewsArticlePresenter(NewsArticleModel.View view) {
        this.mView = view;
        this.time = String.valueOf(System.currentTimeMillis() / 1000);
    }
    
    @Override
    public void doLoadData(String... category) {
        try {
            if (this.category == null) {
                this.category = category[0];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 释放内存
        if (dataList.size() > 150) {
            dataList.clear();
        }
        
        getRandom()
            .subscribeOn(Schedulers.io())
            .switchMap(new Function<MultiNewsArticleBean, Observable<MultiNewsArticleDataBean>>() {
                @Override
                public Observable<MultiNewsArticleDataBean> apply(MultiNewsArticleBean multiNewsArticleBean) throws Exception {
                    List<MultiNewsArticleDataBean> dataList = new ArrayList<>();
                    for (MultiNewsArticleBean.DataBean dataBean : multiNewsArticleBean.data) {
                        dataList.add(gson.fromJson(dataBean.content, MultiNewsArticleDataBean.class));
                    }
                    return Observable.fromIterable(dataList);
                }
            })
            .filter(new Predicate<MultiNewsArticleDataBean>() {
                @Override
                public boolean test(MultiNewsArticleDataBean dataBean) throws Exception {
                    time = dataBean.behot_time;
                    if (TextUtils.isEmpty(dataBean.source)) {
                        return false;
                    }
                    try {
                        // 过滤头条问答新闻
                        if (dataBean.source.contains("头条问答")
                            || dataBean.tag.contains("ad")
                            || dataBean.source.contains("悟空问答")) {
                            return false;
                        }
                        // 过滤头条问答新闻
                        if (dataBean.read_count == 0 || TextUtils.isEmpty(dataBean.media_name)) {
                            String title = dataBean.title;
                            if (title.lastIndexOf("？") == title.length() - 1) {
                                return false;
                            }
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    // 过滤重复新闻(与上次刷新的数据比较)
                    for (MultiNewsArticleDataBean bean : dataList) {
                        if (bean.title.equals(dataBean.title)) {
                            return false;
                        }
                    }
                    return true;
                }
            })
            .toList()
            .map(new Function<List<MultiNewsArticleDataBean>, List<MultiNewsArticleDataBean>>() {
                @Override
                public List<MultiNewsArticleDataBean> apply(List<MultiNewsArticleDataBean> list) throws Exception {
                    // 过滤重复新闻(与本次刷新的数据比较,因为使用了2个请求,数据会有重复)
                    for (int i = 0; i < list.size() - 1; i++) {
                        for (int j = list.size() - 1; j > i; j--) {
                            if (list.get(j).title.equals(list.get(i).title)) {
                                list.remove(j);
                            }
                        }
                    }
                    return list;
                }
            })
            .observeOn(AndroidSchedulers.mainThread())
            .compose(mView.<List<MultiNewsArticleDataBean>>bindToLife())
            .subscribe(new Consumer<List<MultiNewsArticleDataBean>>() {
                @Override
                public void accept(List<MultiNewsArticleDataBean> list) throws Exception {
                    if (null != list && list.size() > 0) {
                        doSetAdapter(list);
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
    public void doLoadMoreData() {
        doLoadData();
    }
    
    @Override
    public void doSetAdapter(List<MultiNewsArticleDataBean> dataBeen) {
        dataList.addAll(dataBeen);
        mView.onSetAdapter(dataList);
        mView.onHideLoading();
    }
    
    @Override
    public void doShowNoMore() {
        mView.onShowNoMore();
        mView.onHideLoading();
        
    }
    
    @Override
    public void doShowNetError() {
        mView.onShowNetError();
        mView.onHideLoading();
    }
    
    @Override
    public void doRefresh() {
        if (dataList.size() != 0) {
            dataList.clear();
            time = TimeUtil.getCurrentTimeStamp();
        }
        mView.onShowLoading();
        doLoadData();
    }
    
    /**
     * 模拟随机的数据（用奇偶数来区分）
     */
    private Observable<MultiNewsArticleBean> getRandom() {
        int i = random.nextInt(10);
        if (i % 2 == 0) {
            return RetrofitUtils.getRetrofit().create(INewstabApi.class).getNewsArticle(this.category, this.time);
        } else {
            return RetrofitUtils.getRetrofit().create(INewstabApi.class).getNewsArticle2(this.category, this.time);
        }
    }
}
