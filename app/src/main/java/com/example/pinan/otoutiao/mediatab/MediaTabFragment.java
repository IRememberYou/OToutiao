package com.example.pinan.otoutiao.mediatab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pinan.otoutiao.R;
import com.example.pinan.otoutiao.base.BaseFragment;

/**
 * @author pinan
 * @date 2017/11/15
 */

public class MediaTabFragment extends BaseFragment {
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.media_tab_fragment, container, false);
    }
    
    public static MediaTabFragment getInstance() {
        return new MediaTabFragment();
    }
}
