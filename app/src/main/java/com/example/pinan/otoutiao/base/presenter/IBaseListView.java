package com.example.pinan.otoutiao.base.presenter;

import java.util.List;

/**
 * @author Meiji
 * @date 2017/7/5
 */

public interface IBaseListView<T> extends IBaseView<T> {
    /**
     * 设置适配器
     *
     * @param list 数据
     */
    void onSetAdapter(List<?> list);
    
    /**
     * 加载完毕
     */
    void onShowNoMore();
}
