package com.mph.chatcontrol.guestlist.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.mph.chatcontrol.R;
import com.mph.chatcontrol.base.adapter.BaseListAdapter;
import com.mph.chatcontrol.guestlist.contract.GuestListPresenter;

import static com.google.common.base.Preconditions.checkNotNull;

/* Created by macmini on 20/07/2017. */

public class GuestsAdapter extends BaseListAdapter<GuestViewHolder> {

    public static final int resID = R.layout.guest_row;

    private final TypedArray mColorPalette;

    private final GuestListPresenter mPresenter;

    public GuestsAdapter(@NonNull GuestListPresenter presenter, @NonNull Context context) {
        super(presenter, context, resID);
        mColorPalette = context.getResources().obtainTypedArray(R.array.color_palette);
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public GuestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GuestViewHolder(mContext, getInflatedItemView(parent), mPresenter);
    }

    @Override
    public void onBindViewHolder(GuestViewHolder holder, int position) {
        holder.render(mItemList.get(position),
                mColorPalette.getColor(position % mColorPalette.length(), 0));
    }
}
