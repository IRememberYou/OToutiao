package com.example.pinan.otoutiao.root;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pinan.otoutiao.R;
import com.example.pinan.otoutiao.base.init.BaseActivity;
import com.example.pinan.otoutiao.utils.email.SendMailUtil;

/**
 * @author pinan
 * @date 2018/2/5
 */

public class BackActivity extends BaseActivity {
    EditText mEditText;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back);
        mEditText = findViewById(R.id.et_content);
    }
    
    public void onBack(View view) {
        String content = mEditText.getText().toString();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        SendMailUtil.send(content);
        Toast.makeText(this, "反馈成功", Toast.LENGTH_SHORT).show();
        finish();
    }
}
