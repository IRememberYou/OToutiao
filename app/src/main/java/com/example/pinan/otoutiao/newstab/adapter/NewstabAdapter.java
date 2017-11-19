package com.example.pinan.otoutiao.newstab.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.pinan.otoutiao.R;
import com.example.pinan.otoutiao.newstab.bean.MultiNewsArticleDataBean;

import java.util.List;

/**
 *
 * @author pinan
 * @date 2017/11/16
 */

public class NewstabAdapter extends BaseQuickAdapter<MultiNewsArticleDataBean> {
    public NewstabAdapter(List<MultiNewsArticleDataBean> data) {
        super(R.layout.item_newstab, data);
    }
    
    @Override
    protected void convert(BaseViewHolder holder, MultiNewsArticleDataBean dataBean) {
        holder.setText(R.id.tv_title, dataBean.title);
    }
}
