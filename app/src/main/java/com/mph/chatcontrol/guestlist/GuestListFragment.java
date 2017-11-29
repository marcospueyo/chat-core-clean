package com.mph.chatcontrol.guestlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mph.chatcontrol.R;
import com.mph.chatcontrol.base.BaseListFragment;
import com.mph.chatcontrol.base.presenter.BaseListPresenter;
import com.mph.chatcontrol.chatlist.viewmodel.ChatViewModel;
import com.mph.chatcontrol.data.Chat;
import com.mph.chatcontrol.data.ChatInfo;
import com.mph.chatcontrol.events.OpenChatEvent;
import com.mph.chatcontrol.guestlist.adapter.GuestsAdapter;
import com.mph.chatcontrol.guestlist.contract.GuestListPresenter;
import com.mph.chatcontrol.guestlist.contract.GuestListView;
import com.mph.chatcontrol.guestlist.viewmodel.GuestViewModel;
import com.mph.chatcontrol.utils.CCUtils;

import org.greenrobot.eventbus.EventBus;

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
        return view;
    }

    @Override
    public void setItems(List<GuestViewModel> guests) {
        mAdapter.updateItemList(guests);
    }

    @Override
    public void callGuest(GuestViewModel guest) {
        CCUtils.makeCall(getActivity(), guest.phone());
    }

    @Override
    public void showRoomLoadError() {
        Snackbar.make(mContentView, getString(R.string.guestlist_room_load_error), Snackbar.LENGTH_SHORT);
    }

    @Override
    public void openChat(String chatID) {
        EventBus.getDefault().post(chatID);
    }

    private void initializeAdapter() {
        super.setAdapter(new GuestsAdapter((GuestListPresenter) mPresenter, mContext));
    }
}
