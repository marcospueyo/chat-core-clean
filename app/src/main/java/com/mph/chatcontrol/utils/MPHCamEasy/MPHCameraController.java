package com.mph.chatcontrol.utils.MPHCamEasy;
/* Created by macmini on 08/02/2018. */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;

public class MPHCameraController {
    private static final String TAG = "MPHCameraController";

    public static final int CODE_CAMERA_HANDLER_ACTIVITY = 1;
    public static final String EXTRA_BITMAP = "bitmap_image";
    public static final int CODE_OK = 1;

    Activity mRootActivity;
    MPHCameraCallback mCallback;

    public interface MPHCameraCallback {
        void onImageRetrievalFinished(MPHCameraResponse response);
    }

    public class MPHCameraResponse {
        boolean successful;
        Bitmap bitmap;

        public MPHCameraResponse() {
        }

        public boolean isSuccessful() {
            return successful;
        }

        public void setSuccessful(boolean successful) {
            this.successful = successful;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }
    }

    public MPHCameraController(Activity rootActivity, MPHCameraCallback callback) {
        mRootActivity = rootActivity;
        mCallback = callback;
    }

    public void startImageRetrieval() {
        Intent intent = new Intent(new Intent(mRootActivity, GhostActivity.class));
        mRootActivity.startActivityForResult
                (intent, CODE_CAMERA_HANDLER_ACTIVITY);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        MPHCameraResponse response = new MPHCameraResponse();
        if (resultCode == CODE_OK) {
            Bitmap bitmap = data.getParcelableExtra(EXTRA_BITMAP);
            response.setSuccessful(true);
            response.setBitmap(bitmap);
        }
        else {
            response.setSuccessful(false);
        }
        notifyListener(response);
    }

    private void notifyListener(MPHCameraResponse response) {
        if (mCallback != null) {
            mCallback.onImageRetrievalFinished(response);
        }
    }
}
