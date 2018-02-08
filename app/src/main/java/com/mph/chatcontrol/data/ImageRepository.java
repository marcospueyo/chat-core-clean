package com.mph.chatcontrol.data;
/* Created by macmini on 08/02/2018. */

import android.graphics.Bitmap;

public interface ImageRepository {

    Bitmap getImage(String resourceID);

    void uploadMessageImage(Bitmap bitmap);
}
