package com.example.pinan.otoutiao.utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.example.pinan.otoutiao.R;

/**
 * 分享相关封装
 */

public class ShareIntentAction {
    /**
     * 分享文本
     *
     * @param context
     * @param shareText 需要分享的文本
     */
    public static void send(@NonNull Context context, @NonNull String shareText) {
        Intent shareIntent = new Intent()
            .setAction(Intent.ACTION_SEND)
            .setType("text/plain")
            .putExtra(Intent.EXTRA_TEXT, shareText);
        context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.share_to)));
    }
}
