package com.mph.chatcontrol.room.contract;
/* Created by macmini on 08/02/2018. */

import android.graphics.Bitmap;

public interface UploadImageInteractor {

    interface OnFinishedListener {

        void onImageUploadSuccess();

        void onImageUploadError();
    }

    void execute(Bitmap bitmap, OnFinishedListener listener);
}
