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

import com.example.pinan.otoutiao.R;
import com.example.pinan.otoutiao.function.newstab.VideoContentActivity;
import com.example.pinan.otoutiao.function.newstab.bean.MultiMediaArticleBean;
import com.example.pinan.otoutiao.function.newstab.bean.MultiNewsArticleDataBean;
import com.example.pinan.otoutiao.utils.ImageLoader;
import com.example.pinan.otoutiao.utils.NetWorkUtil;
import com.example.pinan.otoutiao.utils.ShareIntentAction;
import com.example.pinan.otoutiao.utils.TimeUtil;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.drakeet.multitype.ItemViewBinder;

/**
 * @author pinan
 * @date 2017/11/30
 */

public class MediaArticleVideoViewBinder extends ItemViewBinder<MultiMediaArticleBean.DataBean, MediaArticleVideoViewBinder.ViewHolder> {
    @NonNull
    @Override
    protected MediaArticleVideoViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_media_article_video_binder, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final MultiMediaArticleBean.DataBean item) {
        final Context context = holder.itemView.getContext();
        
        List<MultiMediaArticleBean.DataBean.ImageListBean> imageList = item.image_list;
        String url = null;
        if (imageList != null && imageList.size() > 0) {
            url = imageList.get(0).url;
            if (!TextUtils.isEmpty(url)) {
                if (NetWorkUtil.isWifiConnected(context)) {
                    // 加载高清图
                    url = url.replace("list", "large");
                }
                ImageLoader.loadCenterCrop(context, url, holder.iv_video_image, R.color.viewBackground);
            }
        }
        
        final String title = item.title;
        String readCount = item.total_read_count + "阅读量";
        String commentCount = item.comment_count + "评论";
        String datetime = item.behot_time + "";
        if (!TextUtils.isEmpty(datetime)) {
            datetime = TimeUtil.getTimeStampAgo(datetime);
        }
        String video_time = item.video_duration_str;
        
        holder.tv_title.setText(title);
        holder.tv_extra.setText(readCount + " - " + commentCount + " - " + datetime);
        holder.tv_video_time.setText(video_time);
        
        final String finalUrl = url;
        RxView.clicks(holder.itemView)
            .throttleFirst(1, TimeUnit.SECONDS)
            .observeOn(Schedulers.io())
            .subscribe(new Consumer<Object>() {
                @Override
                public void accept(Object o) throws Exception {
                    MultiNewsArticleDataBean bean = new MultiNewsArticleDataBean();
                    bean.title = title;
                    bean.group_id = item.group_id;
                    bean.item_id = item.item_id;
                    bean.video_id = item.video_infos.get(0).vid;
                    bean.abstractX = item.abstractX;
                    bean.source = item.source;
                    
                    String s = item.video_duration_str;
                    int time = 0;
                    if (s.contains(":")) {
                        String[] split = s.split(":");
                        for (int i = 0; i < split.length; i++) {
                            if (i == 0) {
                                time = Integer.parseInt(split[i]) * 60;
                            }
                            time += Integer.parseInt(split[i]);
                        }
                    }
                    bean.video_duration = time;
                    
                    MultiNewsArticleDataBean.MediaInfoBean mediaInfoBean = new MultiNewsArticleDataBean.MediaInfoBean();
                    mediaInfoBean.media_id = item.media_id;
                    bean.media_info = mediaInfoBean;
                    
                    MultiNewsArticleDataBean.VideoDetailInfoBean.DetailVideoLargeImageBean videobean = new MultiNewsArticleDataBean.VideoDetailInfoBean.DetailVideoLargeImageBean();
                    MultiNewsArticleDataBean.VideoDetailInfoBean videoDetail = new MultiNewsArticleDataBean.VideoDetailInfoBean();
                    videobean.url = finalUrl;
                    videoDetail.detail_video_large_image = videobean;
                    bean.video_detail_info=videoDetail;
                    
                    VideoContentActivity.launch(bean);
                    
                }
            });
        
        
        holder.iv_dots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context,
                    holder.iv_dots, Gravity.END, 0, R.style.MyPopupMenu);
                popupMenu.inflate(R.menu.menu_share);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menu) {
                        int itemId = menu.getItemId();
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
        
        private TextView tv_extra;
        private TextView tv_title;
        private ImageView iv_video_image;
        private TextView tv_video_time;
        private ImageView iv_dots;
        
        
        ViewHolder(View itemView) {
            super(itemView);
            this.tv_extra = itemView.findViewById(R.id.tv_extra);
            this.tv_title = itemView.findViewById(R.id.tv_title);
            this.iv_video_image = itemView.findViewById(R.id.iv_video_image);
            this.tv_video_time = itemView.findViewById(R.id.tv_video_time);
            this.iv_dots = itemView.findViewById(R.id.iv_dots);
        }
    }
}
