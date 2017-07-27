package com.mph.chatcontrol.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;

/* Created by macmini on 27/07/2017. */

public class MPHEditText extends EditText {

    public MPHEditText(Context context) {
        super(context);
    }

    public MPHEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MPHEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public String getTrimmedText() {
        return getText().toString().trim();
    }

    public boolean isEmpty() {
        return TextUtils.isEmpty(getTrimmedText());
    }
}
