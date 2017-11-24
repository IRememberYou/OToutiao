package com.example.pinan.otoutiao.function.videotab;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.pinan.otoutiao.R;
import com.example.pinan.otoutiao.base.init.BaseFragment;
import com.example.pinan.otoutiao.daojishi.RxCountTime;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * @author pinan
 * @date 2017/11/15
 */

public class VideoTabFragment extends BaseFragment {
    
    private TextView mTv;
    
    public static VideoTabFragment getInstance() {
        return new VideoTabFragment();
    }
    
    @Override
    protected int attachLayoutId() {
        return R.layout.video_tab_fragment;
    }
    
    @Override
    protected void initView(View view) {
        mTv = view.findViewById(R.id.tv);
        RxCountTime.countTime(5)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<Integer>() {
                @Override
                public void onSubscribe(Disposable d) {
                    Log.d("onSubscribe", "倒计时开始了");
                }
            
                @Override
                public void onNext(Integer integer) {
                    mTv.setText("当前计时：" + integer);
                    Log.d("onNext", "当前计时：" + integer);
                }
            
                @Override
                public void onError(Throwable e) {
                
                }
            
                @Override
                public void onComplete() {
                    Log.d("RxCountTime", "倒计时完成了");
                }
            });
    
    }
    
    @Override
    protected void initData() throws NullPointerException {
    
    }
    
    @Override
    public void onShowLoading() {
    
    }
    
    @Override
    public void onHideLoading() {
    
    }
    
    @Override
    public void onShowNetError() {
    
    }
    
    @Override
    public void setPresenter(Object presenter) {
    
    }
}
