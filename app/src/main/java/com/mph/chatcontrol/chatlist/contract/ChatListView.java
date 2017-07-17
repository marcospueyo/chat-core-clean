package com.mph.chatcontrol.chatlist.contract;
/* Created by macmini on 17/07/2017. */

import com.mph.chatcontrol.base.BaseView;
import com.mph.chatcontrol.chatlist.contract.ChatListPresenter;
import com.mph.chatcontrol.chatlist.viewmodel.ChatViewModel;
import com.mph.chatcontrol.data.Chat;

import java.util.List;

public interface ChatListView extends BaseView<ChatListPresenter> {

    void showProgress();

    void hideProgress();

    void setChats(List<ChatViewModel> chats);

    void showLoadError();
}
