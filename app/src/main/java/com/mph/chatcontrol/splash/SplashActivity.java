package com.mph.chatcontrol.splash;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mph.chatcontrol.R;
import com.mph.chatcontrol.login.LoginActivity;

/* Created by macmini on 03/08/2017. */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateToLogin();
            }
        }, 1000);
    }

    private void navigateToLogin() {
        startActivity(LoginActivity.newInstance(this));
        finish();
    }

}
