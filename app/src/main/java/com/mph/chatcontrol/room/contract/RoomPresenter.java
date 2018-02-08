package com.mph.chatcontrol.room.contract;

import android.graphics.Bitmap;

import com.mph.chatcontrol.base.presenter.BaseListPresenter;

/* Created by macmini on 24/07/2017. */

public interface RoomPresenter extends BaseListPresenter {

    void onMessageSendClick(String message);

    void bindView();

    void unbindView();

    void setNewRoomID(String roomID);

    void imageSelected(Bitmap bitmap);
}
