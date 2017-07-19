package com.mph.chatcontrol.chatlist.viewmodel.mapper;
/* Created by macmini on 17/07/2017. */

import com.mph.chatcontrol.chatlist.viewmodel.ChatViewModel;
import com.mph.chatcontrol.data.Chat;
import com.mph.chatcontrol.data.Mapper;
import com.mph.chatcontrol.utils.DateUtils;

public class ChatViewModelToChatMapper extends Mapper<ChatViewModel, Chat> {

    public static final String CHECKOUT_DATE_FORMAT = "dd/MM/yyyy";

    public static final String LAST_MSG_DATE_FORMAT = "EE";

    public ChatViewModelToChatMapper() {}

    @Override
    public Chat map(ChatViewModel value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ChatViewModel reverseMap(Chat value) {
        ChatViewModel chatViewModel = new ChatViewModel();
        chatViewModel.setTitle(value.title());
        chatViewModel.setInitial(value.title().substring(0, 1));
        chatViewModel.setDescription(value.description());
        chatViewModel.setPendingCount(value.pendingCount());
        chatViewModel.setCheckoutDate(DateUtils.dateToString(value.endDate(), CHECKOUT_DATE_FORMAT));
        chatViewModel.setLastMsgDate(DateUtils.dateToString(value.lastMsgDate(), LAST_MSG_DATE_FORMAT));
        return chatViewModel;
    }
}
