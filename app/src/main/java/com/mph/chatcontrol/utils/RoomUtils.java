package com.mph.chatcontrol.utils;
/* Created by macmini on 04/09/2017. */

import com.mph.chatcontrol.data.Chat;

import java.util.Date;

public class RoomUtils {

    public static boolean roomIsActive(Chat chat, Date inputDate) {
        return DateUtils.dateIsInsideInterval(inputDate, chat.getStartDate(), chat.getEndDate());
    }
}
