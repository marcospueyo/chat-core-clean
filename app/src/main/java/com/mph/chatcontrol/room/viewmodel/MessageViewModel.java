package com.mph.chatcontrol.room.viewmodel;

import com.google.auto.value.AutoValue;
import com.mph.chatcontrol.base.BaseViewModel;

/* Created by macmini on 24/07/2017. */

@AutoValue
public abstract class MessageViewModel extends BaseViewModel {

    public abstract String id();
    public abstract String text();
    public abstract String timestamp();

    public static MessageViewModel create(String id, String text, String timestamp) {
        return builder()
                .setId(id)
                .setText(text)
                .setTimestamp(timestamp)
                .build();
    }

    public static Builder builder() {
        return new AutoValue_MessageViewModel.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setId(String value);
        public abstract Builder setText(String text);
        public abstract Builder setTimestamp(String timestamp);

        public abstract MessageViewModel build();
    }
}
