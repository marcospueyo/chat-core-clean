package com.mph.chatcontrol.utils;
/* Created by Marcos on 13/07/2017.*/

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;


public class CCUtils {

    @Deprecated
    public static void startFragment(Activity activity, Fragment fragment, int res) {
        FragmentManager fragmentManager = activity.getFragmentManager();
        //while (fragmentManager.popBackStackImmediate()) {}
        fragmentManager.beginTransaction()
                .replace(res, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }
}
