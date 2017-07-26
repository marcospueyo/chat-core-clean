package com.mph.chatcontrol.utils;
/* Created by Marcos on 13/07/2017.*/

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.mph.chatcontrol.Manifest;
import com.mph.chatcontrol.R;

import static android.graphics.Typeface.BOLD;
import static android.graphics.Typeface.NORMAL;
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

    public static boolean isAPI_L_OrAbove() {
        return android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static RecyclerView.AdapterDataObserver getScrolldownObserver(
            final RecyclerView.Adapter adapter,
            final LinearLayoutManager layoutManager,
            final RecyclerView recyclerView) {
        return new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int messageCount = adapter.getItemCount();
                int lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition();

                if (lastVisiblePosition == -1  || (positionStart >= (messageCount - 1)
                        && lastVisiblePosition == (positionStart -1))) {
                    recyclerView.smoothScrollToPosition(positionStart );
                }
            }

            @Override
            public void onChanged() {
                super.onChanged();
                recyclerView.smoothScrollToPosition(adapter.getItemCount());
            }
        };
    }


    // TODO: 26/07/2017 Extend TextView with this method
    public static void renderHighlighting(boolean isHighlighted, TextView textView) {
        textView.setTypeface(null, isHighlighted ? BOLD : NORMAL);
    }

    public static void makeCall(final Activity activity, String phoneNumber) {
        int checkPermission = ContextCompat.checkSelfPermission(activity, "android.permission.CALL_PHONE");
        if (checkPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    new String[]{"android.permission.CALL_PHONE"},
                    /*REQUEST_CALL_PHONE)*/ 0);
        }
        else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            activity.startActivity(intent);
        }
    }
}
