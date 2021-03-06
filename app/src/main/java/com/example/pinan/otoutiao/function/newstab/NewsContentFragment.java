package com.example.pinan.otoutiao.function.newstab;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.pinan.otoutiao.R;
import com.example.pinan.otoutiao.base.init.BaseFragment;
import com.example.pinan.otoutiao.function.newstab.bean.MultiNewsArticleDataBean;
import com.example.pinan.otoutiao.function.newstab.model.NewContentModel;
import com.example.pinan.otoutiao.function.newstab.persenter.NewContentPresenter;
import com.example.pinan.otoutiao.utils.ImageLoader;
import com.example.pinan.otoutiao.utils.ShareIntentAction;
import com.example.pinan.otoutiao.widget.helper.AppBarStateChangeListener;

import java.io.Serializable;

/**
 * @author pinan
 * @date 2017/11/27
 */

public class NewsContentFragment extends BaseFragment<NewContentModel.Presenter> implements NewContentModel.View {
    private static final String KEY_DATABEAN = "databean";
    private static final String KEY_IMGURL = "imgurl";
    
    private ImageView mImage;
    private WebView mWebView;
    private NestedScrollView mScrollView;
    private ProgressBar mProgressBar;
    private SwipeRefreshLayout mRefreshLayout;
    private boolean isHasImage;
    private AppBarLayout mAppBarLayout;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private String mImgUrl;
    private Toolbar mToolbar;
    private MultiNewsArticleDataBean mDataBean;
    
    public static NewsContentFragment newInstance(Serializable dataBean, String imgUrl) {
        NewsContentFragment fragment = new NewsContentFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_DATABEAN, dataBean);
        bundle.putString(KEY_IMGURL, imgUrl);
        fragment.setArguments(bundle);
        return fragment;
    }
    
    @Override
    protected int attachLayoutId() {
        mImgUrl = getArguments().getString(KEY_IMGURL);
        isHasImage = !TextUtils.isEmpty(mImgUrl);
        return isHasImage? R.layout.news_content_fragment : R.layout.fragment_news_content;
    }
    
    @Override
    protected void initView(View view) {
        if (isHasImage) {
            mAppBarLayout = view.findViewById(R.id.app_bar_layout);
            mCollapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar);
            mImage = view.findViewById(R.id.iv_image);
        }
        mWebView = view.findViewById(R.id.webview);
        mScrollView = view.findViewById(R.id.scrollView);
        mProgressBar = view.findViewById(R.id.pb_progress);
        mRefreshLayout = view.findViewById(R.id.refresh_layout);
        mToolbar = view.findViewById(R.id.toolbar);
        initWebClient();
        initToolBar(mToolbar, true, "");
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScrollView.scrollTo(0, 0);
            }
        });
        setHasOptionsMenu(true);
    }
    
    private void initWebClient() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        // 缩放,设置为不能缩放可以防止页面上出现放大和缩小的图标
        settings.setBuiltInZoomControls(false);
        // 缓存
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启DOM storage API功能
        settings.setDomStorageEnabled(true);
        // 开启application Cache功能
        settings.setAppCacheEnabled(true);
        // 判断是否为无图模式
        settings.setBlockNetworkImage(false);
        // 不调用第三方浏览器即可进行页面反应
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!TextUtils.isEmpty(url)) {
                    view.loadUrl(url);
                }
                return true;
            }
            
            @Override
            public void onPageFinished(WebView view, String url) {
                onHideLoading();
                super.onPageFinished(view, url);
            }
        });
        
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
                    mWebView.goBack();
                    return true;
                }
                return false;
            }
        });
        
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress >= 90) {
                    onHideLoading();
                } else {
                    onShowLoading();
                }
            }
        });
    }
    
    @Override
    protected void initData() {
        mDataBean = (MultiNewsArticleDataBean) getArguments().getSerializable(KEY_DATABEAN);
        presenter.doLoadData(mDataBean);
        
        if (isHasImage) {
            ImageLoader.loadCenterCrop(mContext, mImgUrl, mImage, R.color.viewBackground);
            mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
                @Override
                public void onStateChanged(AppBarLayout appBarLayout, State state) {
                    if (state == State.EXPANDED) {
                        mCollapsingToolbarLayout.setTitle("");
                        mToolbar.setBackgroundColor(Color.TRANSPARENT);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                        }
                        Log.d("NewsContentFragment", "expanded");
                    } else if (state == State.COLLAPSED) {
                        Log.d("NewsContentFragment", "collapsed");
                    } else {
                        mCollapsingToolbarLayout.setTitle(mDataBean.media_name);
                        mToolbar.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                        }
                        Log.d("NewsContentFragment", "other state");
                    }
                }
            });
        } else {
            mToolbar.setTitle(mDataBean.media_name);
        }
    }
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_browser, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        String shareUrl = mDataBean.share_url != null ? mDataBean.share_url : mDataBean.display_url;
        switch (itemId) {
            case R.id.action_open_commend:
                NewsCommentActivity.launch(mDataBean.group_id + "", mDataBean.item_id + "");
                break;
            case R.id.action_open_media_home:
                MediaHomeActivity.launcher(mDataBean.media_info.media_id+"");
                break;
            case R.id.action_share:
                ShareIntentAction.send(mContext, mDataBean.title + "\n" + shareUrl);
                break;
            case R.id.home:
                getActivity().onBackPressed();
                break;
            case R.id.action_open_in_browser:
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(shareUrl)));
                break;
            default:
                break;
        }
        
        return super.onOptionsItemSelected(item);
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
    public void setPresenter(NewContentModel.Presenter presenter) {
        if (presenter == null) {
            this.presenter = new NewContentPresenter(this);
        }
    }
    
    @Override
    public void onSetWebView(String url, boolean flag) {
        if (flag) {
            mWebView.loadData(url, "text/html", "utf-8");
        }
    }
}
