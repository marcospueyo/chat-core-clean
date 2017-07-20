package com.mph.chatcontrol.guestlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mph.chatcontrol.base.BaseListFragment;
import com.mph.chatcontrol.base.presenter.BaseListPresenter;
import com.mph.chatcontrol.guestlist.contract.GuestListPresenter;
import com.mph.chatcontrol.guestlist.contract.GuestListView;
import com.mph.chatcontrol.guestlist.viewmodel.GuestViewModel;

import java.util.List;

/* Created by macmini on 20/07/2017. */

public class GuestListFragment extends BaseListFragment implements GuestListView {

    public static GuestListFragment newInstance() {
        return new GuestListFragment();
    }

    @Override
    public void setPresenter(BaseListPresenter presenter) {
        if (!(presenter instanceof GuestListPresenter))
            throw new IllegalArgumentException();
        super.setPresenter(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        initializeAdapter();
        initializeRecyclerView();
        return view;
    }

    @Override
    public void setItems(List<GuestViewModel> guests) {

    }

    private void initializeAdapter() {

    }

    private void initializeRecyclerView() {

    }
}
