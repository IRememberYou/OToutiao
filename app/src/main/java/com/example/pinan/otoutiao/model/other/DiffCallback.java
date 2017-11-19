package com.example.pinan.otoutiao.model.other;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;

import com.example.pinan.otoutiao.newstab.bean.MultiNewsArticleDataBean;

import java.util.List;

/**
 * Created by Meiji on 2017/4/18.
 */

public class DiffCallback extends DiffUtil.Callback {
    
    public static final int MUlTI_NEWS = 7;
    private List oldList, newList;
    private int type;
    
    public DiffCallback(List oldList, List newList, int type) {
        this.oldList = oldList;
        this.newList = newList;
        this.type = type;
    }
    
    /**
     * 刷新
     */
    public static void notifyDataSetChanged(List oldList, List newList, int type, RecyclerView.Adapter adapter) {
        DiffCallback diffCallback = new DiffCallback(oldList, newList, type);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(diffCallback, true);
        result.dispatchUpdatesTo(adapter);
    }
    
    @Override
    public int getOldListSize() {
        return oldList != null ? oldList.size() : 0;
    }
    
    @Override
    public int getNewListSize() {
        return newList != null ? newList.size() : 0;
    }
    
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        try {
            switch (type) {
                case MUlTI_NEWS:
                    return ((MultiNewsArticleDataBean) oldList.get(oldItemPosition)).title.equals(
                        ((MultiNewsArticleDataBean) newList.get(newItemPosition)).title);
                default:
                    break;
            }
        } catch (Exception e) {
        }
        return false;
    }
    
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        try {
            switch (type) {
                case MUlTI_NEWS:
                    return ((MultiNewsArticleDataBean) oldList.get(oldItemPosition)).item_id ==
                        ((MultiNewsArticleDataBean) newList.get(newItemPosition)).item_id;
                default:
                    break;
            }
        } catch (Exception e) {
        }
        return false;
    }
}