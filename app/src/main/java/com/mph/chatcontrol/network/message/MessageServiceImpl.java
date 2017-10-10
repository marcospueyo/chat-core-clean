package com.mph.chatcontrol.network.message;
/* Created by macmini on 09/10/2017. */

import com.mph.chatcontrol.data.Message;

import java.util.List;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

public class MessageServiceImpl implements MessageService {

    private final MessageFirebaseService mFirebaseService;

    private final MessageRestService mRestService;

    private final RestMessageToMessageMapper mMapper;

    public MessageServiceImpl(@Nonnull MessageFirebaseService firebaseService,
                              @Nonnull MessageRestService restService,
                              @Nonnull RestMessageToMessageMapper mapper) {
        mFirebaseService = checkNotNull(firebaseService);
        mRestService = checkNotNull(restService);
        mMapper = checkNotNull(mapper);
    }

    @Override
    public void sendMessage(Message message, final SendMessageCallback callback) {
        mRestService.sendMessage(mMapper.reverseMap(message),
                new MessageRestService.SendMessageCallback() {
            @Override
            public void onMessageSent(RestMessage message) {
                callback.onMessageSent(message);
            }

            @Override
            public void onMessageSendError() {
                callback.onMessageSendError();
            }
        });
    }

    @Override
    public void getRoomMessages(String roomID, final GetMessagesCallback callback) {
        mFirebaseService.getRoomMessages(roomID, new MessageFirebaseService.GetMessagesCallback() {
            @Override
            public void onMessagesLoaded(List<RestMessage> messages) {
                callback.onMessagesLoaded(messages);
            }

            @Override
            public void onMessagesNotAvailable() {
                callback.onMessagesNotAvailable();
            }
        });
    }
}
