package com.mph.chatcontrol.room.viewmodel.mapper;

import com.mph.chatcontrol.data.Mapper;
import com.mph.chatcontrol.data.Message;
import com.mph.chatcontrol.room.viewmodel.MessageViewModel;
import com.mph.chatcontrol.utils.DateUtils;

/* Created by macmini on 25/07/2017. */

public class MessageViewModelToMessageMapper extends Mapper<MessageViewModel, Message> {

    private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm";

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
                .setTimestamp(DateUtils.dateToString(value.getDate(), DATE_FORMAT))
                .setIsOwnMessage(value.isOwnMessage())
                .setSenderName(value.getSenderName())
                .build();
        return messageViewModel;
    }
}
