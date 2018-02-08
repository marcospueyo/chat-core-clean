package com.mph.chatcontrol.utils.MPHCamEasy;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mph.chatcontrol.R;
import com.mph.chatcontrol.utils.BitmapUtils;

public class GhostActivity extends AppCompatActivity {
    private static final String TAG = "GhostActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
    }

    @Override
    public void onResume() {
        super.onResume();
        startImageLoadingProcess();
    }

    private void startImageLoadingProcess() {
        DialogInterface.OnCancelListener onCancelListener = new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                onBackPressed();
            }
        };
        MPHCameraHelper.startImageLoadingProcess(this, onCancelListener);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: fired");
        if (requestCode == MPHCameraHelper.MY_PERMISSIONS_REQUEST_ACCESS_FILES
                || requestCode == MPHCameraHelper.MY_PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (requestCode == MPHCameraHelper.MY_PERMISSIONS_REQUEST_ACCESS_FILES) {
                    startGalleryIntent();
                }
                else {
                    takeImageFromCamera();
                }
            }
            else {
                Log.e(TAG, "onRequestPermissionsResult: Usuari no ha donat permisos");
            }
        }
    }

    private void startGalleryIntent() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, MPHCameraHelper.RESULT_LOAD_IMG);
    }

    public void takeImageFromCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, MPHCameraHelper.RESULT_LOAD_IMG);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = null;
        try {
            // When an InstagramImage is picked
            if (requestCode == MPHCameraHelper.RESULT_LOAD_IMG
                    && resultCode == RESULT_OK
                    && data != null) {
                bitmap = BitmapUtils.getBitmapFromIntent(this, data);
                if (bitmap == null) {
                    Toast.makeText(this, getString(R.string.error_getting_image),
                            Toast.LENGTH_SHORT).show();

                }
                else {
                    Log.d(TAG, "InstagramImage loaded: " + bitmap.getWidth() + " x " + bitmap.getHeight());
                }
            }
        } catch (Exception e) {
            try {
                Toast.makeText(this, getString(R.string.something_wrong), Toast.LENGTH_LONG).show();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }

        Intent resultIntent = new Intent();
        int result = -1;
        if (bitmap != null) {
            result = MPHCameraController.CODE_OK;
            resultIntent.putExtra(MPHCameraController.EXTRA_BITMAP, bitmap);
        }
        setResult(result, resultIntent);
        finish();
    }
}
