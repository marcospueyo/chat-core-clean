package com.mph.chatcontrol.utils;
/* Created by Marcos on 13/07/2017.*/

import android.support.annotation.Nullable;

public class MenuOptions {
    public enum Option {ACTIVE, ARCHIVED, GUESTLIST, CONFIG}

    @Nullable
    public static Option getOption(Integer order) {
        Option option = null;
        switch (order) {
            case 0:
                option = Option.ACTIVE;
                break;
            case 1:
                option = Option.ARCHIVED;
                break;
            case 2:
                option = Option.GUESTLIST;
                break;
            case 3:
                option = Option.CONFIG;
                break;
            default:
                break;
        }
        return option;
    }
}
