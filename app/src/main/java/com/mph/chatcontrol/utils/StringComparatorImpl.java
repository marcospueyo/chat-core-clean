package com.mph.chatcontrol.utils;
/* Created by macmini on 13/12/2017. */

import android.text.TextUtils;

public class StringComparatorImpl implements StringComparator {

    public StringComparatorImpl() {
    }

    @Override
    public boolean stringsAreAlike(String string, String query) {
        return !TextUtils.isEmpty(string) && !TextUtils.isEmpty(query)
                && string.toLowerCase().contains(query.toLowerCase());
    }
}
