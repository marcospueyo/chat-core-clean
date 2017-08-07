package com.mph.chatcontrol.guestlist.viewmodel.mapper;
/* Created by macmini on 20/07/2017. */

import com.mph.chatcontrol.data.Guest;
import com.mph.chatcontrol.data.Mapper;
import com.mph.chatcontrol.guestlist.viewmodel.GuestViewModel;
import com.mph.chatcontrol.utils.DateUtils;

public class GuestViewModelToGuestMapper extends Mapper<GuestViewModel, Guest> {

    private static final String DATE_FORMAT = "dd/MM/yyyy";

    @Override
    public Guest map(GuestViewModel value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public GuestViewModel reverseMap(Guest value) {
        GuestViewModel guestViewModel;
        guestViewModel = GuestViewModel.builder()
                .setId(value.getId())
                .setName(value.getName())
                .setInitial(value.getName().substring(0, 1))
                .setPhone(value.getPhone())
                .setEmail(value.getEmail())
                .setStartDate(DateUtils.dateToString(value.getStartDate(), DATE_FORMAT))
                .setEndDate(DateUtils.dateToString(value.getEndDate(), DATE_FORMAT))
                .setRelatedRoomId(value.getRelatedRoomID())
                .build();
        return guestViewModel;
    }
}
