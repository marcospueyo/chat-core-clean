package com.mph.chatcontrol.network;
/* Created by macmini on 08/02/2018. */

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

public class ImageServiceImpl implements ImageService {

    @SuppressWarnings("unused")
    private static final String TAG = ImageServiceImpl.class.getSimpleName();


    @NonNull
    private final FirebaseDatabaseData mFirebaseDatabaseData;

    @Override
    public void uploadImage(Bitmap bitmap, UploadImageCallback callback) {

    }
}
