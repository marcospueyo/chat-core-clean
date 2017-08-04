package com.mph.chatcontrol.data;
/* Created by Marcos on 04/08/2017.*/

import java.util.List;

public interface MessagesRepository {

    List<Message> getRoomMessages(String roomID);

    List<Message> getRoomMessages(Chat room);

    Message getMessage(String messageID);

    Message insertOwnMessage(String roomID, String text);
}
