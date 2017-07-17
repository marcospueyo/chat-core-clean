package com.mph.chatcontrol.chatlist.viewmodel.mapper;
/* Created by macmini on 17/07/2017. */

import com.mph.chatcontrol.chatlist.viewmodel.ChatViewModel;
import com.mph.chatcontrol.data.Chat;
import com.mph.chatcontrol.data.Mapper;

public class ChatViewModelToChatMapper extends Mapper<ChatViewModel, Chat> {

    public ChatViewModelToChatMapper() {}

    @Override
    public Chat map(ChatViewModel value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ChatViewModel reverseMap(Chat value) {
        ChatViewModel chatViewModel = new ChatViewModel();
        chatViewModel.setTitle(value.title());
        chatViewModel.setDescription(value.description());
        return chatViewModel;
    }
}
