package com.mph.chatcontrol.network;
/* Created by macmini on 08/02/2018. */

import android.graphics.Bitmap;

public interface ImageService {

    interface UploadImageCallback {

        void onImageUploadSuccess(String url);

        void onImageUploadError();

    }

    void uploadImage(Bitmap bitmap, UploadImageCallback callback);
}
