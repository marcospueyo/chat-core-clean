package com.mph.chatcontrol.base;


import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    public View inflate(LayoutInflater inflater, int layoutRes, ViewGroup container) {
        View view = inflater.inflate(layoutRes, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
