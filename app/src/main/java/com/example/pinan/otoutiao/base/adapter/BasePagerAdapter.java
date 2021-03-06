package com.example.pinan.otoutiao.base.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 通用的 TabLayout + ViewPager的适配器
 *
 * @author pinan
 * @date 2017/11/16
 */

public class BasePagerAdapter extends FragmentPagerAdapter {
    
    private List<Fragment> fragmentList;
    private List<String> titleList;
    
    public BasePagerAdapter(FragmentManager fm, List<Fragment> fragmentList, String[] titles) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titleList = new ArrayList<>(Arrays.asList(titles));
    }
    
    public BasePagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titleList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titleList = titleList;
    }
    
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }
    
    @Override
    public int getCount() {
        return titleList.size();
    }
    
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
    
    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }
    
    public void recreateItems(List<Fragment> fragmentList, List<String> titleList) {
        this.fragmentList = fragmentList;
        this.titleList = titleList;
        notifyDataSetChanged();
    }
}
