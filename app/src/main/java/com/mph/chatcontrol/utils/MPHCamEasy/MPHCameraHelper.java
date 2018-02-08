package com.mph.chatcontrol.utils.MPHCamEasy;
/* Created by macmini on 08/02/2018. */

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.mph.chatcontrol.R;

public class MPHCameraHelper {
    private static final String TAG = "MPHCameraHelper";

    public static final int MY_PERMISSIONS_REQUEST_ACCESS_FILES = 2;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 3;
    public static int RESULT_LOAD_IMG = 1;

    public static void startImageLoadingProcess(final Context context,
                                                final DialogInterface.OnCancelListener listener) {
        String[] items = {
                context.getString(R.string.image_source_camera),
                context.getString(R.string.image_source_gallery)};
        new AlertDialog.Builder(context)
                .setTitle(R.string.alert_title_choose_source)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            // Camera
                            loadImageFromCamera(context);
                        }
                        else {
                            // Gallery
                            loadImageFromGallery(context);
                        }
                    }
                })
                .setOnCancelListener(listener)
                .show();
    }

    private static void loadImageFromGallery(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissionsForGallery(context);
        }
        else {
            startGalleryIntent(context);
        }
    }

    private static void loadImageFromCamera(Context context) {
        if (Build.VERSION.SDK_INT >= 23){
            requestPermissionsForCamera(context);
        }
        else {
            takeImageFromCamera(context);
        }
    }

    private static void requestPermissionsForGallery(Context context) {
        if (ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)) {}
            else {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_ACCESS_FILES);
            }
        }
        else {
            startGalleryIntent(context);
        }
    }

    private static void requestPermissionsForCamera(Context context) {
        if (ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                    android.Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{android.Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_CAMERA);
            }
            else {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{android.Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_CAMERA);
            }
        }
        else {
            takeImageFromCamera(context);
        }
    }

    private static void startGalleryIntent(Context context) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        ((Activity)context).startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    private static void takeImageFromCamera(Context context) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            ((Activity)context).startActivityForResult(takePictureIntent, RESULT_LOAD_IMG);
        }
    }
}
