package com.example.pinan.otoutiao.root.adapter;

import android.view.View;
import android.widget.Toast;

import com.example.pinan.otoutiao.R;
import com.example.pinan.otoutiao.base.ErrorAction;
import com.example.pinan.otoutiao.database.dao.SearchHistoryDao;
import com.example.pinan.otoutiao.root.bean.SearchHistoryBean;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author pinan
 * @date 2017/12/6
 */

public class SearchHistoryAdapter extends QuickBaseAdapter<SearchHistoryBean, ListViewHolder> implements View.OnClickListener {
    public SearchHistoryAdapter(List<SearchHistoryBean> datas) {
        super(R.layout.item_search_history, datas);
    }
    
    @Override
    protected void convert(ListViewHolder holder, SearchHistoryBean bean) {
        holder.setText(R.id.tv_keyword, bean.keyword)
            .setOnClickListener(R.id.iv_close, this, bean)
            .setOnClickListener(R.id.itme_view, this, bean);
    }
    
    public void updateDataSource(List<SearchHistoryBean> beans) {
        addData(beans);
    }
    
    @Override
    public void onClick(View v) {
        int id = v.getId();
        final SearchHistoryBean bean = (SearchHistoryBean) v.getTag();
        if (R.id.tv_keyword == id) {
            Toast.makeText(v.getContext(), bean.keyword, Toast.LENGTH_SHORT).show();
        } else if (R.id.iv_close == id) {
            Observable
                .create(new ObservableOnSubscribe<List<SearchHistoryBean>>() {
                    @Override
                    public void subscribe(ObservableEmitter<List<SearchHistoryBean>> e) throws Exception {
                        SearchHistoryDao dao = new SearchHistoryDao();
                        boolean delete = dao.delete(bean.keyword);
                        if (delete) {
                            List<SearchHistoryBean> beans = dao.queryPager(0, 20);
                            e.onNext(beans);
                        } else {
                            e.onError(new Throwable("删除失败"));
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<SearchHistoryBean>>() {
                    @Override
                    public void accept(List<SearchHistoryBean> beans) throws Exception {
                        addData(beans);
                    }
                }, ErrorAction.error());
        }
    }
}
