package com.mph.chatcontrol.chatlist.adapter;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mph.chatcontrol.R;
import com.mph.chatcontrol.chatlist.contract.ChatListPresenter;
import com.mph.chatcontrol.chatlist.viewmodel.ChatViewModel;
import com.mph.chatcontrol.chatlist.widget.CircularTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.graphics.Typeface.BOLD;
import static android.graphics.Typeface.NORMAL;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.google.common.base.Preconditions.checkNotNull;


/* Created by macmini on 17/07/2017. */

public class ChatViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = ChatViewHolder.class.getSimpleName();

    private final ChatListPresenter mPresenter;
    @BindView(R.id.label_title) TextView titleLabel;
    @BindView(R.id.label_descr) TextView descrLabel;
    @BindView(R.id.initial_view) CircularTextView initialLabel;
    @BindView(R.id.label_lastmsg) TextView lastMsgLabel;
    @BindView(R.id.pending_messages) View pendingMessages;

    public ChatViewHolder(@NonNull View itemView, @NonNull ChatListPresenter chatListPresenter) {
        super(itemView);
        mPresenter = checkNotNull(chatListPresenter);
        ButterKnife.bind(this, itemView);
    }

    public void render(ChatViewModel chat, int color) {
        onItemClick(chat);
        renderChatTitle(chat.getTitle());
        renderChatDescription(chat.getDescription());
        renderChatInitial(chat.getInitial(), color);
        renderPendingMessages(chat.getPendingCount());
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

    private void renderChatInitial(String initial, int color) {
        initialLabel.setStrokeWidth(1);
        initialLabel.setStrokeColor(color);
        initialLabel.setSolidColor(color);
        initialLabel.setText(initial.toUpperCase());
    }

    private void renderPendingMessages(int pendingCount) {
        pendingMessages.setVisibility(pendingCount > 0 ? VISIBLE : GONE);
        boolean isHighlighted = pendingCount > 0;
        renderHighlighting(isHighlighted, titleLabel);
        renderHighlighting(isHighlighted, descrLabel);
        renderHighlighting(isHighlighted, lastMsgLabel);
    }

    private void renderHighlighting(boolean isHighlighted, TextView textView) {
        textView.setTypeface(null, isHighlighted ? BOLD : NORMAL);
    }
}
