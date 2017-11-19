package com.example.pinan.otoutiao.newstab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pinan.otoutiao.R;
import com.example.pinan.otoutiao.base.BaseFragment;
import com.example.pinan.otoutiao.base.Constant;
import com.example.pinan.otoutiao.database.dao.NewsChannelDao;
import com.example.pinan.otoutiao.newstab.adapter.BasePagerAdapter;
import com.example.pinan.otoutiao.newstab.bean.NewsChannelBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * @author pinan
 * @date 2017/11/15
 */

public class NewsTabFragment extends BaseFragment {
    @Bind(R.id.view_pager_news)
    ViewPager mVp;
    @Bind(R.id.tablayout)
    TabLayout mTabLayout;
    
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();
    private Map<String, Fragment> map = new HashMap<>();
    private NewsChannelDao dao = new NewsChannelDao();
    private BasePagerAdapter mAdapter;
    
    public static NewsTabFragment getInstance() {
        return new NewsTabFragment();
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.news_tab_fragment, container, false);
    }
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        mVp.setOffscreenPageLimit(15);
        mAdapter = new BasePagerAdapter(getChildFragmentManager(), fragmentList, titleList);
        mTabLayout.setupWithViewPager(mVp);
        mVp.setAdapter(mAdapter);
    }
    
    private void initData() {
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
    }
    
    
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}