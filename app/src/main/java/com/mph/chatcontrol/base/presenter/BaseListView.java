package com.mph.chatcontrol.base.presenter;
/* Created by macmini on 19/07/2017. */

import com.mph.chatcontrol.base.BaseView;
import com.mph.chatcontrol.base.BaseViewModel;
import com.mph.chatcontrol.chatlist.viewmodel.ChatViewModel;

import java.util.List;

public interface BaseListView extends BaseView<BaseListPresenter> {

    void showProgress();

    void hideProgress();

    void showLoadError();
}
