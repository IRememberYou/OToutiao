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
 * @date 2017/12/1
 */

public class VideoContentActivity extends BaseActivity {
    private static final String KEY_NEWS_ARTICLE_DATABEAN = "multinewsarticledatabean";
    
    public static void launch(MultiNewsArticleDataBean bean) {
        BaseApplication.sContext.startActivity(new Intent(BaseApplication.sContext, VideoContentActivity.class)
            .putExtra(KEY_NEWS_ARTICLE_DATABEAN, bean)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_video_content_new);
    }
    
    
    
    
}
