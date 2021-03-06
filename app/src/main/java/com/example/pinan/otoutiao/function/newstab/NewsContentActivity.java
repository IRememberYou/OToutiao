package com.example.pinan.otoutiao.function.newstab;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.pinan.otoutiao.R;
import com.example.pinan.otoutiao.base.init.BaseActivity;
import com.example.pinan.otoutiao.base.init.BaseApplication;
import com.example.pinan.otoutiao.function.newstab.bean.MultiNewsArticleDataBean;

/**
 * @author pinan
 * @date 2017/11/27
 */

public class NewsContentActivity extends BaseActivity {
    private static final String TAG = "NewsContentActivity";
    private static final String KEY_IMGURL = "imgurl";
    private static final String KEY_DATABEAN = "databean";
    
    
    public static void launch(MultiNewsArticleDataBean dataBean, String imgUrl) {
        BaseApplication.sContext.startActivity(new Intent(BaseApplication.sContext, NewsContentActivity.class)
            .putExtra(KEY_IMGURL, imgUrl)
            .putExtra(KEY_DATABEAN, dataBean)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
    
    public static void launch(MultiNewsArticleDataBean dataBean) {
        BaseApplication.sContext.startActivity(new Intent(BaseApplication.sContext, NewsCommentActivity.class)
            .putExtra(KEY_DATABEAN, dataBean)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);
        Intent intent = getIntent();
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.container, NewsContentFragment.newInstance(intent.getSerializableExtra(KEY_DATABEAN), intent.getStringExtra(KEY_IMGURL)))
            .commit();
    }
}
