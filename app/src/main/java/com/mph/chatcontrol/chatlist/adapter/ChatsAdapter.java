package com.mph.chatcontrol.chatlist.adapter;
/* Created by macmini on 17/07/2017. */

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mph.chatcontrol.R;
import com.mph.chatcontrol.chatlist.contract.ChatListPresenter;
import com.mph.chatcontrol.chatlist.viewmodel.ChatViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


public class ChatsAdapter extends RecyclerView.Adapter<ChatViewHolder> {

    @NonNull
    private final ChatListPresenter mPresenter;

    private final List<ChatViewModel> mChatList;

    public ChatsAdapter(@NonNull ChatListPresenter presenter) {
        this.mPresenter = checkNotNull(presenter);
        mChatList = new ArrayList<>();
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_row, parent,
                false);
        return new ChatViewHolder(view, mPresenter);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        holder.render(mChatList.get(position));
    }

    @Override
    public int getItemCount() {
        return mChatList.size();
    }

    public void addAll(Collection<ChatViewModel> collection) {
        mChatList.addAll(collection);
    }
}