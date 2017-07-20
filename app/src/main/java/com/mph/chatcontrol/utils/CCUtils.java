package com.mph.chatcontrol.utils;
/* Created by Marcos on 13/07/2017.*/

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.annotation.NonNull;

import com.mph.chatcontrol.R;

import static com.google.common.base.Preconditions.checkNotNull;


public class CCUtils {

    public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        fragmentManager.beginTransaction()
                .replace(frameId, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    public static String getFormattedCheckout(final Context context, String date) {
        return context.getString(R.string.checkout) + " " + date;
    }
}
