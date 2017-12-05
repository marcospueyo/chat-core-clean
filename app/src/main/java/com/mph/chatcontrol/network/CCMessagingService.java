package com.mph.chatcontrol.network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mph.chatcontrol.ChatcontrolApplication;
import com.mph.chatcontrol.device.notification.NotificationFactory;
import com.mph.chatcontrol.device.notification.Notifications;

/* Created by macmini on 05/12/2017. */

public class CCMessagingService extends FirebaseMessagingService {

    public static final int NEW_MESSAGE_NOTIFICATION_ID = 65484;

    @SuppressWarnings("unused")
    private static final String TAG = CCMessagingService.class.getSimpleName();

    @NonNull
    private Notifications mNotifications;

    @NonNull
    private NotificationFactory mNotificationFactory;

    @Override
    public void onCreate() {
        super.onCreate();
        getDependencies();
    }

    private void getDependencies() {
        mNotifications = ((ChatcontrolApplication) getApplication()).getNotifications();
        mNotificationFactory = ((ChatcontrolApplication) getApplication()).getNotificationFactory();
    }



    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "onMessageReceived: Title: " + remoteMessage.getNotification().getTitle());
            Log.d(TAG, "FCM Notification Body: " + remoteMessage.getNotification().getBody());
        }

        if (remoteMessage.getData() != null) {
            Log.d(TAG, "onMessageReceived: Data: " + remoteMessage.getData());
        }

        mNotifications.showNotification(NEW_MESSAGE_NOTIFICATION_ID,
                mNotificationFactory.createNewMessageNotification(remoteMessage));
    }
}
