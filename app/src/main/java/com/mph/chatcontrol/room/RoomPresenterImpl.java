package com.mph.chatcontrol.room;
/* Created by macmini on 24/07/2017. */

import android.support.annotation.NonNull;
import android.util.Log;

import com.mph.chatcontrol.base.BaseViewModel;
import com.mph.chatcontrol.chatlist.viewmodel.mapper.ChatViewModelToChatMapper;
import com.mph.chatcontrol.data.Chat;
import com.mph.chatcontrol.room.contract.GetRoomInteractor;
import com.mph.chatcontrol.room.contract.RoomPresenter;
import com.mph.chatcontrol.room.contract.RoomView;

import static com.google.common.base.Preconditions.checkNotNull;

public class RoomPresenterImpl implements RoomPresenter, GetRoomInteractor.OnFinishedListener {

    private static final String TAG = RoomPresenterImpl.class.getSimpleName();

    @NonNull private final RoomView mRoomView;
    @NonNull private String mRoomID;
    @NonNull private final ChatViewModelToChatMapper mMapper;
    @NonNull private final GetRoomInteractor mGetRoomInteractor;

    public RoomPresenterImpl(@NonNull RoomView roomView, @NonNull String roomID,
                             @NonNull ChatViewModelToChatMapper mapper,
                             @NonNull GetRoomInteractor getRoomInteractor) {
        mRoomID = checkNotNull(roomID);
        mMapper = checkNotNull(mapper);
        mGetRoomInteractor = checkNotNull(getRoomInteractor);
        mRoomView = checkNotNull(roomView);
        mRoomView.setPresenter(this);
    }

    @Override
    public void start() {
        mRoomView.showProgress();
        mGetRoomInteractor.execute(mRoomID, this);
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

    @Override
    public void onRoomLoaded(Chat chat) {
        mRoomView.setRoom(mMapper.reverseMap(chat));
    }

    @Override
    public void onRoomLoadError() {
        mRoomView.showLoadError();
    }
}
