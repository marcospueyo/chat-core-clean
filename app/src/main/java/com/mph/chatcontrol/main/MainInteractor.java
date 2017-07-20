package com.mph.chatcontrol.main;
/* Created by Marcos on 13/07/2017.*/

import com.mph.chatcontrol.utils.MenuOptions;

public interface MainInteractor {
    interface OnViewLoadedListener {
        void onViewLoaded();
        void onError();
    }

    void loadView(MenuOptions.Option option, OnViewLoadedListener listener);
}
