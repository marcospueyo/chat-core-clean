package com.mph.chatcontrol.chatlist.viewmodel.mapper;
/* Created by macmini on 17/07/2017. */

import android.util.Log;

import com.mph.chatcontrol.chatlist.viewmodel.ChatViewModel;
import com.mph.chatcontrol.data.Chat;
import com.mph.chatcontrol.data.Mapper;
import com.mph.chatcontrol.utils.CCUtils;
import com.mph.chatcontrol.utils.DateUtils;
import com.mph.chatcontrol.utils.RoomUtils;

import java.util.Date;

public class ChatViewModelToChatMapper extends Mapper<ChatViewModel, Chat> {

    public static final String CHECKOUT_DATE_FORMAT = "dd/MM/yyyy";

    public static final String LAST_MSG_DATE_FORMAT = "EE";

    @Override
    public Chat map(ChatViewModel value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ChatViewModel reverseMap(Chat value) {
        ChatViewModel chatViewModel = ChatViewModel.builder()
                .setId(value.getId())
                .setTitle(value.getGuestName())
                .setInitial(value.getGuestName().substring(0, 1))
                .setDescription(value.getPropertyName())
                .setPendingCount(value.getPendingCount())
                .setCheckoutDate(DateUtils.dateToString(value.getEndDate(), CHECKOUT_DATE_FORMAT))
                .setLastMsgDate(formatLastMsgDate(value.getLastMsgDate()))
                .setLastActivity(value.getLastMsg())
                .setActive(chatIsActive(value))
                .build();
        return chatViewModel;
    }

    private String formatLastMsgDate(Date date) {
        return date == null ? "" : CCUtils.getFormattedLastActivity(date);
    }





    private boolean chatIsActive(Chat chat) {
        return RoomUtils.roomIsActive(chat, new Date());
    }
}
