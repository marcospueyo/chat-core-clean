package com.mph.chatcontrol.room;
/* Created by macmini on 24/07/2017. */

import android.support.annotation.NonNull;
import android.util.Log;

import com.mph.chatcontrol.base.BaseViewModel;
import com.mph.chatcontrol.chatlist.viewmodel.mapper.ChatViewModelToChatMapper;
import com.mph.chatcontrol.data.Chat;
import com.mph.chatcontrol.data.Message;
import com.mph.chatcontrol.room.contract.GetMessagesInteractor;
import com.mph.chatcontrol.room.contract.GetRoomInteractor;
import com.mph.chatcontrol.room.contract.RoomPresenter;
import com.mph.chatcontrol.room.contract.RoomView;
import com.mph.chatcontrol.room.viewmodel.mapper.MessageViewModelToMessageMapper;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class RoomPresenterImpl implements RoomPresenter, GetRoomInteractor.OnFinishedListener,
        GetMessagesInteractor.OnFinishedListener {

    private static final String TAG = RoomPresenterImpl.class.getSimpleName();

    @NonNull private final RoomView mRoomView;
    @NonNull private String mRoomID;

    @NonNull private final ChatViewModelToChatMapper mChatMapper;
    @NonNull private final MessageViewModelToMessageMapper mMessageMapper;

    @NonNull private final GetRoomInteractor mGetRoomInteractor;
    @NonNull private final GetMessagesInteractor mGetMessagesInteractor;

    public RoomPresenterImpl(@NonNull RoomView roomView, @NonNull String roomID,
                             @NonNull ChatViewModelToChatMapper chatMapper,
                             @NonNull MessageViewModelToMessageMapper messageMapper,
                             @NonNull GetRoomInteractor getRoomInteractor,
                             @NonNull GetMessagesInteractor getMessagesInteractor) {
        mRoomID = checkNotNull(roomID);
        mChatMapper = checkNotNull(chatMapper);
        mMessageMapper = checkNotNull(messageMapper);
        mGetRoomInteractor = checkNotNull(getRoomInteractor);
        mGetMessagesInteractor = checkNotNull(getMessagesInteractor);
        mRoomView = checkNotNull(roomView);
        mRoomView.setPresenter(this);
    }

    @Override
    public void start() {
        mRoomView.showProgress();
        mGetRoomInteractor.execute(mRoomID, this);
        mGetMessagesInteractor.execute(mRoomID, this);
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
        mRoomView.setRoom(mChatMapper.reverseMap(chat));
    }

    @Override
    public void onRoomLoadError() {
        mRoomView.showLoadError();
    }

    @Override
    public void onMessagesLoaded(List<Message> messages) {
        mRoomView.setMessages(mMessageMapper.reverseMap(messages));
        mRoomView.hideProgress();
    }

    @Override
    public void onMessagesLoadError() {
        mRoomView.showLoadError();
    }
}
