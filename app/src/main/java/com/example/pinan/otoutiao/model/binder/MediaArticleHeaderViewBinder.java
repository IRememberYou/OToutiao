package com.example.pinan.otoutiao.model.binder;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pinan.otoutiao.R;
import com.example.pinan.otoutiao.database.dao.MediaChannelDao;
import com.example.pinan.otoutiao.function.newstab.bean.MediaProfileBean;
import com.example.pinan.otoutiao.utils.ImageLoader;
import com.example.pinan.otoutiao.widget.CircleImageView;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.drakeet.multitype.ItemViewBinder;

/**
 * @author pinan
 * @date 2017/11/30
 */

public class MediaArticleHeaderViewBinder extends ItemViewBinder<MediaProfileBean.DataBean, MediaArticleHeaderViewBinder.ViewHodler> {
    private MediaChannelDao dao = new MediaChannelDao();
    private String TAG = "MediaArticleHeaderViewBinder";
    
    @NonNull
    @Override
    protected MediaArticleHeaderViewBinder.ViewHodler onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_mediarticle_header_view_binder, parent, false);
        return new ViewHodler(view);
    }
    
    @Override
    protected void onBindViewHolder(@NonNull final MediaArticleHeaderViewBinder.ViewHodler holder, @NonNull final MediaProfileBean.DataBean item) {
        final Context context = holder.itemView.getContext();
        if (!TextUtils.isEmpty(item.bg_img_url)) {
            ImageLoader.loadCenterCrop(context, item.bg_img_url, holder.iv_bg, R.mipmap.error_image);
        }
        if (!TextUtils.isEmpty(item.avatar_url)) {
            ImageLoader.loadCenterCrop(context, item.avatar_url, holder.cv_avatar, R.mipmap.error_image);
        }
        holder.tv_name.setText(item.name);
        holder.tv_desc.setText(item.description);
        holder.tv_sub_count.setText(item.followers_count + " 订阅量");
        holder.setIsSub(item.media_id);
        RxView.clicks(holder.tv_is_sub)
            .throttleFirst(1, TimeUnit.SECONDS)
            .observeOn(Schedulers.io())
            .map(new Function<Object, Boolean>() {
                @Override
                public Boolean apply(Object o) throws Exception {
                    return dao.queryIsExist(item.media_id);
                }
            })
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean isExist) throws Exception {
                    if (isExist) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                        dialog.setMessage("取消订阅\"" + item.name + "\"?");
                        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                holder.tv_is_sub.setText("已订阅");
                                dialog.dismiss();
                            }
                        });
                        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                boolean delete = dao.delete(item.media_id);
                                if (delete) {
                                    holder.tv_is_sub.setText("订阅");
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(context, "取消订阅失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        dialog.show();
                    }
                    
                    if (!isExist) {
                        boolean add = dao.add(item.media_id, item.name, item.avatar_url, item.followers_count + ""
                            , item.media_type, item.bg_img_url, item.description);
                        if (add) {
                            Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            })
            .subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean isExist) throws Exception {
                    holder.setIsSub(item.media_id);
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    System.out.println("我的线程是：" + Thread.currentThread().getName());
                    throwable.printStackTrace();
                }
            });
        
    }
    
    
    public class ViewHodler extends RecyclerView.ViewHolder {
        ImageView iv_bg;
        CircleImageView cv_avatar;
        TextView tv_desc, tv_is_sub, tv_sub_count, tv_name;
        
        public ViewHodler(View view) {
            super(view);
            iv_bg = view.findViewById(R.id.iv_bg);
            cv_avatar = view.findViewById(R.id.cv_avatar);
            tv_desc = view.findViewById(R.id.tv_desc);
            tv_is_sub = view.findViewById(R.id.tv_is_sub);
            tv_sub_count = view.findViewById(R.id.tv_sub_count);
            tv_name = view.findViewById(R.id.tv_name);
        }
        
        private void setIsSub(String mediaId) {
            boolean isExist = dao.queryIsExist(mediaId);
            tv_is_sub.setText(isExist ? "已订阅" : "订阅");
        }
    }
}