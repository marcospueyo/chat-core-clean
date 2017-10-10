package com.mph.chatcontrol.network.message;
/* Created by macmini on 09/10/2017. */

import com.google.firebase.database.PropertyName;
import com.mph.chatcontrol.data.Message;
import com.mph.chatcontrol.utils.DateUtils;


public class RestMessage {

    @PropertyName("id")
    public String id;

    @PropertyName("text")
    public String text;

    @PropertyName("date")
    public String date;

    @PropertyName("room_id")
    public String roomID;

    @PropertyName("sender_name")
    public String senderName;

    @PropertyName("sender_id")
    public String senderID;

    public RestMessage() {
    }

    public RestMessage(Message message) {
        id = message.getId();
        text = message.getText();
        date = message.getDate().toString();
        roomID = message.getRoomId();
        // TODO: 09/10/2017 Handle roomID, senderName and senderID server-side
        senderName = message.getSenderName();
        senderID = message.getSenderId();
        roomID = message.getRoomId();
    }

    public Message toMessage() {
        Message message = new Message();
        message.setId(getId());
        message.setText(getText());
        message.setDate(DateUtils.stringToDateISO8601(getDate()));
        message.setRoomId(getRoomID());
        message.setSenderName(getSenderName());
        message.setSenderId(getSenderID());
        return message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }
}
