package com.mph.chatcontrol.device.notification;
/* Created by macmini on 05/12/2017. */

import android.app.Notification;

import com.google.firebase.messaging.RemoteMessage;

public interface NotificationFactory {

    Notification createNewMessageNotification(RemoteMessage remoteMessage);
}
