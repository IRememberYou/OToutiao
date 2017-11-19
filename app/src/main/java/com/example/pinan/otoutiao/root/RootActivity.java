package com.example.pinan.otoutiao.root;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.pinan.otoutiao.R;
import com.example.pinan.otoutiao.base.BaseActivity;
import com.example.pinan.otoutiao.mediatab.MediaTabFragment;
import com.example.pinan.otoutiao.newstab.NewsTabFragment;
import com.example.pinan.otoutiao.phototab.PhotoTabFragment;
import com.example.pinan.otoutiao.utils.BottomNavigationViewUtil;
import com.example.pinan.otoutiao.videotab.VideoTabFragment;

/**
 * @author pinan
 * @date 2017/11/13
 */

public class RootActivity extends BaseActivity {
    
    private static final String POSITION = "position";//记录位置
    private static final String SELECT_ITEM = "bottomNavigationSelectItem";//记录底部选中状态
    private static final int FRAGMENT_NEWS = 0;
    private static final int FRAGMENT_PHOTO = 1;
    private static final int FRAGMENT_VIDEO = 2;
    private static final int FRAGMENT_MEDIA = 3;
    private int position;
    private Toolbar mToolbar;
    private BottomNavigationView mBottomNavigation;
    private NavigationView mNavView;
    private DrawerLayout mDrawerLayout;
    private MediaTabFragment mMediaTabFragment;
    private VideoTabFragment mVideoTabFragment;
    private PhotoTabFragment mPhotoTabFragment;
    private NewsTabFragment mNewsTabFragment;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        initView();
        if (savedInstanceState != null) {
            mNewsTabFragment = (NewsTabFragment) getSupportFragmentManager().findFragmentByTag(NewsTabFragment.class.getName());
            mPhotoTabFragment = (PhotoTabFragment) getSupportFragmentManager().findFragmentByTag(PhotoTabFragment.class.getName());
            mVideoTabFragment = (VideoTabFragment) getSupportFragmentManager().findFragmentByTag(VideoTabFragment.class.getName());
            mMediaTabFragment = (MediaTabFragment) getSupportFragmentManager().findFragmentByTag(MediaTabFragment.class.getName());
            
            showFragment(savedInstanceState.getInt(POSITION));
            mBottomNavigation.setSelectedItemId(savedInstanceState.getInt(SELECT_ITEM));
        } else {
            showFragment(FRAGMENT_NEWS);
        }
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.RED));
//        getWindow().setNavigationBarColor(Color.GREEN);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_root, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            startActivity(SearchActivity.class);
        }
        return super.onOptionsItemSelected(item);
    }
    
    private long exitTime;
    
    @Override
    public void onBackPressed() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - exitTime < 2000) {
            super.onBackPressed();
        } else {
            exitTime = currentTimeMillis;
            Toast.makeText(this, R.string.double_click_exit, Toast.LENGTH_SHORT).show();
        }
    }
    
    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mBottomNavigation = findViewById(R.id.bottom_navigation);
        mNavView = findViewById(R.id.nav_view);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        
        mToolbar.setTitleTextColor(getResources().getColor(R.color.White));
        setSupportActionBar(mToolbar);
        //停用BottomNavigationView的转换模式
        BottomNavigationViewUtil.disableShiftMode(mBottomNavigation);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.close_desc, R.string.open_desc);
        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        
        mBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.action_news:
                        showFragment(FRAGMENT_NEWS);
                        doubleClick(FRAGMENT_NEWS);
                        break;
                    case R.id.action_photo:
                        showFragment(FRAGMENT_PHOTO);
                        doubleClick(FRAGMENT_PHOTO);
                        break;
                    case R.id.action_video:
                        showFragment(FRAGMENT_VIDEO);
                        doubleClick(FRAGMENT_VIDEO);
                        break;
                    case R.id.action_media:
                        showFragment(FRAGMENT_MEDIA);
                        doubleClick(FRAGMENT_MEDIA);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        
        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.nav_switch_night_mode:
                        Toast.makeText(RootActivity.this, "设置主题", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_setting:
                        Toast.makeText(RootActivity.this, "设置", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_share:
                        Toast.makeText(RootActivity.this, "分享", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }
    
    
    private void showFragment(int index) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        hideFragment(ft);
        position = index;
        switch (index) {
            case FRAGMENT_NEWS:
                mToolbar.setTitle("头条");
                if (mNewsTabFragment == null) {
                    mNewsTabFragment = NewsTabFragment.getInstance();
                    ft.add(R.id.container, mNewsTabFragment, NewsTabFragment.class.getName());
                } else {
                    ft.show(mNewsTabFragment);
                }
                break;
            case FRAGMENT_PHOTO:
                mToolbar.setTitle("图片");
                if (mPhotoTabFragment == null) {
                    mPhotoTabFragment = PhotoTabFragment.getInstance();
                    ft.add(R.id.container, mPhotoTabFragment, PhotoTabFragment.class.getName());
                } else {
                    ft.show(mPhotoTabFragment);
                }
                break;
            case FRAGMENT_VIDEO:
                mToolbar.setTitle("视频");
                if (mVideoTabFragment == null) {
                    mVideoTabFragment = VideoTabFragment.getInstance();
                    ft.add(R.id.container, mVideoTabFragment, VideoTabFragment.class.getName());
                } else {
                    ft.show(mVideoTabFragment);
                }
                break;
            case FRAGMENT_MEDIA:
                mToolbar.setTitle("头条号");
                if (mMediaTabFragment == null) {
                    mMediaTabFragment = MediaTabFragment.getInstance();
                    ft.add(R.id.container, mMediaTabFragment, MediaTabFragment.class.getName());
                } else {
                    ft.show(mMediaTabFragment);
                }
                break;
            default:
                break;
        }
        ft.commit();
    }
    
    private void hideFragment(FragmentTransaction ft) {
        if (mNewsTabFragment != null) {
            ft.hide(mNewsTabFragment);
        }
        if (mPhotoTabFragment != null) {
            ft.hide(mPhotoTabFragment);
        }
        if (mVideoTabFragment != null) {
            ft.hide(mVideoTabFragment);
        }
        if (mMediaTabFragment != null) {
            ft.hide(mMediaTabFragment);
        }
    }
    
    private long firstClickTime;
    
    public void doubleClick(int index) {
        long secondClickTime = System.currentTimeMillis();
        if ((secondClickTime - firstClickTime < 500)) {
            switch (index) {
                case FRAGMENT_NEWS:
                    Toast.makeText(this, "double_ FRAGMENT_NEWS", Toast.LENGTH_SHORT).show();
                    break;
                case FRAGMENT_PHOTO:
                    Toast.makeText(this, "double_ FRAGMENT_PHOTO", Toast.LENGTH_SHORT).show();
                    break;
                case FRAGMENT_VIDEO:
                    Toast.makeText(this, "double_ FRAGMENT_VIDEO", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        } else {
            firstClickTime = secondClickTime;
        }
    }
}