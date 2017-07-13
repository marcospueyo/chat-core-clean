package com.mph.chatcontrol.main;
/* Created by Marcos on 13/07/2017.*/

import android.os.Handler;

import com.mph.chatcontrol.main.utils.MenuOptions;

public class MainInteractorImpl implements MainInteractor {
    @Override
    public void loadView(MenuOptions.Option option, final OnViewLoadedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onViewLoaded();
            }
        }, 1000);
    }
}
