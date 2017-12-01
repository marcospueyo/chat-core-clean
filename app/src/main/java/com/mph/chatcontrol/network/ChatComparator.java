package com.mph.chatcontrol.network;
/* Created by macmini on 01/12/2017. */

import android.util.Pair;

import com.mph.chatcontrol.data.Chat;
import com.mph.chatcontrol.data.ChatInfo;

import java.util.Comparator;

public class ChatComparator implements Comparator<Pair<Chat, ChatInfo>> {

    public ChatComparator() {

    }

    @Override
    public int compare(Pair<Chat, ChatInfo> leftPair, Pair<Chat, ChatInfo> rightPair) {
        boolean leftComesFirst;
        Chat left = leftPair.first;
        Chat right = rightPair.first;
        if (left.getLastMsgDate().equals(right.getLastMsgDate())) {
            leftComesFirst = left.getId().compareTo(right.getId()) < 0;
        }
        else {
            leftComesFirst = left.getLastMsgDate().after(right.getLastMsgDate());
        }
        return leftComesFirst ? -1 : 1;
    }
}
