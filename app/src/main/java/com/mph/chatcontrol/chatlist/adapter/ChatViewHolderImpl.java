package com.mph.chatcontrol.chatlist.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.mph.chatcontrol.R;
import com.mph.chatcontrol.base.BaseViewModel;
import com.mph.chatcontrol.base.adapter.BaseViewHolderImpl;
import com.mph.chatcontrol.chatlist.contract.ChatListPresenter;
import com.mph.chatcontrol.chatlist.viewmodel.ChatViewModel;
import com.mph.chatcontrol.widget.CircularTextView;
import com.mph.chatcontrol.utils.CCUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.graphics.Typeface.BOLD;
import static android.graphics.Typeface.NORMAL;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.google.common.base.Preconditions.checkNotNull;


/* Created by macmini on 17/07/2017. */

public class ChatViewHolderImpl extends BaseViewHolderImpl {

    private static final String TAG = ChatViewHolderImpl.class.getSimpleName();

    @BindView(R.id.label_title) TextView titleLabel;
    @BindView(R.id.label_descr) TextView descrLabel;
    @BindView(R.id.initial_view) CircularTextView initialLabel;
    @BindView(R.id.label_lastmsg) TextView lastMsgLabel;
    @BindView(R.id.pending_messages) View pendingMessages;
    @BindView(R.id.label_last_date) TextView lastDateLabel;

    public ChatViewHolderImpl(@NonNull Context context, @NonNull View itemView,
                              @NonNull ChatListPresenter chatListPresenter) {
        super(context, itemView, chatListPresenter);
        ButterKnife.bind(this, itemView);
    }

    public void render(BaseViewModel entity, int color) {
        ChatViewModel chat = (ChatViewModel) entity;
        super.render(chat);
        renderChatTitle(chat.title());
        renderChatDescription(chat.description());
        renderChatInitial(chat.initial(), color);
        renderPendingMessages(chat.pendingCount());
        renderLastActivity(chat);
        renderLastMsgDate(chat.lastMsgDate());
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

    private void renderLastMsgDate(String lastMsgDate) {
        lastDateLabel.setText(lastMsgDate);
    }

    private void renderLastActivity(final ChatViewModel chat) {
        lastMsgLabel.setText(chat.active()
                ? chat.lastActivity() : CCUtils.getFormattedCheckout(mContext, chat.checkoutDate()));
    }
}
