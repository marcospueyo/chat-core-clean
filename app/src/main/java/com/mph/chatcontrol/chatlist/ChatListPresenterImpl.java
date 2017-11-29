package com.mph.chatcontrol.chatlist;
/* Created by macmini on 17/07/2017. */

import android.support.annotation.NonNull;
import android.util.Pair;

import com.mph.chatcontrol.base.BaseViewModel;
import com.mph.chatcontrol.chatlist.contract.ChatListPresenter;
import com.mph.chatcontrol.chatlist.contract.ChatListView;
import com.mph.chatcontrol.chatlist.contract.FindChatsInteractor;
import com.mph.chatcontrol.chatlist.viewmodel.ChatViewModel;
import com.mph.chatcontrol.chatlist.viewmodel.mapper.ChatViewModelToChatMapper;
import com.mph.chatcontrol.data.Chat;
import com.mph.chatcontrol.data.ChatInfo;

import java.util.Date;
import java.util.List;


import static com.google.common.base.Preconditions.checkNotNull;

public class ChatListPresenterImpl implements ChatListPresenter,
        FindChatsInteractor.OnFinishedListener {

    @SuppressWarnings("unused")
    private static final String TAG = ChatListPresenterImpl.class.getSimpleName();

    @NonNull
    private final ChatListView mChatListView;

    // TODO: 29/11/2017 Create interactor to follow live updates from the rooms on display
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
        Date currentDate = new Date();
        if (mShouldShowActiveChats)
            mFindChatsInteractor.findActiveChats(currentDate, this);
        else
            mFindChatsInteractor.findArchivedChats(currentDate, this);
    }

    @Override
    public void stop() {

    }

    @Override
    public void onItemClicked(BaseViewModel chat) {
        mChatListView.openChat((ChatViewModel) chat);
    }

    @Override
    public void onFinished(List<Pair<Chat, ChatInfo>> chats) {
        List<ChatViewModel> chatViewModels = mMapper.reverseMap(chats);
        mChatListView.setItems(chatViewModels);
        mChatListView.hideProgress();
    }

//    private void processChats(List<Pair<Chat, ChatInfo>> chats) {
//        for (final Pair<Chat, ChatInfo> pair : chats) {
//            final Chat chat = pair.first;
//            ChatInfo info = pair.second;
            //chat.setPendingCount();
//            mGetLastMessageInteractor.execute(chat.getId(), new GetLastMessageInteractor.OnFinishedListener() {
//                @Override
//                public void onLastMessageFetched(Message message) {
//                    chat.setLastMsgDate(message == null ? null : message.getDate());
//                    chat.setLastMsg(message == null ? "" : message.getText());
//                }
//
//                @Override
//                public void onLastActivityLoadError() {
//
//                }
//            });
//        }
//    }

    @Override
    public void onDataNotAvailable() {
        mChatListView.hideProgress();
        mChatListView.showLoadError();
    }
}
