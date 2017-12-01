package com.example.pinan.otoutiao.model.binder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pinan.otoutiao.R;
import com.example.pinan.otoutiao.function.newstab.bean.MediaWendaBean;
import com.example.pinan.otoutiao.utils.ShareIntentAction;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;
import me.drakeet.multitype.ItemViewBinder;

/**
 * @author pinan
 * @date 2017/12/1
 */

public class MediaWendaViewBinder extends ItemViewBinder<MediaWendaBean.AnswerQuestionBean, MediaWendaViewBinder.ViewHolder> {
    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View inflate = inflater.inflate(R.layout.media_wenda_view_binder, parent, false);
        return new ViewHolder(inflate);
    }
    
    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final MediaWendaBean.AnswerQuestionBean item) {
        final Context context = holder.itemView.getContext();
        
        try {
            MediaWendaBean.AnswerQuestionBean.AnswerBean answerBean = item.answer;
            MediaWendaBean.AnswerQuestionBean.QuestionBean questionBean = item.question;
            
            final String title = questionBean.title;
            String abstractX = answerBean.content_abstract.text;
            String readCount = answerBean.brow_count + "个回答";
            String time = answerBean.show_time;
            
            holder.tv_title.setText(title);
            holder.tv_abstract.setText(abstractX);
            holder.tv_extra.setText(readCount + "  -  " + time);
            
            RxView.clicks(holder.itemView)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Object o) throws Exception {
//                        WendaContentBean.AnsListBean ansBean = new WendaContentBean.AnsListBean();
//                        WendaContentBean.AnsListBean.ShareDataBeanX shareBean = new WendaContentBean.AnsListBean.ShareDataBeanX();
//                        WendaContentBean.AnsListBean.UserBeanX userBean = new WendaContentBean.AnsListBean.UserBeanX();
//
//                        ansBean.setTitle(title);
//                        ansBean.setQid(item.getQuestion().getQid());
//                        ansBean.setAnsid(item.getQuestion().getQid());
//                        shareBean.setShare_url(item.getAnswer().getWap_url());
//                        userBean.setUname(item.getAnswer().getUser().getUname());
//                        userBean.setAvatar_url(item.getAnswer().getUser().getAvatar_url());
//                        ansBean.setShare_data(shareBean);
//                        ansBean.setUser(userBean);
//
//                        WendaDetailActivity.launch(ansBean);
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
                                ShareIntentAction.send(context, title + "\n" + item.answer.wap_url);
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title;
        private TextView tv_abstract;
        private TextView tv_extra;
        private ImageView iv_dots;
        
        ViewHolder(View itemView) {
            super(itemView);
            this.tv_title = itemView.findViewById(R.id.tv_title);
            this.tv_abstract = itemView.findViewById(R.id.tv_abstract);
            this.tv_extra = itemView.findViewById(R.id.tv_extra);
            this.iv_dots = itemView.findViewById(R.id.iv_dots);
        }
    }
}
