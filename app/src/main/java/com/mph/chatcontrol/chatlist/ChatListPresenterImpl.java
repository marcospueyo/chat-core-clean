package com.mph.chatcontrol.chatlist;
/* Created by macmini on 17/07/2017. */

import android.support.annotation.NonNull;
import android.text.TextUtils;
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
import com.mph.chatcontrol.events.SearchRoomsDisableEvent;
import com.mph.chatcontrol.events.SearchRoomsEvent;
import com.mph.chatcontrol.network.ChatComparator;
import com.mph.chatcontrol.utils.StringComparator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
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
    private final StringComparator mStringComparator;

    @NonNull
    private final ChatComparator mChatComparator;

    private final boolean mShouldShowActiveChats;

    @NonNull
    private final EventBus mEventBus;

    private Map<String, Pair<Chat, ChatInfo>> mRoomMap;

    private boolean mFilterEnabled;

    private String mQueryFilter;

    public ChatListPresenterImpl(@NonNull ChatListView chatListView,
                                 @NonNull ChatViewModelToChatMapper mapper,
                                 @NonNull ChatComparator chatComparator,
                                 @NonNull FindChatsInteractor findChatsInteractor,
                                 boolean shouldShowActiveChats,
                                 @NonNull EventBus eventBus,
                                 @NonNull StringComparator stringComparator) {
        mChatListView = checkNotNull(chatListView);
        mFindChatsInteractor = checkNotNull(findChatsInteractor);
        mMapper = checkNotNull(mapper);
        mChatComparator = checkNotNull(chatComparator);
        mChatListView.setPresenter(this);
        mShouldShowActiveChats = shouldShowActiveChats;
        mEventBus = checkNotNull(eventBus);
        mStringComparator = checkNotNull(stringComparator);
        mRoomMap = new HashMap<>();
        mFilterEnabled = false;
    }

    @Override
    public void start() {
        mEventBus.register(this);
        loadRooms();
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
        showRooms();
        mChatListView.hideProgress();
    }

    @Override
    public void onChatChanged(Pair<Chat, ChatInfo> item) {
        mRoomMap.put(item.first.getId(), item);
        showRooms();
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSearchRoomsEvent(SearchRoomsEvent event) {
        enableFilter(event.query());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSearchRoomsDisableEvent(SearchRoomsDisableEvent event) {
        disableFilter();
    }


    private void loadRooms() {
        if (cacheContainsRooms()) {
            showRooms();
        }
        else {
            mChatListView.showProgress();
        }
        remoteFetchRooms();
    }

    private boolean cacheContainsRooms() {
        return mRoomMap.size() > 0;
    }

    private void remoteFetchRooms() {
        Date currentDate = new Date();
        if (mShouldShowActiveChats) {
            mFindChatsInteractor.findActiveChats(currentDate, this);
        }

        else {
            mFindChatsInteractor.findArchivedChats(currentDate, this);
        }
    }

    private void showRooms() {
        if (mustFilter()) {
            showFilteredRooms(mQueryFilter);
        }
        else {
            showRoomList(new ArrayList<>(mRoomMap.values()));
        }

    }

    private boolean mustFilter() {
        return mFilterEnabled && !TextUtils.isEmpty(mQueryFilter);
    }

    private void showRoomList(List<Pair<Chat, ChatInfo>> list) {
        Collections.sort(list, mChatComparator);
        mChatListView.setItems(mMapper.reverseMap(list));
    }

    private void enableFilter(String queryFilter) {
        mFilterEnabled = true;
        mQueryFilter = queryFilter;
        showRooms();
    }

    private void disableFilter() {
        mFilterEnabled = false;
        mQueryFilter = null;
        showRooms();
    }

    private void showFilteredRooms(String queryFilter) {
        List<Pair<Chat, ChatInfo>> list = new ArrayList<>();
        for (Map.Entry<String, Pair<Chat, ChatInfo>> entry : mRoomMap.entrySet()) {
            Pair<Chat, ChatInfo> roomPair = entry.getValue();
            if (searchConditionIsMet(roomPair.first, queryFilter)) {
                list.add(roomPair);
            }
        }
        showRoomList(list);
    }

    private boolean searchConditionIsMet(Chat chat, String queryFilter) {
        return mStringComparator.stringsAreAlike(chat.getPropertyName(), queryFilter)
                || mStringComparator.stringsAreAlike(chat.getGuestName(), queryFilter);
    }

    private void saveFetchResults(List<Pair<Chat, ChatInfo>> chats) {
        mRoomMap.clear();
        for (Pair<Chat, ChatInfo> item : chats) {
            mRoomMap.put(item.first.getId(), item);
        }
    }
}
