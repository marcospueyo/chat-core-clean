package com.mph.chatcontrol.device.notification;
/* Created by macmini on 05/12/2017. */

import android.app.Notification;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.res.ResourcesCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.mph.chatcontrol.R;

import static com.google.common.base.Preconditions.checkNotNull;

public class NotificationFactoryImpl implements NotificationFactory {

    @NonNull
    private final Context mContext;

    @NonNull
    private final Resources mResources;

    public NotificationFactoryImpl(@NonNull Context context, @NonNull Resources resources) {
        mContext = checkNotNull(context);
        mResources = checkNotNull(resources);
    }

    @Override
    public Notification createNewMessageNotification(RemoteMessage remoteMessage) {
        // TODO: 05/12/2017 Create and manage a notification channel
        final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext);
        String contentText = remoteMessage.getNotification() != null
                ? remoteMessage.getNotification().getBody() : "";
        return notificationBuilder
                .setAutoCancel(true)
                .setColor(ResourcesCompat.getColor(mResources, R.color.brand_color, null))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(mResources.getString(R.string.new_message))
                .setContentText(contentText)
                /*.setContentIntent(remoteMessage)*/
                .build();
    }
}
