package com.mph.chatcontrol.device.notification;
/* Created by macmini on 05/12/2017. */

import android.app.Notification;
import android.app.PendingIntent;

public interface NotificationFactory {

    Notification createNewMessageNotification(String propertyName, String senderName, String text,
                                              PendingIntent pendingIntent);

    int getNewMessageNotificationID();
}
