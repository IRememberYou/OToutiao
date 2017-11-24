package com.example.pinan.otoutiao.daojishi;


import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * @author pinan
 * @date 2017/11/24
 */

public class RxCountTime {
    public static Observable<Integer> countTime(int time) {
        if (time < 0) {
            time = 0;
        }
        final int currTime = time;
        return Observable.interval(0, 1, TimeUnit.SECONDS)
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .map(new Function<Long, Integer>() {
                @Override
                public Integer apply(Long aLong) throws Exception {
                    return currTime - aLong.intValue();
                }
            })
            .take(currTime + 1);
    }
    
    public static void use() {
        RxCountTime.countTime(5)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<Integer>() {
                @Override
                public void onSubscribe(Disposable d) {
                    Log.d("onSubscribe", "倒计时开始了");
                }
                
                @Override
                public void onNext(Integer integer) {
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
}
