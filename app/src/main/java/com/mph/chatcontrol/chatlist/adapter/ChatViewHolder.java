package com.mph.chatcontrol.chatlist.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mph.chatcontrol.R;
import com.mph.chatcontrol.chatlist.ChatlistFragment;
import com.mph.chatcontrol.chatlist.contract.ChatListPresenter;
import com.mph.chatcontrol.chatlist.viewmodel.ChatViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;


/* Created by macmini on 17/07/2017. */

public class ChatViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = ChatViewHolder.class.getSimpleName();

    private final ChatListPresenter mPresenter;
    @BindView(R.id.label_title) TextView titleLabel;
    @BindView(R.id.label_descr) TextView descrLabel;

    public ChatViewHolder(@NonNull View itemView, @NonNull ChatListPresenter chatListPresenter) {
        super(itemView);
        mPresenter = checkNotNull(chatListPresenter);
        ButterKnife.bind(this, itemView);
    }

    public void render(ChatViewModel chat) {
        onItemClick(chat);
        renderChatTitle(chat.getTitle());
        renderChatDescription(chat.getDescription());
    }

    private void onItemClick(final ChatViewModel chatViewModel) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onItemClicked(chatViewModel);
            }
        });
    }

    private void renderChatTitle(String title) {
        titleLabel.setText(title);
    }

    private void renderChatDescription(String description) {
        descrLabel.setText(description);
    }
}
