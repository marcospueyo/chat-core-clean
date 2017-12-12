package com.mph.chatcontrol.chatlist;
/* Created by macmini on 17/07/2017. */

import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;

import com.mph.chatcontrol.base.BaseViewModel;
import com.mph.chatcontrol.chatlist.contract.ChatListPresenter;
import com.mph.chatcontrol.chatlist.contract.ChatListView;
import com.mph.chatcontrol.chatlist.contract.FindChatsInteractor;
import com.mph.chatcontrol.chatlist.viewmodel.ChatViewModel;
import com.mph.chatcontrol.chatlist.viewmodel.mapper.ChatViewModelToChatMapper;
import com.mph.chatcontrol.data.Chat;
import com.mph.chatcontrol.data.ChatInfo;
import com.mph.chatcontrol.events.RefreshRoomsEvent;
import com.mph.chatcontrol.events.SearchRoomsEvent;
import com.mph.chatcontrol.network.ChatComparator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static com.google.common.base.Preconditions.checkNotNull;

public class ChatListPresenterImpl implements ChatListPresenter,
        FindChatsInteractor.OnFinishedListener {

    @SuppressWarnings("unused")
    private static final String TAG = ChatListPresenterImpl.class.getSimpleName();

    @NonNull
    private final ChatListView mChatListView;

    @NonNull
    private final FindChatsInteractor mFindChatsInteractor;

    @NonNull
    private final ChatViewModelToChatMapper mMapper;

    @NonNull
    private final ChatComparator mChatComparator;

    private final boolean mShouldShowActiveChats;

    @NonNull
    private final EventBus mEventBus;

    private Map<String, Pair<Chat, ChatInfo>> mRoomMap;

    public ChatListPresenterImpl(@NonNull ChatListView chatListView,
                                 @NonNull ChatViewModelToChatMapper mapper,
                                 @NonNull ChatComparator chatComparator,
                                 @NonNull FindChatsInteractor findChatsInteractor,
                                 boolean shouldShowActiveChats,
                                 @NonNull EventBus eventBus) {
        mChatListView = checkNotNull(chatListView);
        mFindChatsInteractor = checkNotNull(findChatsInteractor);
        mMapper = checkNotNull(mapper);
        mChatComparator = checkNotNull(chatComparator);
        mChatListView.setPresenter(this);
        mShouldShowActiveChats = shouldShowActiveChats;
        mEventBus = checkNotNull(eventBus);
        mRoomMap = new HashMap<>();
    }

    @Override
    public void start() {
        mEventBus.register(this);
        loadRooms();
    }

    private void loadRooms() {
        mChatListView.showProgress();
        Date currentDate = new Date();
        if (mShouldShowActiveChats) {
            mFindChatsInteractor.findActiveChats(currentDate, this);
        }

        else {
            mFindChatsInteractor.findArchivedChats(currentDate, this);
        }
    }

    @Override
    public void stop() {
        mEventBus.unregister(this);
        mFindChatsInteractor.stopUpdates();
    }

    @Override
    public void onItemClicked(BaseViewModel chat) {
        mChatListView.openChat((ChatViewModel) chat);
    }

    @Override
    public void onFinished(List<Pair<Chat, ChatInfo>> chats) {
        saveFetchResults(chats);
        List<ChatViewModel> chatViewModels = mMapper.reverseMap(chats);
        mChatListView.setItems(chatViewModels);
        mChatListView.hideProgress();
    }

    private void saveFetchResults(List<Pair<Chat, ChatInfo>> chats) {
        mRoomMap.clear();
        for (Pair<Chat, ChatInfo> item : chats) {
            mRoomMap.put(item.first.getId(), item);
        }
    }

    @Override
    public void onChatChanged(Pair<Chat, ChatInfo> item) {
        Log.d(TAG, "onChatChanged: fired");
        mRoomMap.put(item.first.getId(), item);
        List<Pair<Chat, ChatInfo>> list = new ArrayList<>(mRoomMap.values());
        Collections.sort(list, mChatComparator);
        mChatListView.setItems(mMapper.reverseMap(list));
    }

    @Override
    public void onChatChangedError() {
        mChatListView.showUpdateError();
    }

    @Override
    public void onDataNotAvailable() {
        mChatListView.hideProgress();
        mChatListView.showLoadError();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshRoomsEvent(RefreshRoomsEvent event) {
        loadRooms();
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onSearchRoomsEvent(SearchRoomsEvent event) {
        Log.d(TAG, "onSearchRoomsEvent: ");
    }
}
