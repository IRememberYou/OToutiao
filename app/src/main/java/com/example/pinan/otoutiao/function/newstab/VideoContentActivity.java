package com.example.pinan.otoutiao.function.newstab;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.VideoView;

import com.example.pinan.otoutiao.R;
import com.example.pinan.otoutiao.base.init.BaseActivity;
import com.example.pinan.otoutiao.base.init.BaseApplication;
import com.example.pinan.otoutiao.daojishi.RxCountTime;
import com.example.pinan.otoutiao.function.newstab.bean.MultiNewsArticleDataBean;
import com.example.pinan.otoutiao.function.newstab.model.NewsCommentModel;
import com.example.pinan.otoutiao.function.newstab.model.VideoContentModel;
import com.example.pinan.otoutiao.function.newstab.persenter.VideoContentPresenter;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * @author pinan
 * @date 2017/12/1
 */

public class VideoContentActivity extends BaseActivity implements VideoContentModel.View {
    private static final String KEY_NEWS_ARTICLE_DATABEAN = "multinewsarticledatabean";
    private VideoContentPresenter mPresenter;
    private VideoView mVideoView;
    private SeekBar mSb;
    private Handler mHandler = new Handler();
    private boolean isAdvert = false;
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (!isAdvert || mVideoView.isPlaying() || mVideoView.getCurrentPosition() > 3000) {
                isAdvert = true;
                mVideoView.pause();
                mHandler.removeCallbacks(this);
                RxCountTime.countTime(5)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Integer>() {
                        
                        @Override
                        public void onSubscribe(Disposable d) {
                        
                        }
                        
                        @Override
                        public void onNext(Integer integer) {
                            Log.d("VideoContentActivity", integer + "秒后自动播放");
                            isAdvert = false;
                            mVideoView.start();
                        }
                        
                        @Override
                        public void onError(Throwable e) {
                        
                        }
                        
                        @Override
                        public void onComplete() {
                        
                        }
                    });
                return;
            }
            mSb.setProgress(mVideoView.getCurrentPosition());
            Log.d("VideoContentActivity", "缓冲的百分比：" + mVideoView.getBufferPercentage());
            mHandler.postDelayed(this, 1000);
        }
    };
    
    public static void launch(MultiNewsArticleDataBean bean) {
        BaseApplication.sContext.startActivity(new Intent(BaseApplication.sContext, VideoContentActivity.class)
            .putExtra(KEY_NEWS_ARTICLE_DATABEAN, bean)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_video_content_new);
        mPresenter = new VideoContentPresenter(this);
        mVideoView = findViewById(R.id.video_view);
        mSb = findViewById(R.id.sb);
        MultiNewsArticleDataBean bean = (MultiNewsArticleDataBean) getIntent().getSerializableExtra(KEY_NEWS_ARTICLE_DATABEAN);
        mPresenter.doLoadVideoData(bean.video_id);
        mSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mVideoView.seekTo(progress);
            }
            
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            
            }
            
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            
            }
        });
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
            mRunnable = null;
        }
    }
    
    @Override
    public void onShowLoading() {
    
    }
    
    @Override
    public void onSetVideoPlay(String url) {
        mVideoView.setVideoURI(Uri.parse(url));
        mVideoView.start();
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.d("VideoContentActivity", "setOnPreparedListener");
                mSb.setMax(mp.getDuration());
                mHandler.postDelayed(mRunnable, 1000);
            }
        });
        mVideoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                Log.d("2VideoContentActivity", what + "\n" + extra);
                return true;
            }
        });
        
        mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.d("1VideoContentActivity", what + "\n" + extra);
                return true;
            }
        });
        
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d("VideoContentActivity", "*****onCompletion");
                mHandler.removeCallbacksAndMessages(null);
            }
        });
    }
    
    @Override
    public void onLoadData() {
    }
    
    @Override
    public void onHideLoading() {
    
    }
    
    @Override
    public void onShowNetError() {
    
    }
    
    @Override
    public void setPresenter(NewsCommentModel.Presenter presenter) {
    
    }
    
    
    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.bindUntilEvent(ActivityEvent.DESTROY);
    }
    
    @Override
    public void onSetAdapter(List<?> list) {
    
    }
    
    @Override
    public void onShowNoMore() {
    
    }
    
}
