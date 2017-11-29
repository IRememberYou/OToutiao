package com.example.pinan.otoutiao.model.binder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pinan.otoutiao.R;
import com.example.pinan.otoutiao.function.newstab.bean.NewsCommentBean;
import com.example.pinan.otoutiao.utils.ImageLoader;
import com.example.pinan.otoutiao.widget.CircleImageView;

import me.drakeet.multitype.ItemViewBinder;

/**
 * @author pinan
 * @date 2017/11/28
 */

public class NewsCommentViewBinder extends ItemViewBinder<NewsCommentBean.DataBean.CommentBean, NewsCommentViewBinder.ViewHolder> {
    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_news_comment, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull NewsCommentBean.DataBean.CommentBean item) {
        Context context = holder.itemView.getContext();
        ImageLoader.loadCenterCrop(context,item.user_profile_image_url,holder.mIvAvatar,R.mipmap.error_image);
        holder.mTvText.setText(item.text);
        holder.mTvUsername.setText(item.user_name);
        holder.mTvLikes.setText(item.digg_count+"赞");
    }
    
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView mIvAvatar;
        private TextView mTvUsername;
        private TextView mTvText;
        private TextView mTvLikes;
        
        public ViewHolder(View view) {
            super(view);
            mIvAvatar = view.findViewById(R.id.iv_avatar);
            mTvUsername = view.findViewById(R.id.tv_username);
            mTvText = view.findViewById(R.id.tv_text);
            mTvLikes = view.findViewById(R.id.tv_likes);
        }
    }
}
