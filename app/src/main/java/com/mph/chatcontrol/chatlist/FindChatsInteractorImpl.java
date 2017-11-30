package com.mph.chatcontrol.chatlist;
/* Created by macmini on 17/07/2017. */

import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;

import com.mph.chatcontrol.chatlist.contract.FindChatsInteractor;
import com.mph.chatcontrol.data.Chat;
import com.mph.chatcontrol.data.ChatInfo;
import com.mph.chatcontrol.data.ChatInfoRepository;
import com.mph.chatcontrol.data.ChatsRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public class FindChatsInteractorImpl implements FindChatsInteractor {

    @SuppressWarnings("unused")
    private static final String TAG = FindChatsInteractorImpl.class.getSimpleName();

    @NonNull
    private final ChatsRepository mChatsRepository;

    @NonNull
    private final ChatInfoRepository mChatInfoRepository;


    public FindChatsInteractorImpl(@NonNull ChatsRepository mChatsRepository,
                                   @NonNull ChatInfoRepository chatInfoRepository) {
        this.mChatsRepository = checkNotNull(mChatsRepository);
        mChatInfoRepository = checkNotNull(chatInfoRepository);
    }

    @Override
    public void findActiveChats(Date inputDate, final OnFinishedListener listener) {
        mChatsRepository.findActiveChats(inputDate, new ChatsRepository.GetChatsCallback() {
            @Override
            public void onChatsLoaded(List<Chat> chats) {
                handleChatList(chats, listener);
            }

            @Override
            public void onChatChanged(Chat chat) {
                Log.d(TAG, "onChatChanged: fired");
                handleChatChanged(chat, listener);
            }

            @Override
            public void onChatUpdateError() {
                listener.onChatChangedError();
            }

            @Override
            public void onChatsNotAvailable() {
                handleError(listener);
            }
        });
    }

    @Override
    public void findArchivedChats(Date inputDate, final OnFinishedListener listener) {
        mChatsRepository.findArchivedChats(inputDate, new ChatsRepository.GetChatsCallback() {
            @Override
            public void onChatsLoaded(List<Chat> chats) {
                handleChatList(chats, listener);
            }

            @Override
            public void onChatChanged(Chat chat) {

            }

            @Override
            public void onChatUpdateError() {

            }

            @Override
            public void onChatsNotAvailable() {
                handleError(listener);
            }
        });
    }

    @Override
    public void stopUpdates() {
        mChatsRepository.stopListeningAllRooms();
    }

    private void handleChatList(final List<Chat> chatList, final OnFinishedListener listener) {
        final Map<String, Chat> chatMap = mapChats(chatList);
        mChatInfoRepository.getChatInfoMap(chatMap.keySet(),
                new ChatInfoRepository.GetChatInfoCallback() {
            @Override
            public void onChatInfoMapLoaded(Map<String, ChatInfo> chatInfoMap) {
                List<Pair<Chat, ChatInfo>> pairList = new ArrayList<>();
                for (Map.Entry<String, ChatInfo> entry : chatInfoMap.entrySet()) {
                    Pair<Chat, ChatInfo> pair =
                            new Pair<>(chatMap.get(entry.getKey()), entry.getValue());
                    pairList.add(pair);
                }
                listener.onFinished(pairList);
            }

            @Override
            public void onChatInfoMapLoadError() {
                handleError(listener);
            }
        });
    }

    private void handleChatChanged(final Chat chat, final OnFinishedListener listener) {
        mChatInfoRepository.getSingleChatInfo(chat.getId(), new ChatInfoRepository.GetSingleChatInfoCallback() {
            @Override
            public void onChatInfoLoaded(ChatInfo chatInfo) {
                listener.onChatChanged(new Pair<>(chat, chatInfo));
            }

            @Override
            public void onChatInfoLoadError() {

            }
        });
    }

    private void handleError(final OnFinishedListener listener) {
        listener.onDataNotAvailable();
    }

    private Map<String, Chat> mapChats(final List<Chat> chatList) {
        Map<String, Chat> map = new HashMap<>();
        for (Chat chat : chatList) {
            map.put(chat.getId(), chat);
        }
        return map;
    }
}
