package com.mph.chatcontrol.network;
/* Created by macmini on 31/08/2017. */

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;
import com.google.gson.annotations.SerializedName;
import com.mph.chatcontrol.data.Chat;
import com.mph.chatcontrol.utils.DateUtils;

@SuppressWarnings("unused")
@IgnoreExtraProperties
public class RestRoom {

    @SerializedName("id")
    @PropertyName("id")
    public String id;

    @SerializedName("guest_name")
    @PropertyName("guest_name")
    public String guestName;

    @SerializedName("date_start")
    @PropertyName("date_start")
    public String startDate;

    @SerializedName("date_end")
    @PropertyName("date_end")
    public String endDate;

    @SerializedName("date_last_msg")
    @PropertyName("date_last_msg")
    public String lastMsgDate;

    @SerializedName("message_count")
    @PropertyName("message_count")
    public int messageCount;

    @SerializedName("property_name")
    @PropertyName("property_name")
    public String propertyName;

    @SerializedName("last_msg_str")
    @PropertyName("last_msg_str")
    public String lastMsgStr;

    @SerializedName("guest_id")
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
        messageCount = chat.getMessageCount();
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
        chat.setMessageCount(getMessageCount());
        chat.setPropertyName(getPropertyName());
        chat.setLastMsg(getLastMsgStr());
        chat.setGuestID(getGuestID());
        return chat;
    }
}
