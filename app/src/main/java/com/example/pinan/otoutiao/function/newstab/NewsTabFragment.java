package com.example.pinan.otoutiao.function.newstab;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.pinan.otoutiao.R;
import com.example.pinan.otoutiao.base.Constant;
import com.example.pinan.otoutiao.base.init.BaseFragment;
import com.example.pinan.otoutiao.database.dao.NewsChannelDao;
import com.example.pinan.otoutiao.function.newstab.adapter.BasePagerAdapter;
import com.example.pinan.otoutiao.function.newstab.bean.NewsChannelBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pinan
 * @date 2017/11/15
 */

public class NewsTabFragment extends BaseFragment {
    ViewPager mVp;
    TabLayout mTabLayout;
    
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();
    private Map<String, Fragment> map = new HashMap<>();
    private NewsChannelDao dao = new NewsChannelDao();
    private BasePagerAdapter mAdapter;
    
    public static NewsTabFragment getInstance() {
        return new NewsTabFragment();
    }
    
    @Override
    protected int attachLayoutId() {
        return R.layout.news_tab_fragment;
    }
    
    @Override
    protected void initView(View view) {
        mVp = view.findViewById(R.id.view_pager_news);
        mTabLayout = view.findViewById(R.id.tablayout);
    }
    
    @Override
    public void initData() {
        List<NewsChannelBean> queryList = dao.query(Constant.NEWS_CHANNEL_ENABLE);
        if (queryList == null || queryList.size() <= 0) {
            dao.addInitData();
            queryList = dao.query(Constant.NEWS_CHANNEL_ENABLE);
        }
        for (NewsChannelBean bean : queryList) {
            Fragment fragment = null;
            String channelId = bean.channelId;
            switch (channelId) {
                case "essay_joke":
                    if (map.containsKey(channelId)) {
                        fragmentList.add(map.get(channelId));
                    } else {
                        fragment = JokeContentFragment.getInstance();
                        fragmentList.add(fragment);
                    }
                    break;
                case "question_and_answer":
                    if (map.containsKey(channelId)) {
                        fragmentList.add(map.get(channelId));
                    } else {
                        fragment = WendaArticleFragment.getInstance();
                        fragmentList.add(fragment);
                    }
                    break;
                default:
                    if (map.containsKey(channelId)) {
                        fragmentList.add(map.get(channelId));
                    } else {
                        fragment = NewsArticleFragment.newInstance(channelId);
                        fragmentList.add(fragment);
                    }
                    break;
            }
            titleList.add(bean.channelName);
            if (fragment != null) {
                map.put(channelId, fragment);
            }
        }
    
        mVp.setOffscreenPageLimit(15);
        mAdapter = new BasePagerAdapter(getChildFragmentManager(), fragmentList, titleList);
        mTabLayout.setupWithViewPager(mVp);
        mVp.setAdapter(mAdapter);
    }
    
    
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    
    @Override
    public void onShowLoading() {
    
    }
    
    @Override
    public void onHideLoading() {
    
    }
    
    @Override
    public void onShowNetError() {
    
    }
    
    @Override
    public void setPresenter(Object presenter) {
    
    }
}