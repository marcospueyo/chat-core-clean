package com.mph.chatcontrol.room;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mph.chatcontrol.data.Chat;
import com.mph.chatcontrol.data.ChatInfo;
import com.mph.chatcontrol.data.ChatInfoRepository;
import com.mph.chatcontrol.data.ChatsRepository;
import com.mph.chatcontrol.device.notification.NotificationFactory;
import com.mph.chatcontrol.device.notification.Notifications;
import com.mph.chatcontrol.room.contract.GetRoomInteractor;
import com.mph.chatcontrol.room.contract.UpdateSeenStatusInteractor;

import static com.google.common.base.Preconditions.checkNotNull;

/* Created by macmini on 03/08/2017. */

public class UpdateSeenStatusInteractorImpl implements UpdateSeenStatusInteractor {

    @SuppressWarnings("unused")
    private static final String TAG = UpdateSeenStatusInteractorImpl.class.getSimpleName();

    @NonNull
    private final ChatsRepository mChatsRepository;

    @NonNull
    private final ChatInfoRepository mChatInfoRepository;


    // TODO: 11/12/2017 Create an interactor to manage Notifications component
    @NonNull
    private final Notifications mNotifications;

    @NonNull
    private final NotificationFactory mNotificationFactory;

    public UpdateSeenStatusInteractorImpl(@NonNull ChatsRepository chatsRepository,
                                          @NonNull ChatInfoRepository chatInfoRepository,
                                          @NonNull Notifications notifications,
                                          @NonNull NotificationFactory notificationFactory) {
        mChatsRepository = checkNotNull(chatsRepository);
        mChatInfoRepository = checkNotNull(chatInfoRepository);
        mNotifications = checkNotNull(notifications);
        mNotificationFactory = checkNotNull(notificationFactory);
    }

    @Override
    public void execute(String roomID, final boolean seen, final OnFinishedListener listener) {
        mNotifications.hideNotification(roomID, mNotificationFactory.getNewMessageNotificationID());
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
                            Log.d(TAG, "onChatInfoLoaded: Room msg count=" + chat.getMessageCount() + " ChatInfo read count = " + chatInfo.getReadCount());
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
