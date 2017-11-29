package com.mph.chatcontrol.chatlist.viewmodel.mapper;
/* Created by macmini on 17/07/2017. */

import android.util.Pair;

import com.mph.chatcontrol.chatlist.viewmodel.ChatViewModel;
import com.mph.chatcontrol.data.Chat;
import com.mph.chatcontrol.data.ChatInfo;
import com.mph.chatcontrol.data.Mapper;
import com.mph.chatcontrol.utils.CCUtils;
import com.mph.chatcontrol.utils.DateUtils;
import com.mph.chatcontrol.utils.RoomUtils;

import java.util.Date;

public class ChatViewModelToChatMapper extends Mapper<ChatViewModel, Pair<Chat, ChatInfo>> {

    private static final String CHECKOUT_DATE_FORMAT = "dd/MM/yyyy";

    @Override
    public ChatViewModel reverseMap(Pair<Chat, ChatInfo> pair) {
        Chat chat = pair.first;
        ChatInfo info = pair.second;
        return ChatViewModel.builder()
                .setId(chat.getId())
                .setTitle(chat.getGuestName())
                .setInitial(chat.getGuestName().substring(0, 1))
                .setDescription(chat.getPropertyName())
                .setPendingCount(chat.getMessageCount() - info.getReadCount())
                .setCheckoutDate(DateUtils.dateToString(chat.getEndDate(), CHECKOUT_DATE_FORMAT))
                .setLastMsgDate(formatLastMsgDate(chat.getLastMsgDate()))
                .setLastActivity(chat.getLastMsg())
                .setActive(chatIsActive(chat))
                .build();
    }

    @Override
    public Pair<Chat, ChatInfo> map(ChatViewModel value) {
        throw new UnsupportedOperationException();
    }

    private String formatLastMsgDate(Date date) {
        return date == null ? "" : CCUtils.getFormattedLastActivity(date);
    }

    private boolean chatIsActive(Chat chat) {
        return RoomUtils.roomIsActive(chat, new Date());
    }
}
