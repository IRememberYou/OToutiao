package com.example.pinan.otoutiao.model.binder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pinan.otoutiao.R;
import com.example.pinan.otoutiao.model.bean.PhotoBean;

import me.drakeet.multitype.ItemViewBinder;

/**
 * @author pinan
 * @date 2017/11/23
 */

public class PhotoBinder extends ItemViewBinder<PhotoBean, PhotoBinder.MyViewHolder> {
    
    @NonNull
    @Override
    protected MyViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_photo_binder, parent, false);
        return new MyViewHolder(view);
    }
    
    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, @NonNull PhotoBean item) {
        holder.name.setText(item.name);
        holder.size.setText(item.size+"");
    }
    
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name, size;
        
        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tv_name);
            size = view.findViewById(R.id.tv_size);
        }
    }
    
}
