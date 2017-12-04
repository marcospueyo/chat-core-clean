package com.mph.chatcontrol.room.viewmodel.mapper;

import com.mph.chatcontrol.data.Mapper;
import com.mph.chatcontrol.data.Message;
import com.mph.chatcontrol.login.contract.SharedPreferencesRepository;
import com.mph.chatcontrol.room.viewmodel.MessageViewModel;
import com.mph.chatcontrol.utils.CCUtils;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

/* Created by macmini on 25/07/2017. */

public class MessageViewModelToMessageMapper extends Mapper<MessageViewModel, Message> {

    private final SharedPreferencesRepository mSharedPreferencesRepository;

    public MessageViewModelToMessageMapper(
            @Nonnull SharedPreferencesRepository sharedPreferencesRepository) {
        mSharedPreferencesRepository = checkNotNull(sharedPreferencesRepository);
    }

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
                .setIsOwnMessage(checkOwnMessage(value))
                .setSenderName(value.getSenderName())
                .build();
        return messageViewModel;
    }

    private boolean checkOwnMessage(Message message) {
        return message.getSenderId().equals(mSharedPreferencesRepository.getUserID());
    }
}
