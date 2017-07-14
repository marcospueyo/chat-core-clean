package com.mph.chatcontrol.utils;
/* Created by Marcos on 13/07/2017.*/

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;


public class CCUtils {
    public static void startFragment(Activity activity, Fragment fragment, int res) {
        FragmentManager fragmentManager = activity.getFragmentManager();
        //while (fragmentManager.popBackStackImmediate()) {}
        fragmentManager.beginTransaction()
                .replace(res, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
