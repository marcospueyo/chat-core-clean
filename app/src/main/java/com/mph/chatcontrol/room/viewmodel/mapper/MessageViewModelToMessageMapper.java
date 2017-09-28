package com.mph.chatcontrol.room.viewmodel.mapper;

import com.mph.chatcontrol.data.Mapper;
import com.mph.chatcontrol.data.Message;
import com.mph.chatcontrol.room.viewmodel.MessageViewModel;
import com.mph.chatcontrol.utils.CCUtils;
import com.mph.chatcontrol.utils.DateUtils;

/* Created by macmini on 25/07/2017. */

public class MessageViewModelToMessageMapper extends Mapper<MessageViewModel, Message> {

    @Override
    public Message map(MessageViewModel value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public MessageViewModel reverseMap(Message value) {
        MessageViewModel messageViewModel;
        messageViewModel = MessageViewModel.builder()
                .setId(value.getId())
                .setText(value.getText())
                .setTimestamp(CCUtils.getFormattedMessageDate(value.getDate()))
                .setIsOwnMessage(value.isOwnMessage())
                .setSenderName(value.getSenderName())
                .build();
        return messageViewModel;
    }
}
