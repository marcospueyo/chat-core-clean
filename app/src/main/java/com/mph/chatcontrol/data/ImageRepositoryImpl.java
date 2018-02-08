package com.mph.chatcontrol.data;
/* Created by macmini on 08/02/2018. */

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.mph.chatcontrol.login.contract.SharedPreferencesRepository;

public class ImageRepositoryImpl implements ImageRepository {


    @NonNull
    private SharedPreferencesRepository sharedPreferencesRepository;

    @Override
    public Bitmap getImage(String resourceID) {
        return null;
    }

    @Override
    public void uploadMessageImage(Bitmap bitmap) {

    }
}
