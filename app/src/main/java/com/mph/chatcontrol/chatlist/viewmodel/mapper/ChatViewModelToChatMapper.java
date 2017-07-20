package com.mph.chatcontrol.chatlist.viewmodel.mapper;
/* Created by macmini on 17/07/2017. */

import com.mph.chatcontrol.chatlist.viewmodel.ChatViewModel;
import com.mph.chatcontrol.data.Chat;
import com.mph.chatcontrol.data.Mapper;
import com.mph.chatcontrol.utils.DateUtils;

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
                .setId(value.id())
                .setTitle(value.title())
                .setInitial(value.title().substring(0, 1))
                .setDescription(value.description())
                .setPendingCount(value.pendingCount())
                .setCheckoutDate(DateUtils.dateToString(value.endDate(), CHECKOUT_DATE_FORMAT))
                .setLastMsgDate(DateUtils.dateToString(value.lastMsgDate(), LAST_MSG_DATE_FORMAT))
                .setLastActivity(value.lastMsg())
                .setActive(value.isActive())
                .build();
        return chatViewModel;
    }
}
