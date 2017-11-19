package com.example.pinan.otoutiao.model;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by pinan on 2017/11/17.
 */

public abstract class LazyLoadFragment<T extends BasePresenter> extends BaseFragment<T> {
    
    private boolean isViewInitated;
    private boolean isVisibleToUser;
    private boolean isDataInitated;
    
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitated = true;
        prepareFetchData();
    }
    
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }
    
    protected boolean prepareFetchData() {
        return prepareFetchData(false);
    }
    
    private boolean prepareFetchData(boolean forceUpdate) {
        boolean isFetchData = isViewInitated && isVisibleToUser && (!isDataInitated || forceUpdate);
        if (isFetchData) {
            fetchData();
            isDataInitated = true;
            return true;
        }
        return false;
    }
    
    /**
     * 获取数据
     */
    protected abstract void fetchData();
}
