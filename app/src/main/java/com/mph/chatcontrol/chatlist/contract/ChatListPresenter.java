package com.mph.chatcontrol.chatlist.contract;
/* Created by macmini on 17/07/2017. */

import com.mph.chatcontrol.base.BasePresenter;
import com.mph.chatcontrol.chatlist.viewmodel.ChatViewModel;

public interface ChatListPresenter extends BasePresenter {

    void onItemClicked(ChatViewModel chat);


}
