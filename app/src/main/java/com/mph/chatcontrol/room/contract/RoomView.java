package com.mph.chatcontrol.room.contract;
/* Created by macmini on 24/07/2017. */

import com.mph.chatcontrol.base.presenter.BaseListView;
import com.mph.chatcontrol.chatlist.viewmodel.ChatViewModel;
import com.mph.chatcontrol.room.viewmodel.MessageViewModel;

import java.util.List;

public interface RoomView extends BaseListView {

    void setRoom(ChatViewModel room);

    void setMessages(List<MessageViewModel> messages);

    void addMessage(MessageViewModel message);

    void setChatEnabled(boolean enabled);

    void handleMessageSendSuccess();

    void handleMessageSendError();
}
