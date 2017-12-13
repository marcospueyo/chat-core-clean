package com.mph.chatcontrol.main;
/* Created by Marcos on 13/07/2017.*/

public interface MainPresenter {

    void onMenuOptionSelected(Integer order);

    void onLogout();

    void onOpenChat(String chatID);

    void onStart();

    void onResume();

    void onDestroy();

    void onRefresh(Integer order);

    void onSearch(Integer order, String query);

    void onSearchDisabled(Integer order);
}