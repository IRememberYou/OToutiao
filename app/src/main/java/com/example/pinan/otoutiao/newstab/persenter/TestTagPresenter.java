package com.example.pinan.otoutiao.newstab.persenter;

import android.text.TextUtils;
import android.widget.Toast;

import com.example.pinan.otoutiao.base.BaseApplication;
import com.example.pinan.otoutiao.base.RetrofitHelp;
import com.example.pinan.otoutiao.newstab.api.INewstabApi;
import com.example.pinan.otoutiao.newstab.bean.MultiNewsArticleBean;
import com.example.pinan.otoutiao.newstab.bean.MultiNewsArticleDataBean;
import com.example.pinan.otoutiao.newstab.model.TestTagModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * @author pinan
 * @date 2017/11/17
 */

public class TestTagPresenter implements TestTagModel.Presenter {
    private TestTagModel.View mView;
    private String category;
    private String time;
    private Gson gson = new Gson();
    private Random random = new Random();
    private List<MultiNewsArticleDataBean> dataList = new ArrayList();
    
    public TestTagPresenter() {
    
    }
    
    public TestTagPresenter(TestTagModel.View view) {
        this.mView = view;
        this.time = String.valueOf(System.currentTimeMillis() / 1000);
    }
    
    @Override
    public void doLoadData(String... category) {
        mView.onShowLoading();
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
            .switchMap(new Function<MultiNewsArticleBean, ObservableSource<MultiNewsArticleDataBean>>() {
                @Override
                public ObservableSource<MultiNewsArticleDataBean> apply(MultiNewsArticleBean multiNewsArticleBean) throws Exception {
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
                    //过滤头条问答新闻
                    if (dataBean.source.contains("头条问答") || dataBean.tag.contains("ad") || dataBean.source.contains("悟空问答")) {
                        return false;
                    }
                    
                    if (dataBean.read_count == 0 || TextUtils.isEmpty(dataBean.media_name)) {
                        String title = dataBean.title;
                        if (title.lastIndexOf("?") == title.length() - 1) {
                            return false;
                        }
                    }
                    return true;
                }
            })
            .toList()
            .observeOn(AndroidSchedulers.mainThread())
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
                }
            });
    }
    
    
    @Override
    public void doLoadMoreData() {
    
    }
    
    @Override
    public void doSetAdapter(List<MultiNewsArticleDataBean> dataBeen) {
        dataList.addAll(dataBeen);
        mView.setAdapter(dataList);
        mView.onHideLoading();
    }
    
    @Override
    public void doShowNoMore() {
        Toast.makeText(BaseApplication.sContext, "没有更多数据了", Toast.LENGTH_SHORT).show();
        mView.onHideLoading();
    }
    
    @Override
    public void doShowNetError() {
        Toast.makeText(BaseApplication.sContext, "网络错误", Toast.LENGTH_SHORT).show();
    }
    
    
    /**
     * 模拟随机的数据（用奇偶数来区分）
     */
    private Observable<MultiNewsArticleBean> getRandom() {
        int i = random.nextInt(10);
        if (i % 2 == 0) {
            return RetrofitHelp.getRetrofit().create(INewstabApi.class).getNewsArticle(category, time);
            
        } else {
            return RetrofitHelp.getRetrofit().create(INewstabApi.class).getNewsArticle2(category, time);
        }
    }
    
    
    @Override
    public void doRefresh() {
    }
}
