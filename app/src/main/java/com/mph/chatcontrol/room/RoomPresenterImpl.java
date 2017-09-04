package com.mph.chatcontrol.room;
/* Created by macmini on 24/07/2017. */

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.mph.chatcontrol.base.BaseViewModel;
import com.mph.chatcontrol.chatlist.viewmodel.mapper.ChatViewModelToChatMapper;
import com.mph.chatcontrol.data.Chat;
import com.mph.chatcontrol.data.Message;
import com.mph.chatcontrol.room.contract.GetMessagesInteractor;
import com.mph.chatcontrol.room.contract.GetRoomInteractor;
import com.mph.chatcontrol.room.contract.RoomPresenter;
import com.mph.chatcontrol.room.contract.RoomView;
import com.mph.chatcontrol.room.contract.SendMessageInteractor;
import com.mph.chatcontrol.room.contract.UpdateSeenStatusInteractor;
import com.mph.chatcontrol.room.viewmodel.MessageViewModel;
import com.mph.chatcontrol.room.viewmodel.mapper.MessageViewModelToMessageMapper;
import com.mph.chatcontrol.utils.RoomUtils;

import java.util.Date;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class RoomPresenterImpl implements RoomPresenter, GetRoomInteractor.OnFinishedListener,
        GetMessagesInteractor.OnFinishedListener, SendMessageInteractor.OnFinishedListener,
        UpdateSeenStatusInteractor.OnFinishedListener {

    private static final String TAG = RoomPresenterImpl.class.getSimpleName();

    @NonNull private final RoomView mRoomView;
    @NonNull private String mRoomID;
    private Chat mRoom;

    @NonNull private final ChatViewModelToChatMapper mChatMapper;
    @NonNull private final MessageViewModelToMessageMapper mMessageMapper;

    @NonNull private final GetRoomInteractor mGetRoomInteractor;
    @NonNull private final GetMessagesInteractor mGetMessagesInteractor;
    @NonNull private final SendMessageInteractor mSendMessageInteractor;
    @NonNull private final UpdateSeenStatusInteractor mUpdateSeenStatusInteractor;

    public RoomPresenterImpl(@NonNull RoomView roomView, @NonNull String roomID,
                             @NonNull ChatViewModelToChatMapper chatMapper,
                             @NonNull MessageViewModelToMessageMapper messageMapper,
                             @NonNull GetRoomInteractor getRoomInteractor,
                             @NonNull GetMessagesInteractor getMessagesInteractor,
                             @NonNull SendMessageInteractor sendMessageInteractor,
                             @NonNull UpdateSeenStatusInteractor updateSeenStatusInteractor) {
        mRoomID = checkNotNull(roomID);
        mChatMapper = checkNotNull(chatMapper);
        mMessageMapper = checkNotNull(messageMapper);
        mGetRoomInteractor = checkNotNull(getRoomInteractor);
        mGetMessagesInteractor = checkNotNull(getMessagesInteractor);
        mSendMessageInteractor = checkNotNull(sendMessageInteractor);
        mUpdateSeenStatusInteractor = checkNotNull(updateSeenStatusInteractor);
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
        if (!TextUtils.isEmpty(message)) {
            mSendMessageInteractor.execute(mRoomID, message, this);
        }
    }

    @Override
    public void bindView() {

    }

    @Override
    public void unbindView() {

    }

    @Override
    public void onRoomLoaded(Chat chat) {
        if (!RoomUtils.roomIsActive(chat, new Date()))
            mRoomView.disableChat();

        mRoomView.setRoom(mChatMapper.reverseMap(chat));
        setRoomSeen(chat);

        mRoom = chat;
        mGetMessagesInteractor.execute(mRoom, this);
    }

    private void setRoomSeen(final Chat chat) {
        mUpdateSeenStatusInteractor.execute(chat.getId(), true, this);
    }

    @Override
    public void onRoomLoadError() {
        mRoomView.showLoadError();
    }

    @Override
    public void onMessagesLoaded(List<Message> messages) {
        mRoomView.hideProgress();
        mRoomView.setMessages(mMessageMapper.reverseMap(messages));
    }

    @Override
    public void onMessagesLoadError() {
        mRoomView.showLoadError();
    }

    @Override
    public void onMessageSent(Message message) {
        mRoomView.handleMessageSendSuccess();
        mRoomView.addMessage(mMessageMapper.reverseMap(message));
    }

    @Override
    public void onMessageSendError() {
        mRoomView.handleMessageSendError();
    }

    @Override
    public void onSeenStatusUpdated() {

    }

    @Override
    public void onSeenStatusUpdateError() {

    }
}
