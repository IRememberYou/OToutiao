package com.example.pinan.otoutiao.function.newstab;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.pinan.otoutiao.R;
import com.example.pinan.otoutiao.base.init.BaseActivity;
import com.example.pinan.otoutiao.base.init.BaseApplication;

/**
 * @author pinan
 * @date 2017/11/28
 */

public class NewsCommentActivity extends BaseActivity {
    private static final String KEY_GROUP_ID = "groupid";
    private static final String KEY_ITEM_ID = "itemid";
    
    public static void launch(String groupId, String itemId) {
        Intent intent = new Intent(BaseApplication.sContext, NewsCommentActivity.class);
        intent.putExtra(KEY_GROUP_ID, groupId);
        intent.putExtra(KEY_ITEM_ID, itemId);
        BaseApplication.sContext.startActivity(intent);
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);
        Intent intent = getIntent();
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.container, NewsCommentFragment.newInstance(intent.getStringExtra(KEY_GROUP_ID), intent.getStringExtra(KEY_ITEM_ID)))
            .commit();
    }
}
