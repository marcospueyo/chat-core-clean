package com.mph.chatcontrol.room;

import android.support.annotation.NonNull;

import com.mph.chatcontrol.data.Chat;
import com.mph.chatcontrol.data.ChatsRepository;
import com.mph.chatcontrol.room.contract.UpdateSeenStatusInteractor;

import static com.google.common.base.Preconditions.checkNotNull;

/* Created by macmini on 03/08/2017. */

public class UpdateSeenStatusInteractorImpl implements UpdateSeenStatusInteractor {

    @NonNull
    private final ChatsRepository mChatsRepository;

    public UpdateSeenStatusInteractorImpl(@NonNull ChatsRepository mChatsRepository) {
        this.mChatsRepository = checkNotNull(mChatsRepository);
    }

    @Override
    public void execute(String roomID, final boolean seen, final OnFinishedListener listener) {
        mChatsRepository.getChat(roomID, new ChatsRepository.GetSingleChatCallback() {
            @Override
            public void onSingleChatLoaded(Chat chat) {
                if (chat == null)
                    listener.onSeenStatusUpdateError();
                else {
                    chat.setPendingCount(seen ? 0 : 1);
                    mChatsRepository.updateChat(chat);
                    listener.onSeenStatusUpdated();
                }
            }

            @Override
            public void onChatNotAvailable() {
                listener.onSeenStatusUpdateError();
            }
        });
    }
}
