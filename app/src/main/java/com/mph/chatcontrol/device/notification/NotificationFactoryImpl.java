package com.mph.chatcontrol.device.notification;
/* Created by macmini on 05/12/2017. */

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.res.ResourcesCompat;
import com.mph.chatcontrol.R;

import static com.google.common.base.Preconditions.checkNotNull;

public class NotificationFactoryImpl implements NotificationFactory {

    public static final int NEW_MESSAGE_NOTIFICATION_ID = 65484;

    @NonNull
    private final Context mContext;

    @NonNull
    private final Resources mResources;

    public NotificationFactoryImpl(@NonNull Context context, @NonNull Resources resources) {
        mContext = checkNotNull(context);
        mResources = checkNotNull(resources);
    }

    @Override
    public Notification createNewMessageNotification(String propertyName, String senderName,
                                                     String text, PendingIntent pendingIntent) {
        // TODO: 05/12/2017 Create and manage a notification channel
        final NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(mContext);
        return notificationBuilder
                .setAutoCancel(true)
                .setColor(ResourcesCompat.getColor(mResources, R.color.brand_color, null))
                .setLargeIcon(BitmapFactory.decodeResource(mResources, R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(buildFormattedTitleString(propertyName))
                .setContentText(buildFormattedContentString(senderName, text))
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_MAX)
                .setSound(getAlarmSoundForNewMessage())
                .setVibrate(getVibrationPattern())
                .build();
    }

    @Override
    public int getNewMessageNotificationID() {
        return NEW_MESSAGE_NOTIFICATION_ID;
    }

    private long[] getVibrationPattern() {
        return new long[]{250,250,250,250,250};
    }

    private Uri getAlarmSoundForNewMessage() {
        return RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    }

    private String buildFormattedContentString(String senderName, String text) {
        return senderName + ": " + text;
    }

    private String buildFormattedTitleString(String propertyName) {
        return mResources.getString(R.string.new_message_in_room) + " " + propertyName;
    }
}
