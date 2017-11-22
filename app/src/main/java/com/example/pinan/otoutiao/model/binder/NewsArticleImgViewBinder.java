package com.example.pinan.otoutiao.model.binder;

import android.content.Context;
import android.content.Intent;
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
import com.example.pinan.otoutiao.function.newstab.bean.MultiNewsArticleDataBean;
import com.example.pinan.otoutiao.utils.ImageLoader;
import com.example.pinan.otoutiao.widget.CircleImageView;

import java.util.List;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Meiji on 2017/6/8.
 * 带图片的 item
 */

public class NewsArticleImgViewBinder extends ItemViewBinder<MultiNewsArticleDataBean, NewsArticleImgViewBinder.ViewHolder> {
    
    @NonNull
    @Override
    protected NewsArticleImgViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_news_article_img, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    protected void onBindViewHolder(@NonNull final NewsArticleImgViewBinder.ViewHolder holder, @NonNull final MultiNewsArticleDataBean item) {
        
        final Context context = holder.itemView.getContext();
        
        try {
            String imgUrl = "http://p3.pstatp.com/";
            List<MultiNewsArticleDataBean.ImageListBean> image_list = item.image_list;
            if (image_list != null && image_list.size() != 0) {
                String url = image_list.get(0).url;
                ImageLoader.loadCenterCrop(context, url, holder.iv_image, R.color.viewBackground);
                if (!TextUtils.isEmpty(image_list.get(0).uri)) {
                    imgUrl += image_list.get(0).uri.replace("list", "large");
                }
            }
            
            if (null != item.user_info) {
                String avatar_url = item.user_info.avatar_url;
                if (!TextUtils.isEmpty(avatar_url)) {
                    ImageLoader.loadCenterCrop(context, avatar_url, holder.iv_media, R.color.viewBackground);
                }
            }
            
            String tv_title = item.title;
            String tv_abstract = item.abstractX;
            String tv_source = item.source;
            String tv_comment_count = item.comment_count + "评论";
            String tv_datetime = item.behot_time + "";
            if (!TextUtils.isEmpty(tv_datetime)) {
//                tv_datetime = TimeUtil.getTimeStampAgo(tv_datetime);
            }
            
            holder.tv_title.setText(tv_title);
//            holder.tv_title.setTextSize(SettingUtil.getInstance().getTextSize());
            holder.tv_abstract.setText(tv_abstract);
            holder.tv_extra.setText(tv_source + " - " + tv_comment_count + " - " + tv_datetime);
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
                                String shareText = item.title + "\n" + item.share_url;
                                Intent shareIntent = new Intent()
                                    .setAction(Intent.ACTION_SEND)
                                    .setType("text/plain")
                                    .putExtra(Intent.EXTRA_TEXT, shareText);
                                context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.share_to)));
                            } else if (itemId == R.id.action_setting) {
                                Toast.makeText(context, "配置啥东东", Toast.LENGTH_SHORT).show();
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
            
            final String finalImgUrl = imgUrl;
//            RxView.clicks(holder.itemView)
//                    .throttleFirst(1, TimeUnit.SECONDS)
//                    .subscribe(new Consumer<Object>() {
//                        @Override
//                        public void accept(@io.reactivex.annotations.NonNull Object o) throws Exception {
//                            NewsContentActivity.launch(item, finalImgUrl);
//                        }
//                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    class ViewHolder extends RecyclerView.ViewHolder {
        
        private CircleImageView iv_media;
        private ImageView iv_image;
        private TextView tv_title;
        private TextView tv_abstract;
        private TextView tv_extra;
        private ImageView iv_dots;
        
        ViewHolder(View itemView) {
            super(itemView);
            this.iv_media = itemView.findViewById(R.id.iv_media);
            this.iv_image = itemView.findViewById(R.id.iv_image);
            this.tv_title = itemView.findViewById(R.id.tv_title);
            this.tv_abstract = itemView.findViewById(R.id.tv_abstract);
            this.tv_extra = itemView.findViewById(R.id.tv_extra);
            this.iv_dots = itemView.findViewById(R.id.iv_dots);
        }
    }
}
