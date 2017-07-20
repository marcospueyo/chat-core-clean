package com.mph.chatcontrol.guestlist.contract;
/* Created by macmini on 19/07/2017. */

import com.mph.chatcontrol.base.presenter.BaseListPresenter;
import com.mph.chatcontrol.guestlist.viewmodel.GuestViewModel;

public interface GuestListPresenter extends BaseListPresenter {
    void onCallClicked(GuestViewModel guest);
    void onChatClicked(GuestViewModel guest);
}
