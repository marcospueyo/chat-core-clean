package com.mph.chatcontrol.utils;
/* Created by macmini on 08/02/2018. */

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

public class BitmapUtils {
    private static final String TAG = "BitmapUtils";


    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
            //inSampleSize *= 2;
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromFile(String file, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(file, options);
    }

    public static Bitmap decodeSampledBitmapFromFile(String file, int reqWidth, int reqHeight, int sampleSize) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        if(sampleSize < 0) {
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(file, options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        }
        else {
            options.inSampleSize = sampleSize;
        }

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(file, options);
    }

    public static Bitmap decodeSampledBitmapFromStream(String url, int reqWidth, int reqHeight) {
        InputStream iStream;
        try {
            iStream = new java.net.URL(url).openStream();
            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(iStream, new Rect(), options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

            // Decode bitmap with inSampleSize set
            iStream = new java.net.URL(url).openStream();
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeStream(iStream, new Rect(), options);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap decodeSampledBitmapFromStream(Context context, Uri uri, int reqWidth, int reqHeight) {
        try {
            InputStream iStream = context.getContentResolver().openInputStream(uri);
            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(iStream, new Rect(), options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

            // Decode bitmap with inSampleSize set
            iStream = context.getContentResolver().openInputStream(uri);
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeStream(iStream, new Rect(), options);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap cropSquared(Bitmap original) {
        Bitmap result;
        if (original.getWidth() >= original.getHeight()){
            result = Bitmap.createBitmap(
                    original,
                    original.getWidth()/2 - original.getHeight()/2,
                    0,
                    original.getHeight(),
                    original.getHeight()
            );
        }
        else {
            result = Bitmap.createBitmap(
                    original,
                    0,
                    original.getHeight()/2 - original.getWidth()/2,
                    original.getWidth(),
                    original.getWidth()
            );
        }
        return result;
    }

    public static Bitmap getBitmapFromIntent(Context context, Intent data) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = { MediaStore.Images.Media.DATA };

        // Get the cursor

        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Move to first row
        if (cursor == null) {
            Log.d(TAG, "getBitmap: Can't get the cursor");
            return null;
        }
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String imgDecodableString = cursor.getString(columnIndex);
        cursor.close();

        Bitmap bitmap = null;
        try {
            bitmap = BitmapUtils.safeImageProcessing(imgDecodableString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Bitmap safeImageProcessing(String pathName) {
        int orientation = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(pathName);
            orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            Log.d(TAG, "safeImageProcessing: degree = " + orientation);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bitmap originalBitmap = safeDecodeFile(pathName);
        Bitmap scaledBitmap = scaleWithinLimits(originalBitmap, 512);
        Bitmap croppedBitmap = cropSquared(scaledBitmap);
        return rotateBitmap(croppedBitmap, orientation);
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap safeDecodeFile(String pathName) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inDither = true;
        return BitmapFactory.decodeFile(pathName, options);
    }

    public static Bitmap scaleWithinLimits(Bitmap original, float pxLimit) {
        Bitmap result;
        if (original.getWidth() > pxLimit || original.getHeight() > pxLimit) {
            //float aspectRatio = original.getWidth() / original.getHeight();
            float scale;
            if (original.getWidth() > original.getHeight()) {
                scale = pxLimit / original.getWidth();
            }
            else {
                scale = pxLimit / original.getHeight();
            }
            int newWidth = (int) (original.getWidth() * scale);
            int newHeight = (int) (original.getHeight() * scale);

            result = Bitmap.createScaledBitmap(original, newWidth/*100*/, newHeight/*100*/, true);
        }
        else {
            result = original;
        }
        return result;
    }
}
