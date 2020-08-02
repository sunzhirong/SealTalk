package cn.rongcloud.im.niko.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import cn.rongcloud.im.R;

import androidx.annotation.Nullable;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        startActivity(new Intent(this, MainActivity.class));
//        startActivity(new Intent(this, TestActivity1.class));
//        startActivity(new Intent(this, MyLikedActivity.class));
        finish();
    }

    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

//    @Override
//    protected void initView() {
//        startActivity(new Intent(this, MainActivity.class));
//        finish();
//        if (intentUri != null) {
//            goWithUri();
//        } else {
//            finish();
//        }
//    }
}
