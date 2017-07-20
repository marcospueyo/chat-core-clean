package com.mph.chatcontrol.guestlist.viewmodel;

import com.google.auto.value.AutoValue;
import com.mph.chatcontrol.base.BaseViewModel;

/* Created by macmini on 19/07/2017. */

@AutoValue
public abstract class GuestViewModel extends BaseViewModel {

    public abstract String id();
    public abstract String name();
    public abstract String initial();
    public abstract String phone();
    public abstract String email();
    public abstract String startDate();
    public abstract String endDate();

    public static GuestViewModel create(String id, String name, String initial, String phone,
                                        String email, String startDate, String endDate) {
        return builder()
                .setId(id)
                .setName(name)
                .setInitial(initial)
                .setPhone(phone)
                .setEmail(email)
                .setStartDate(startDate)
                .setEndDate(endDate)
                .build();
    }

    public static Builder builder() {
        return new AutoValue_GuestViewModel.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setId(String value);
        public abstract Builder setName(String value);
        public abstract Builder setInitial(String value);
        public abstract Builder setPhone(String value);
        public abstract Builder setEmail(String value);
        public abstract Builder setStartDate(String value);
        public abstract Builder setEndDate(String value);

        public abstract GuestViewModel build();
    }
}
