package com.mph.chatcontrol.utils;
/* Created by macmini on 08/08/2017. */

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * *
        * The Class DisplayUtils.
        */
public class DisplayUtils {

    /**
     * Converteix el valor en dp a px
     *
     * @param dp Valor en dp
     * @return Valor convertit a px
     */
    public static int dpToPx(float dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    /**
     * Converteix el valor en px a dp
     *
     * @param px Valor en px
     * @return Valor convertit a dp
     */
    public static int pxToDp(float px) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(px / density);
    }

    /**
     * Converteix el valor en px a sp
     *
     * @param px Valor en px
     * @return Valor convertit a sp
     */
    public static int pxToSp(float px) {
        float scaledDensity = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return Math.round(px / scaledDensity);
    }

    /**
     * Converteix el valor en sp a px
     *
     * @param sp Valor en sp
     * @return Valor convertit a px
     */
    public static int spToPx(float sp) {
        float scaledDensity = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return Math.round(sp * scaledDensity);
    }

    /**
     * Gets the screen width.
     *
     * @return the screen width
     */
    public static int getScreenWidth() {
        DisplayMetrics displaymetrics = Resources.getSystem()
                .getDisplayMetrics();
        return displaymetrics.widthPixels;
    }

    /**
     * Gets the screen height.
     *
     * @return the screen height
     */
    public static int getScreenHeight() {
        DisplayMetrics displaymetrics = Resources.getSystem().getDisplayMetrics();
        return displaymetrics.heightPixels;
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static void removeOnGlobalLayoutListener(View view, ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
        ViewTreeObserver obs = view.getViewTreeObserver();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            obs.removeOnGlobalLayoutListener(onGlobalLayoutListener);
        } else {
            obs.removeGlobalOnLayoutListener(onGlobalLayoutListener);
        }
    }

    public static int getTextHeight(String text, int maxWidth, Paint paint) {
        // If maxWidth <= 0 we set maxWidth to the screenWidth
        if(maxWidth <= 0) {
            maxWidth = getScreenWidth();
        }

        int lineCount = 0;

        int index = 0;
        int length = text.length();

        while(index < length - 1) {
            index += paint.breakText(text, index, length, true, maxWidth, null);
            lineCount++;
        }

        Rect bounds = new Rect();
        paint.getTextBounds("Py", 0, 2, bounds);
        return (int)Math.floor(lineCount * bounds.height());
    }

}
