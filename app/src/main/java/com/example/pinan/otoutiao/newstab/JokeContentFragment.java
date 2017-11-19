package com.example.pinan.otoutiao.newstab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pinan.otoutiao.R;
import com.example.pinan.otoutiao.base.BaseFragment;

import butterknife.Bind;

/**
 * @author pinan
 * @date 2017/11/16
 */

public class JokeContentFragment extends BaseFragment {
    private String TAG = "JokeContentFragment";
    @Bind(R.id.tv)
    TextView tv;
    
    public static JokeContentFragment getInstance() {
        return new JokeContentFragment();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.joke_content_fragment, container, false);
    }
    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv.setText(TAG+"3333333");
    }
}
