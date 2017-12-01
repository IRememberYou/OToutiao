package com.example.pinan.otoutiao.model.binder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pinan.otoutiao.R;
import com.example.pinan.otoutiao.function.newstab.NewsContentActivity;
import com.example.pinan.otoutiao.function.newstab.bean.MultiMediaArticleBean;
import com.example.pinan.otoutiao.function.newstab.bean.MultiNewsArticleDataBean;
import com.example.pinan.otoutiao.utils.ShareIntentAction;
import com.example.pinan.otoutiao.utils.TimeUtil;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;
import me.drakeet.multitype.ItemViewBinder;

/**
 * @author pinan
 * @date 2017/11/30
 */

public class MediaArticleTextViewBinder extends ItemViewBinder<MultiMediaArticleBean.DataBean, MediaArticleTextViewBinder.ViewHolder> {
    @NonNull
    @Override
    protected MediaArticleTextViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.media_article_textview_binder, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    protected void onBindViewHolder(@NonNull final MediaArticleTextViewBinder.ViewHolder holder, @NonNull final MultiMediaArticleBean.DataBean item) {
        
        final Context context = holder.itemView.getContext();
        
        try {
            String title = item.title;
            String abstractX = item.abstractX;
            String readCount = item.total_read_count + "阅读量";
            String countmmentCount = item.comment_count + "评论";
            String time = item.behot_time + "";
            if (!TextUtils.isEmpty(time)) {
                time = TimeUtil.getTimeStampAgo(time);
            }
            
            holder.tv_title.setText(title);
            holder.tv_abstract.setText(abstractX);
            holder.tv_extra.setText(readCount + " - " + countmmentCount + " - " + time);
            
            RxView.clicks(holder.itemView)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Object o) throws Exception {
                        MultiNewsArticleDataBean bean = new MultiNewsArticleDataBean();
                        bean.title = item.title;
                        bean.display_url = item.display_url;
                        bean.share_url = item.display_url;
                        bean.media_name = item.source;
                        MultiNewsArticleDataBean.MediaInfoBean mediaInfo = new MultiNewsArticleDataBean.MediaInfoBean();
                        mediaInfo.media_id = item.media_id;
                        bean.media_info = mediaInfo;
                        bean.group_id = item.group_id;
                        bean.item_id = item.item_id;
                        NewsContentActivity.launch(bean);
                    }
                });
    
            RxView.clicks(holder.iv_dots)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        PopupMenu popup = new PopupMenu(context, holder.iv_dots, Gravity.CENTER, 0, R.style.MyPopupMenu);
                        popup.inflate(R.menu.menu_share);
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menu) {
                                int itemId = menu.getItemId();
                                if (itemId == R.id.action_share) {
                                    ShareIntentAction.send(context, item.title + "\n" + item.display_url);
                                } else if (itemId == R.id.action_setting) {
                                    Toast.makeText(context, "配置", Toast.LENGTH_SHORT).show();
                                }
                                return false;
                            }
                        });
                        popup.show();
                    }
                });
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public static class ViewHolder extends RecyclerView.ViewHolder {
        
        private TextView tv_extra;
        private TextView tv_title;
        private TextView tv_abstract;
        private ImageView iv_dots;
        
        ViewHolder(View itemView) {
            super(itemView);
            this.tv_extra = itemView.findViewById(R.id.tv_extra);
            this.tv_title = itemView.findViewById(R.id.tv_title);
            this.tv_abstract = itemView.findViewById(R.id.tv_abstract);
            this.iv_dots = itemView.findViewById(R.id.iv_dots);
        }
    }
}
