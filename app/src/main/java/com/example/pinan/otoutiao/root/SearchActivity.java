package com.example.pinan.otoutiao.root;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pinan.otoutiao.R;
import com.example.pinan.otoutiao.base.ErrorAction;
import com.example.pinan.otoutiao.base.http.RetrofitUtils;
import com.example.pinan.otoutiao.base.init.BaseActivity;
import com.example.pinan.otoutiao.database.dao.SearchHistoryDao;
import com.example.pinan.otoutiao.root.adapter.SearchHistoryAdapter;
import com.example.pinan.otoutiao.root.api.RootApi;
import com.example.pinan.otoutiao.root.bean.SearchHistoryBean;
import com.example.pinan.otoutiao.root.bean.SearchRecommentBean;
import com.google.android.flexbox.FlexboxLayout;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author pinan
 * @date 2017/11/15
 */

public class SearchActivity extends BaseActivity implements View.OnClickListener {
    SearchHistoryDao dao = new SearchHistoryDao();
    private FlexboxLayout mFlexboxLayout;
    private ListView mHistoryList;
    private SearchHistoryAdapter mListoryAdapter;
    private SearchView mSearchView;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        getSearchHotWord();
        getSearchHistory();
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        
        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(item);
        //关联检索配置与 searchActivity
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(new ComponentName(getApplication(), SearchActivity.class));
        mSearchView.setSearchableInfo(searchableInfo);
        mSearchView.onActionViewExpanded();
        //设置搜索文字的样式
        EditText searchEditText = mSearchView.findViewById(R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.textColorPrimary));
        searchEditText.setHintTextColor(getResources().getColor(R.color.textColorPrimary));
        searchEditText.setBackgroundColor(Color.WHITE);
        setOnQuenyTextChangeListener(searchEditText);
        return super.onCreateOptionsMenu(menu);
    }
    
    private void setOnQuenyTextChangeListener(EditText searchEditText) {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            
            }
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    Toast.makeText(SearchActivity.this, "s:" + s, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SearchActivity.this, "为空", Toast.LENGTH_SHORT).show();
                }
            }
            
            @Override
            public void afterTextChanged(Editable s) {
            
            }
        });
        
        if (mSearchView.isSubmitButtonEnabled()) {
        
        }
        mSearchView.findViewById(R.id.search_close_btn).setOnClickListener(this);
        
        mSearchView.findViewById(R.id.search_go_btn).setOnClickListener(this);
        mSearchView.findViewById(R.id.search_voice_btn).setOnClickListener(this);
        
        mSearchView.findViewById(R.id.search_button).setOnClickListener(this);
        
    }
    
    
    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        initToolBar(toolbar, true, "");
        
        LinearLayout hotWordLayout = findViewById(R.id.hotword_layout);
        TextView tvRefresh = findViewById(R.id.tv_refresh);
        mFlexboxLayout = findViewById(R.id.flexbox_layout);
        mFlexboxLayout.setShowDividerHorizontal(FlexboxLayout.FLEX_DIRECTION_ROW);
        mFlexboxLayout.setFlexWrap(FlexboxLayout.FLEX_WRAP_WRAP);
        TextView tvClear = findViewById(R.id.tv_clear);
        mHistoryList = findViewById(R.id.history_list);
        
        LinearLayout resultLayout = findViewById(R.id.result_layout);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);
        
        ListView suggestionList = findViewById(R.id.suggestion_list);
        
        //----------------------------事件处理----------------------------//
        //清空数据
        RxView.clicks(tvClear).throttleFirst(1, TimeUnit.SECONDS)
            .compose(this.bindToLifecycle())
            .subscribe(new Consumer<Object>() {
                @Override
                public void accept(Object o) throws Exception {
                    new AlertDialog.Builder(SearchActivity.this)
                        .setTitle("警告")
                        .setMessage("是否要清空所有的历史搜索记录")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //删除数据
                                boolean b = dao.deleteAll();
                                if (b) {
                                    Toast.makeText(SearchActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                    mListoryAdapter.updateDataSource(null);
                                } else {
                                    Toast.makeText(SearchActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                                }
                                dialog.dismiss();
                                
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                }
            });
        //获取热词
        RxView.clicks(tvRefresh).throttleFirst(1, TimeUnit.SECONDS)
            .compose(this.bindToLifecycle())
            .subscribe(new Consumer<Object>() {
                @Override
                public void accept(Object o) throws Exception {
                    getSearchHotWord();
                }
            });
        
        //word列表
        mListoryAdapter = new SearchHistoryAdapter(null);
        mHistoryList.setAdapter(mListoryAdapter);
        
    }
    
    public void getSearchHotWord() {
        mFlexboxLayout.removeAllViews();
        RetrofitUtils.getRetrofit().create(RootApi.class)
            .getSearchRecomment()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map(new Function<SearchRecommentBean, List<String>>() {
                @Override
                public List<String> apply(SearchRecommentBean bean) throws Exception {
                    List<SearchRecommentBean.DataBean.SuggestWordListBean> suggest_word_list = bean.data.suggest_word_list;
                    List<String> wordList = new ArrayList<>();
                    for (int i = 0; i < suggest_word_list.size(); i++) {
                        if (suggest_word_list.get(i).type.equals("recom")) {
                            wordList.add(suggest_word_list.get(i).word);
                        }
                    }
                    return wordList;
                }
            })
            .subscribe(new Consumer<List<String>>() {
                @Override
                public void accept(List<String> wordList) throws Exception {
                    for (int i = 0; i < wordList.size(); i++) {
                        TextView textView = new TextView(SearchActivity.this);
                        textView.setTextColor(getRandomColor());
                        textView.setPadding(20, 10, 20, 10);
                        textView.setText(wordList.get(i));
                        mFlexboxLayout.addView(textView);
                        if (i == 7) {
                            return;
                        }
                    }
                }
            }, ErrorAction.error());
    }
    
    //获取随机颜色
    public int getRandomColor() {
        int color[] = {Color.GREEN, Color.BLUE, Color.BLACK, Color.YELLOW, Color.RED};
        Random random = new Random();
        int index = random.nextInt(color.length);
        return color[index];
    }
    
    public void getSearchHistory() {
        Observable
            .create(new ObservableOnSubscribe<List<SearchHistoryBean>>() {
                @Override
                public void subscribe(ObservableEmitter<List<SearchHistoryBean>> e) throws Exception {
                    List<SearchHistoryBean> beans = dao.queryPager(0, 20);
                    e.onNext(beans);
                }
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(this.<List<SearchHistoryBean>>bindToLifecycle())
            .subscribe(new Consumer<List<SearchHistoryBean>>() {
                @Override
                public void accept(List<SearchHistoryBean> beans) throws Exception {
                    mListoryAdapter.updateDataSource(beans);
                }
            }, ErrorAction.error());
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_close_btn:
                //关闭
                Toast.makeText(SearchActivity.this, "关闭", Toast.LENGTH_SHORT).show();
                mSearchView.clearFocus();
                break;
            case R.id.search_go_btn:
                //搜索
                Toast.makeText(SearchActivity.this, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.search_button:
                Toast.makeText(this, "search_button", Toast.LENGTH_SHORT).show();
                break;
            case R.id.search_voice_btn:
                Toast.makeText(this, "search_voice_btn", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        
    }
}完成
