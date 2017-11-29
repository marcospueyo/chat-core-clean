package com.mph.chatcontrol.room;

import android.support.annotation.NonNull;

import com.mph.chatcontrol.data.Chat;
import com.mph.chatcontrol.data.ChatInfo;
import com.mph.chatcontrol.data.ChatInfoRepository;
import com.mph.chatcontrol.data.ChatsRepository;
import com.mph.chatcontrol.room.contract.GetRoomInteractor;
import com.mph.chatcontrol.room.contract.UpdateSeenStatusInteractor;

import static com.google.common.base.Preconditions.checkNotNull;

/* Created by macmini on 03/08/2017. */

public class UpdateSeenStatusInteractorImpl implements UpdateSeenStatusInteractor {

    @NonNull
    private final ChatsRepository mChatsRepository;

    @NonNull
    private final ChatInfoRepository mChatInfoRepository;

    public UpdateSeenStatusInteractorImpl(@NonNull ChatsRepository mChatsRepository,
                                          @NonNull ChatInfoRepository chatInfoRepository) {
        this.mChatsRepository = checkNotNull(mChatsRepository);
        mChatInfoRepository = checkNotNull(chatInfoRepository);
    }

    @Override
    public void execute(String roomID, final boolean seen, final OnFinishedListener listener) {
        mChatsRepository.getChat(roomID, new ChatsRepository.GetSingleChatCallback() {
            @Override
            public void onSingleChatLoaded(final Chat chat) {
                if (chat == null)
                    handleError(listener);
                else {
                    mChatInfoRepository.getSingleChatInfo(chat.getId(),
                            new ChatInfoRepository.GetSingleChatInfoCallback() {
                        @Override
                        public void onChatInfoLoaded(ChatInfo chatInfo) {
                            chatInfo.setReadCount(chat.getMessageCount());
                            mChatInfoRepository.updateChatInfo(chatInfo);
                            listener.onSeenStatusUpdated();
                        }

                        @Override
                        public void onChatInfoLoadError() {
                            handleError(listener);
                        }
                    });
                }
            }

            @Override
            public void onChatNotAvailable() {
                handleError(listener);
            }
        });
    }

    private void handleError(final OnFinishedListener listener) {
        listener.onSeenStatusUpdateError();
    }
}
