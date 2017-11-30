package com.mph.chatcontrol.chatlist.contract;
/* Created by macmini on 17/07/2017. */

import com.mph.chatcontrol.base.presenter.BaseListView;
import com.mph.chatcontrol.chatlist.viewmodel.ChatViewModel;

import java.util.List;

public interface ChatListView extends BaseListView {

    void setItems(List<ChatViewModel> chats);

    void updateItem(ChatViewModel chat);

    void openChat(ChatViewModel chat);

    void showUpdateError();
}
