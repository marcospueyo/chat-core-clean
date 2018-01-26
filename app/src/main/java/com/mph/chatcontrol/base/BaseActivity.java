package com.mph.chatcontrol.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics;

/* Created by Marcos on 26/01/2018.*/

public abstract class BaseActivity extends AppCompatActivity {


    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendScreenView(getViewName());
    }

    protected void sendScreenView(String viewName) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, viewName);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    protected abstract String getViewName();
}
