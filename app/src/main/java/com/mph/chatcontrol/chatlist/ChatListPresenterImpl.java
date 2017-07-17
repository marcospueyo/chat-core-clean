package com.mph.chatcontrol.chatlist;
/* Created by macmini on 17/07/2017. */

import android.support.annotation.NonNull;

import com.mph.chatcontrol.data.Chat;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class ChatListPresenterImpl implements ChatListPresenter,
        FindChatsInteractor.OnFinishedListener {

    // TODO: 17/07/2017 Implement ChatsDataSource
    //@NonNull
    //private final ChatsDataSource mChatsRepository;

    @NonNull
    private final ChatListView mChatListView;

    @NonNull
    private final FindChatsInteractor mFindChatsInteractor;

    public ChatListPresenterImpl(@NonNull ChatListView chatListView,
                                 @NonNull FindChatsInteractor findChatsInteractor) {
        mChatListView = checkNotNull(chatListView);
        mFindChatsInteractor = checkNotNull(findChatsInteractor);

        mChatListView.setPresenter(this);
    }

    @Override
    public void start() {
        mChatListView.showProgress();
        mFindChatsInteractor.findChats(this);
    }

    @Override
    public void onItemClicked(int position) {

    }

    @Override
    public void onFinished(List<Chat> chats) {
        mChatListView.setChats(chats);
        mChatListView.hideProgress();
    }

    @Override
    public void onDataNotAvailable() {
        mChatListView.hideProgress();
        mChatListView.showLoadError();
    }
}
