package com.example.pinan.otoutiao.model.bean;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;

import com.example.pinan.otoutiao.function.newstab.bean.MultiNewsArticleDataBean;

import java.util.List;

/**
 * @author Meiji
 * @date 2017/4/18
 */

public class DiffCallback extends DiffUtil.Callback {
    public static final int PHOTO = 1;
    public static final int MUlTI_NEWS = 7;
    private List oldList, newList;
    private int type;
    
    private DiffCallback(List oldList, List newList, int type) {
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
        Object olds = oldList.get(oldItemPosition);
        Object news = newList.get(newItemPosition);
        switch (type) {
            case MUlTI_NEWS:
                if (olds instanceof MultiNewsArticleDataBean && news instanceof MultiNewsArticleDataBean) {
                    return ((MultiNewsArticleDataBean) olds).title.equals(((MultiNewsArticleDataBean) news).title);
                }
                break;
            case PHOTO:
                if (olds instanceof PhotoBean && news instanceof PhotoBean) {
                    return ((PhotoBean) olds).name.equals(((PhotoBean) news).name);
                }
                break;
            default:
                break;
        }
        return true;
    }
    
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Object olds = oldList.get(oldItemPosition);
        Object news = newList.get(newItemPosition);
        switch (type) {
            case MUlTI_NEWS:
                if (olds instanceof MultiNewsArticleDataBean && news instanceof MultiNewsArticleDataBean) {
                    return ((MultiNewsArticleDataBean) olds).item_id == (((MultiNewsArticleDataBean) news).item_id);
                }
                break;
            case PHOTO:
                if (olds instanceof PhotoBean && news instanceof PhotoBean) {
                    return ((PhotoBean) olds).id.equals(((PhotoBean) news).id);
                }
                break;
            default:
                break;
        }
        return true;
    }
}