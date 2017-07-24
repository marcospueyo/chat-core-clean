package com.mph.chatcontrol.room;
/* Created by macmini on 24/07/2017. */

import android.support.annotation.NonNull;
import android.util.Log;

import com.mph.chatcontrol.base.BaseViewModel;

import static com.google.common.base.Preconditions.checkNotNull;

public class RoomPresenterImpl implements RoomPresenter {

    private static final String TAG = RoomPresenterImpl.class.getSimpleName();

    @NonNull private final RoomView mRoomView;
    @NonNull private String mRoomID;

    public RoomPresenterImpl(@NonNull RoomView roomView, @NonNull String roomID) {
        mRoomID = checkNotNull(roomID);
        mRoomView = checkNotNull(roomView);
        mRoomView.setPresenter(this);
    }

    @Override
    public void start() {
        mRoomView.showProgress();
    }

    @Override
    public void onItemClicked(BaseViewModel item) {
        Log.d(TAG, "onItemClicked: " + item.toString());
    }

    @Override
    public void onMessageSendClick(String message) {
        Log.d(TAG, "onMessageSendClick: " + message);
    }

    @Override
    public void bindView() {

    }

    @Override
    public void unbindView() {

    }
}
