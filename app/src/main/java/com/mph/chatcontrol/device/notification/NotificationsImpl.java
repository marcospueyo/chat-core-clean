package com.mph.chatcontrol.device.notification;
/* Created by macmini on 05/12/2017. */

import android.app.Notification;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationManagerCompat;

import static com.google.common.base.Preconditions.checkNotNull;

public class NotificationsImpl implements Notifications {

    @SuppressWarnings("unused")
    private static final String TAG = NotificationsImpl.class.getSimpleName();

    private final NotificationManagerCompat mNotificationManagerCompat;

    public NotificationsImpl(@NonNull final NotificationManagerCompat notificationManagerCompat) {
        mNotificationManagerCompat = checkNotNull(notificationManagerCompat);
    }

    @Override
    public void showNotification(final int notificationID, final Notification notification) {
        mNotificationManagerCompat.notify(notificationID, notification);
    }

    @Override
    public void updateNotification(final int notificationID, final Notification notification) {
        mNotificationManagerCompat.notify(notificationID, notification);
    }

    @Override
    public void hideNotification(final int notificationID) {
        mNotificationManagerCompat.cancel(notificationID);
    }

    @Override
    public void hideNotifications() {
        mNotificationManagerCompat.cancelAll();
    }
}