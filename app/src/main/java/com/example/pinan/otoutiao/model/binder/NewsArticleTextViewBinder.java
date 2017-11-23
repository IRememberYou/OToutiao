package com.example.pinan.otoutiao.model.binder;

import android.content.Context;
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
import com.example.pinan.otoutiao.function.newstab.bean.MultiNewsArticleDataBean;
import com.example.pinan.otoutiao.utils.ImageLoader;
import com.example.pinan.otoutiao.utils.ShareIntentAction;
import com.example.pinan.otoutiao.utils.TimeUtil;
import com.example.pinan.otoutiao.widget.CircleImageView;

import me.drakeet.multitype.ItemViewBinder;

/**
 *
 * @author Meiji
 * @date 2017/6/8
 * 不带图片的 item
 */

public class NewsArticleTextViewBinder extends ItemViewBinder<MultiNewsArticleDataBean, NewsArticleTextViewBinder.ViewHolder> {
    
    private static final String TAG = "NewsArticleTextViewBind";
    
    @Override
    protected NewsArticleTextViewBinder.ViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_news_article_text, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    protected void onBindViewHolder(final NewsArticleTextViewBinder.ViewHolder holder, final MultiNewsArticleDataBean item) {
        
        final Context context = holder.itemView.getContext();
        
        try {
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
                tv_datetime = TimeUtil.getTimeStampAgo(tv_datetime);
            }
            
            holder.tv_title.setText(tv_title);
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
                                ShareIntentAction.send(context, item.title + "\n" + item.share_url);
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });

//            RxView.clicks(holder.itemView)
//                    .throttleFirst(1, TimeUnit.SECONDS)
//                    .subscribe(new Consumer<Object>() {
//                        @Override
//                        public void accept(@io.reactivex.annotations.NonNull Object o) throws Exception {
//                            NewsContentActivity.launch(item);
//                        }
//                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    class ViewHolder extends RecyclerView.ViewHolder {
        
        private CircleImageView iv_media;
        private TextView tv_extra;
        private TextView tv_title;
        private TextView tv_abstract;
        private ImageView iv_dots;
        
        ViewHolder(View itemView) {
            super(itemView);
            this.iv_media = (CircleImageView)itemView.findViewById(R.id.iv_media);
            this.tv_extra = (TextView) itemView.findViewById(R.id.tv_extra);
            this.tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            this.tv_abstract = (TextView) itemView.findViewById(R.id.tv_abstract);
            this.iv_dots = (ImageView) itemView.findViewById(R.id.iv_dots);
        }
    }
}
