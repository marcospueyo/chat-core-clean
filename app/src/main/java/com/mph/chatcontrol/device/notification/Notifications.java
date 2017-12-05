package com.mph.chatcontrol.device.notification;
/* Created by macmini on 05/12/2017. */

import android.app.Notification;

public interface Notifications {

    void showNotification(int notificationID, Notification notification);

    void updateNotification(int notificationID, Notification notification);

    void hideNotification(int notificationID);

    void hideNotifications();
}
