package com.mph.chatcontrol.chatlist.adapter;
/* Created by macmini on 17/07/2017. */

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.mph.chatcontrol.R;
import com.mph.chatcontrol.base.adapter.BaseListAdapter;
import com.mph.chatcontrol.chatlist.contract.ChatListPresenter;

import static com.google.common.base.Preconditions.checkNotNull;


public class ChatsAdapter extends BaseListAdapter<ChatViewHolderImpl> {

    private static final int resID = R.layout.chat_row;
    private final TypedArray mColorPalette;
    private final ChatListPresenter mPresenter;


    public ChatsAdapter(@NonNull ChatListPresenter presenter, @NonNull Context context) {
        super(presenter, context, resID);
        mColorPalette = context.getResources().obtainTypedArray(R.array.color_palette);
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public ChatViewHolderImpl onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChatViewHolderImpl(mContext, getInflatedItemView(parent), mPresenter);
    }

    @Override
    public void onBindViewHolder(ChatViewHolderImpl holder, int position) {
        holder.render(mItemList.get(position),
                mColorPalette.getColor(position % mColorPalette.length(), 0));
    }
}
