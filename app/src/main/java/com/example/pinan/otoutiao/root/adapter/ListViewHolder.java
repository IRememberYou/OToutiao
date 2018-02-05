package com.example.pinan.otoutiao.root.adapter;

import android.view.View;
import android.widget.TextView;

/**
 * @author pinan
 */
public class ListViewHolder {
    private View itemView;
    
    public ListViewHolder(View view) {
        this.itemView = view;
    }
    
    public ListViewHolder setText(int id, String text) {
        ((TextView) itemView.findViewById(id)).setText(text);
        return this;
    }
    
    public <T> ListViewHolder setOnClickListener(int id, View.OnClickListener listener, T... tag) {
        View view = itemView.findViewById(id);
        if (tag != null) {
            view.setTag(tag[0]);
        }
        view.setOnClickListener(listener);
        return this;
    }
    
    public <T> ListViewHolder setOnLongClickListener(int id, View.OnLongClickListener listener, T... tag) {
        View view = itemView.findViewById(id);
        if (tag != null) {
            view.setTag(tag[0]);
        }
        view.setOnLongClickListener(listener);
        return this;
    }
    
    public <T extends View> T getView(int id) {
        return (T) itemView.findViewById(id);
    }
    
    public <T extends View> T getItemView() {
        return (T) itemView;
    }
}