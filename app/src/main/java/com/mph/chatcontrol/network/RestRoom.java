package com.mph.chatcontrol.network;
/* Created by macmini on 31/08/2017. */

import com.google.gson.annotations.SerializedName;
import com.mph.chatcontrol.data.Chat;
import com.mph.chatcontrol.utils.DateUtils;

public class RestRoom {

    @SerializedName("id")
    private String id;

    @SerializedName("guest_name")
    private String guestName;

    @SerializedName("date_start")
    private String startDate;

    @SerializedName("date_end")
    private String endDate;

    @SerializedName("date_last_msg")
    private String lastMsgDate;

    public RestRoom(Chat chat) {
        id = chat.getId();
        guestName = chat.getGuestName();
        startDate = chat.getStartDate().toString();
        endDate = chat.getEndDate().toString();
        lastMsgDate = chat.getLastMsgDate().toString();
    }

    public String getId() {
        return id;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getLastMsgDate() {
        return lastMsgDate;
    }

    public Chat toChat() {
        Chat chat = new Chat();
        chat.setId(getId());
        chat.setGuestName(getGuestName());
        chat.setStartDate(DateUtils.stringToDateISO8601(getStartDate()));
        chat.setEndDate(DateUtils.stringToDateISO8601(getEndDate()));
        chat.setLastMsgDate(DateUtils.stringToDateISO8601(getLastMsgDate()));

        return chat;
    }
}
