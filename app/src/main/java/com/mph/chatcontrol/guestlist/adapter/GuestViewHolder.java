package com.mph.chatcontrol.guestlist.adapter;


/* Created by macmini on 20/07/2017. */

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mph.chatcontrol.R;
import com.mph.chatcontrol.base.BaseViewModel;
import com.mph.chatcontrol.base.adapter.BaseViewHolderImpl;
import com.mph.chatcontrol.guestlist.contract.GuestListPresenter;
import com.mph.chatcontrol.guestlist.viewmodel.GuestViewModel;
import com.mph.chatcontrol.widget.CircularTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuestViewHolder extends BaseViewHolderImpl {

    private static final String TAG = GuestViewHolder.class.getSimpleName();

    @BindView(R.id.label_title) TextView titleLabel;
    @BindView(R.id.label_descr) TextView descrLabel;
    @BindView(R.id.initial_view) CircularTextView initialLabel;
    @BindView(R.id.ib_call) ImageButton callButton;
    @BindView(R.id.ib_chat) ImageButton chatButton;

    private final GuestListPresenter mPresenter;

    public GuestViewHolder(@NonNull Context context, @NonNull View itemView,
                           @NonNull GuestListPresenter guestListPresenter) {
        super(context, itemView, guestListPresenter);
        ButterKnife.bind(this, itemView);
        mPresenter = guestListPresenter;
    }

    public void render(final BaseViewModel entity, int color) {
        GuestViewModel guest = (GuestViewModel) entity;
        super.render(guest);
        renderInitial(guest.initial(), color);
        renderGuestName(guest.name());
        renderGuestInterval(guest.startDate(), guest.endDate());
        onCall(guest);
        onChat(guest);
    }

    private void renderInitial(String initial, int color) {
        initialLabel.setStrokeWidth(1);
        initialLabel.setStrokeColor(color);
        initialLabel.setSolidColor(color);
        initialLabel.setText(initial.toUpperCase());
    }

    private void renderGuestName(String name) {
        titleLabel.setText(name);
    }

    private void renderGuestInterval(String startDate, String endDate) {
        descrLabel.setText(startDate + " - " + endDate);
    }

    private void onCall(final GuestViewModel guest) {
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onCallClicked(guest);
            }
        });
    }

    private void onChat(final GuestViewModel guest) {
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onChatClicked(guest);
            }
        });
    }
}
