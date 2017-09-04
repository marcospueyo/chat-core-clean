package com.mph.chatcontrol.network;
/* Created by macmini on 31/08/2017. */

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;
import com.mph.chatcontrol.data.Chat;
import com.mph.chatcontrol.utils.DateUtils;

@SuppressWarnings("unused")
@IgnoreExtraProperties
public class RestRoom {

    @PropertyName("id")
    public String id;

    @PropertyName("guest_name")
    public String guestName;

    @PropertyName("date_start")
    public String startDate;

    @PropertyName("date_end")
    public String endDate;

    @PropertyName("date_last_msg")
    public String lastMsgDate;

    @PropertyName("message_count")
    public int messageCount;

    @PropertyName("property_name")
    public String propertyName;

    @PropertyName("last_msg_str")
    public String lastMsgStr;

    @PropertyName("guest_id")
    public String guestID;

    public RestRoom() {
    }

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

    public int getMessageCount() {
        return messageCount;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getLastMsgStr() {
        return lastMsgStr;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setLastMsgDate(String lastMsgDate) {
        this.lastMsgDate = lastMsgDate;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getGuestID() {
        return guestID;
    }

    public void setGuestID(String guestID) {
        this.guestID = guestID;
    }

    public void setLastMsgStr(String lastMsgStr) {
        this.lastMsgStr = lastMsgStr;
    }

    public Chat toChat() {
        Chat chat = new Chat();
        chat.setId(getId());
        chat.setGuestName(getGuestName());
        chat.setStartDate(DateUtils.stringToDateISO8601(getStartDate()));
        chat.setEndDate(DateUtils.stringToDateISO8601(getEndDate()));
        chat.setLastMsgDate(DateUtils.stringToDateISO8601(getLastMsgDate()));
        chat.setPropertyName(getPropertyName());
        chat.setLastMsg(getLastMsgStr());
        chat.setGuestID(getGuestID());
        return chat;
    }
}
