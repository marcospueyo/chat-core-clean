package com.mph.chatcontrol.room.viewmodel;

import com.google.auto.value.AutoValue;
import com.mph.chatcontrol.base.BaseViewModel;

/* Created by macmini on 24/07/2017. */

@AutoValue
public abstract class MessageViewModel extends BaseViewModel {

    public abstract String id();
    public abstract String text();
    public abstract String timestamp();
    public abstract boolean isOwnMessage();
    public abstract String senderName();

    public static MessageViewModel create(String id, String text, String timestamp,
                                          boolean isOwnMessage, String senderName) {
        return builder()
                .setId(id)
                .setText(text)
                .setTimestamp(timestamp)
                .setIsOwnMessage(isOwnMessage)
                .setSenderName(senderName)
                .build();
    }

    public static Builder builder() {
        return new AutoValue_MessageViewModel.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setId(String value);
        public abstract Builder setText(String value);
        public abstract Builder setTimestamp(String value);
        public abstract Builder setIsOwnMessage(boolean value);
        public abstract Builder setSenderName(String value);

        public abstract MessageViewModel build();
    }
}
