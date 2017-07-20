package com.mph.chatcontrol.chatlist.viewmodel;
/* Created by macmini on 17/07/2017. */

import com.google.auto.value.AutoValue;
import com.mph.chatcontrol.base.BaseViewModel;

@AutoValue
public abstract class ChatViewModel extends BaseViewModel {

    public abstract String id();
    public abstract String title();
    public abstract String description();
    public abstract String initial();
    public abstract int pendingCount();
    public abstract String checkoutDate();
    public abstract String lastActivity();
    public abstract String lastMsgDate();
    public abstract boolean active();

    public static ChatViewModel create(String id, String title, String description, String initial,
                                            int pendingCount, String checkoutDate, String lastActivity,
                                            String lastMsgDate, boolean isActive) {
        return builder()
                .setId(id)
                .setTitle(title)
                .setDescription(description)
                .setInitial(initial)
                .setPendingCount(pendingCount)
                .setCheckoutDate(checkoutDate)
                .setLastActivity(lastActivity)
                .setLastMsgDate(lastMsgDate)
                .setActive(isActive)
                .build();
    }

    public static Builder builder() {
        return new AutoValue_ChatViewModel.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setId(String value);
        public abstract Builder setTitle(String value);
        public abstract Builder setDescription(String value);
        public abstract Builder setInitial(String value);
        public abstract Builder setPendingCount(int value);
        public abstract Builder setCheckoutDate(String value);
        public abstract Builder setLastActivity(String value);
        public abstract Builder setLastMsgDate(String value);
        public abstract Builder setActive(boolean value);

        public abstract ChatViewModel build();
    }
}
