package com.example.pinan.otoutiao.model.binder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.pinan.otoutiao.R;
import com.example.pinan.otoutiao.function.newstab.NewsContentActivity;
import com.example.pinan.otoutiao.function.newstab.bean.MultiMediaArticleBean;
import com.example.pinan.otoutiao.function.newstab.bean.MultiNewsArticleDataBean;
import com.example.pinan.otoutiao.utils.ImageLoader;
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

public class MediaArticleImgViewBinder extends ItemViewBinder<MultiMediaArticleBean.DataBean, MediaArticleImgViewBinder.ViewHolder> {
    
    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_media_article_imgview_binder, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final MultiMediaArticleBean.DataBean item) {
        final Context context = holder.itemView.getContext();
        holder.tvTitle.setText(item.title);
        holder.tvExtra.setText(String.format(context.getString(R.string.extra), item.total_read_count, item.comment_count, TimeUtil.getTimeStampAgo(item.behot_time + "")));
        ImageLoader.loadCenterCrop(context, item.image_list.get(0).url, holder.ivImage, R.color.viewBackground);
        RxView.clicks(holder.itemView)
            .throttleFirst(1, TimeUnit.SECONDS)
            .subscribe(new Consumer<Object>() {
                @Override
                public void accept(Object o) throws Exception {
                    MultiNewsArticleDataBean bean = new MultiNewsArticleDataBean();
                    bean.title = item.title;
                    bean.display_url = item.display_url;
                    bean.media_name = item.source;
                    bean.group_id = item.group_id;
                    bean.item_id = item.item_id;
                    MultiNewsArticleDataBean.MediaInfoBean mediaInfo = new MultiNewsArticleDataBean.MediaInfoBean();
                    mediaInfo.media_id = item.media_id;
                    bean.media_info = mediaInfo;
                    NewsContentActivity.launch(bean, item.image_list.get(0).url);
                }
            });
        RxView.clicks(holder.ivDots)
            .throttleFirst(1, TimeUnit.SECONDS)
            .subscribe(new Consumer<Object>() {
                @Override
                public void accept(Object o) throws Exception {
                    PopupMenu popupMenu = new PopupMenu(context, holder.ivDots, Gravity.END, 0, R.style.MyPopupMenu);
                    popupMenu.inflate(R.menu.menu_share);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            int itemId = menuItem.getItemId();
                            if (itemId == R.id.action_share) {
                                ShareIntentAction.send(context, item.title + "\n" + item.display_url);
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
    }
    
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvExtra;
        ImageView ivImage, ivDots;
        
        public ViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tv_title);
            tvExtra = view.findViewById(R.id.tv_extra);
            ivImage = view.findViewById(R.id.iv_image);
            ivDots = view.findViewById(R.id.iv_dots);
        }
    }
}
