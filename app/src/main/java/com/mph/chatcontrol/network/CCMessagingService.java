package com.mph.chatcontrol.network;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mph.chatcontrol.ChatcontrolApplication;
import com.mph.chatcontrol.device.notification.NotificationFactory;
import com.mph.chatcontrol.device.notification.Notifications;
import com.mph.chatcontrol.device.screen.ScreenSupervisor;
import com.mph.chatcontrol.utils.IntentFactory;

import java.util.Map;

/* Created by macmini on 05/12/2017. */

public class CCMessagingService extends FirebaseMessagingService {

    @SuppressWarnings("unused")
    private static final String TAG = CCMessagingService.class.getSimpleName();

    @NonNull
    private Notifications mNotifications;

    @NonNull
    private NotificationFactory mNotificationFactory;

    @NonNull
    private ScreenSupervisor mScreenSupervisor;

    @NonNull
    private IntentFactory mIntentFactory;

    @NonNull
    private Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        getDependencies();
    }

    private void getDependencies() {
        mNotifications = ((ChatcontrolApplication) getApplication()).getNotifications();
        mNotificationFactory = ((ChatcontrolApplication) getApplication()).getNotificationFactory();
        mScreenSupervisor = ((ChatcontrolApplication) getApplication()).getScreenSupervisor();
        mIntentFactory = ((ChatcontrolApplication) getApplication()).getIntentFactory();
        mContext = ((ChatcontrolApplication) getApplication()).getContext();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "onMessageReceived: fired");
//        String contentText = null;
//        if (remoteMessage.getNotification() != null) {
//            Log.d(TAG, "onMessageReceived: Title: " + remoteMessage.getNotification().getTitle());
//            Log.d(TAG, "FCM Notification Body: " + remoteMessage.getNotification().getBody());
//            contentText = remoteMessage.getNotification().getBody();
//        }

        Map<String, String> data = remoteMessage.getData();
        String roomID = null;
        String propertyName = null;
        String senderName = null;
        String text = null;
        if (data != null) {
            Log.d(TAG, "onMessageReceived: Data: " + data);
            roomID = data.get("room_id");
            propertyName = data.get("property_name");
            senderName = data.get("sender_name");
            text = data.get("text");
        }

        if (roomID != null && canShowNotification(roomID)) {
            mNotifications.showNotification(
                    roomID,
                    mNotificationFactory.getNewMessageNotificationID(),
                    mNotificationFactory.createNewMessageNotification(
                            propertyName,
                            senderName,
                            text,
                            buildPendingIntent(mIntentFactory.getRoomIntent(roomID))));
        }
    }

    private boolean canShowNotification(String roomID) {
        return !mScreenSupervisor.isRoomOnDisplay(roomID);
    }

    private PendingIntent buildPendingIntent(Intent targetActivityIntent) {
        return PendingIntent.getActivity(mContext, 0, targetActivityIntent,
                PendingIntent.FLAG_ONE_SHOT);
    }
}
