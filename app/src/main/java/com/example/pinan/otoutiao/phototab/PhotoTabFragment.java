package com.example.pinan.otoutiao.phototab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pinan.otoutiao.R;
import com.example.pinan.otoutiao.base.BaseFragment;

/**
 *
 * @author pinan
 * @date 2017/11/15
 */

public class PhotoTabFragment extends BaseFragment {
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       return inflater.inflate(R.layout.photo_tab_fragment,container,false);
    }
    
    public static PhotoTabFragment getInstance() {
        return new PhotoTabFragment();
    }
}
