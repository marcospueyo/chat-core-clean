package com.mph.chatcontrol.guestlist.contract;
/* Created by macmini on 19/07/2017. */

import android.util.Pair;

import com.mph.chatcontrol.base.presenter.BaseListView;
import com.mph.chatcontrol.chatlist.viewmodel.ChatViewModel;
import com.mph.chatcontrol.data.Chat;
import com.mph.chatcontrol.data.ChatInfo;
import com.mph.chatcontrol.guestlist.viewmodel.GuestViewModel;

import java.util.List;

public interface GuestListView extends BaseListView {

    void setItems(List<GuestViewModel> guests);

    void callGuest(GuestViewModel guest);

    void showRoomLoadError();

    void openChat(String chatID);
}
