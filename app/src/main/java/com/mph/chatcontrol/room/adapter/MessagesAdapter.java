package com.mph.chatcontrol.room.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.mph.chatcontrol.R;
import com.mph.chatcontrol.base.adapter.BaseListAdapter;
import com.mph.chatcontrol.room.contract.RoomPresenter;

import static com.google.common.base.Preconditions.checkNotNull;

/* Created by macmini on 26/07/2017. */

public class MessagesAdapter extends BaseListAdapter<MessageViewHolder> {

    public static final int resID = R.layout.message_row;

    private final RoomPresenter mPresenter;

    public MessagesAdapter(@NonNull Context context, @NonNull RoomPresenter presenter) {
        super(presenter, context, resID);
        this.mPresenter = checkNotNull(presenter);
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MessageViewHolder(mContext, getInflatedItemView(parent), mPresenter);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        holder.render(mItemList.get(position));
    }
}
