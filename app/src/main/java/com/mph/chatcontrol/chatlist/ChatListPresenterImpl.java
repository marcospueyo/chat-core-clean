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
import com.mph.chatcontrol.data.Message;

import java.util.Date;
import java.util.List;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

public class ChatListPresenterImpl implements ChatListPresenter,
        FindChatsInteractor.OnFinishedListener {
    private static final String TAG = ChatListPresenterImpl.class.getSimpleName();

    @NonNull
    private final ChatListView mChatListView;

    @NonNull
    private final FindChatsInteractor mFindChatsInteractor;

    @Nonnull private final GetLastMessageInteractor mGetLastMessageInteractor;

    @NonNull
    private ChatViewModelToChatMapper mMapper;

    private final boolean mShouldShowActiveChats;

    public ChatListPresenterImpl(@NonNull ChatListView chatListView,
                                 @NonNull ChatViewModelToChatMapper mapper,
                                 @NonNull FindChatsInteractor findChatsInteractor,
                                 @Nonnull GetLastMessageInteractor getLastMessageInteractor,
                                 boolean shouldShowActiveChats) {
        mChatListView = checkNotNull(chatListView);
        mFindChatsInteractor = checkNotNull(findChatsInteractor);
        mGetLastMessageInteractor = checkNotNull(getLastMessageInteractor);
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
    public void onItemClicked(BaseViewModel chat) {
        Log.d(TAG, "onItemClicked: " + chat.toString());
        mChatListView.openChat((ChatViewModel) chat);
    }

    @Override
    public void onFinished(List<Chat> chats) {
        processChats(chats);
        List<ChatViewModel> chatViewModels = mMapper.reverseMap(chats);
        mChatListView.setItems(chatViewModels);
        mChatListView.hideProgress();
    }

    // TODO: 04/09/2017 Must be removed
    private void processChats(List<Chat> chats) {
        for (final Chat chat : chats) {
            chat.setPendingCount(0);
            mGetLastMessageInteractor.execute(chat.getId(), new GetLastMessageInteractor.OnFinishedListener() {
                @Override
                public void onLastMessageFetched(Message message) {
                    chat.setLastMsgDate(message == null ? null : message.getDate());
                    chat.setLastMsg(message == null ? "" : message.getText());
                }

                @Override
                public void onLastActivityLoadError() {

                }
            });
        }
    }

    @Override
    public void onDataNotAvailable() {
        mChatListView.hideProgress();
        mChatListView.showLoadError();
    }
}
