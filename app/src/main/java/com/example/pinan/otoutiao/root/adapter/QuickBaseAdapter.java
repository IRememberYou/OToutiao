package com.example.pinan.otoutiao.root.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pinan
 * @date 2017/12/6
 */

public abstract class QuickBaseAdapter<T, H extends ListViewHolder> extends BaseAdapter {
    private List<T> datas;
    private int layoutId;
    
    public QuickBaseAdapter(int layoutId, List<T> datas) {
        if (datas != null) {
            this.datas = datas;
        } else {
            this.datas = new ArrayList<>();
        }
        this.layoutId = layoutId;
    }
    
    @Override
    public int getCount() {
        return datas.size();
    }
    
    @Override
    public T getItem(int position) {
        return datas.get(position);
    }
    
    @Override
    public long getItemId(int position) {
        return 0;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
            holder = new ListViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ListViewHolder) convertView.getTag();
        }
        convert(holder, getItem(position));
        return convertView;
    }
    
    protected abstract void convert(ListViewHolder holder, T bean);
    
    /**
     * 添加数据
     *
     * @param list   数据集
     * @param append true，表示不删除原有的数据，则反之，默认是false
     */
    protected void addData(List<T> list, boolean... append) {
        if (!(append != null && append[0])) {
            datas.clear();
        }
        datas.addAll(list);
        notifyDataSetChanged();
    }
}
