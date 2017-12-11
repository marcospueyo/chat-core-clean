package com.mph.chatcontrol.utils;
/* Created by macmini on 11/12/2017. */

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.mph.chatcontrol.room.RoomActivity;

import static com.google.common.base.Preconditions.checkNotNull;

public class IntentFactoryImpl implements IntentFactory {

    @NonNull
    private final Context mContext;

    public IntentFactoryImpl(@NonNull Context context) {
        mContext = checkNotNull(context);
    }

    @Override
    public Intent getRoomIntent(String roomID) {
        return RoomActivity.getIntent(mContext, roomID);
    }
}
