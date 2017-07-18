package com.mph.chatcontrol.base;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    protected Context mContext;

    public View init(LayoutInflater inflater, int layoutRes, ViewGroup container) {
        View view = inflater.inflate(layoutRes, container, false);
        ButterKnife.bind(this, view);
        mContext = container.getContext();
        return view;
    }
}
