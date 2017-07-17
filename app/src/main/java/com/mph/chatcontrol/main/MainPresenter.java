package com.mph.chatcontrol.main;
/* Created by Marcos on 13/07/2017.*/

public interface MainPresenter {
    void onMenuOptionSelected(Integer order);
    void onStart();
    void onResume();
    void onDestroy();
}
