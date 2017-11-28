package com.mph.chatcontrol.data;

/*Created by Marcos on 28/11/2017.*/

import java.util.Map;
import java.util.Set;

public interface ChatInfoRepository {

    interface GetSingleChatInfoCallback {

        void onChatInfoLoaded(ChatInfo chatInfo);

        void onChatInfoLoadError();
    }

    interface GetChatInfoCallback {

        void onChatInfoMapLoaded(Map<String, ChatInfo> chatInfoMap);

        void onChatInfoMapLoadError();
    }

    void getSingleChatInfo(String roomID);

    void getChatInfoMap(Set<String> roomIDSet);
}
