package com.mph.chatcontrol.base.presenter;
/* Created by macmini on 19/07/2017. */

import com.mph.chatcontrol.base.BaseViewModel;

public interface BaseListPresenter extends BasePresenter {
    void onItemClicked(BaseViewModel item);
}
