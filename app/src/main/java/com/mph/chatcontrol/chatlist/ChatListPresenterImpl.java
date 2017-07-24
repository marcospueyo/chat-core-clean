package com.mph.chatcontrol.chatlist;
/* Created by macmini on 17/07/2017. */

import android.support.annotation.NonNull;
import android.util.Log;

import com.mph.chatcontrol.base.BaseViewModel;
import com.mph.chatcontrol.chatlist.contract.ChatListPresenter;
import com.mph.chatcontrol.chatlist.contract.ChatListView;
import com.mph.chatcontrol.chatlist.contract.FindChatsInteractor;
import com.mph.chatcontrol.chatlist.viewmodel.ChatViewModel;
import com.mph.chatcontrol.chatlist.viewmodel.mapper.ChatViewModelToChatMapper;
import com.mph.chatcontrol.data.Chat;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class ChatListPresenterImpl implements ChatListPresenter,
        FindChatsInteractor.OnFinishedListener {
    private static final String TAG = ChatListPresenterImpl.class.getSimpleName();

    @NonNull
    private final ChatListView mChatListView;

    @NonNull
    private final FindChatsInteractor mFindChatsInteractor;

    @NonNull
    private ChatViewModelToChatMapper mMapper;

    private final boolean mShouldShowActiveChats;

    public ChatListPresenterImpl(@NonNull ChatListView chatListView,
                                 @NonNull ChatViewModelToChatMapper mapper,
                                 @NonNull FindChatsInteractor findChatsInteractor,
                                 boolean shouldShowActiveChats) {
        mChatListView = checkNotNull(chatListView);
        mFindChatsInteractor = checkNotNull(findChatsInteractor);
        mMapper = checkNotNull(mapper);
        mChatListView.setPresenter(this);
        mShouldShowActiveChats = shouldShowActiveChats;
    }

    @Override
    public void start() {
        mChatListView.showProgress();
        if (mShouldShowActiveChats)
            mFindChatsInteractor.findActiveChats(this);
        else
            mFindChatsInteractor.findArchivedChats(this);
    }

    @Override
    public void onItemClicked(BaseViewModel chat) {
        Log.d(TAG, "onItemClicked: " + chat.toString());
        mChatListView.openChat((ChatViewModel) chat);
    }

    @Override
    public void onFinished(List<Chat> chats) {
        List<ChatViewModel> chatViewModels = mMapper.reverseMap(chats);
        mChatListView.setItems(chatViewModels);
        mChatListView.hideProgress();
    }

    @Override
    public void onDataNotAvailable() {
        mChatListView.hideProgress();
        mChatListView.showLoadError();
    }
}
