package com.mph.chatcontrol.room.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mph.chatcontrol.R;
import com.mph.chatcontrol.base.BaseViewModel;
import com.mph.chatcontrol.base.adapter.BaseViewHolderImpl;
import com.mph.chatcontrol.room.contract.RoomPresenter;
import com.mph.chatcontrol.room.viewmodel.MessageViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.TEXT_ALIGNMENT_VIEW_END;
import static android.view.View.TEXT_ALIGNMENT_VIEW_START;

/* Created by macmini on 26/07/2017. */

public class MessageViewHolder extends BaseViewHolderImpl {

    private static final String TAG = MessageViewHolder.class.getSimpleName();

    @BindView(R.id.label_timestamp) TextView tvTimestamp;
    @BindView(R.id.label_sender) TextView tvSender;
    @BindView(R.id.label_text) TextView tvMessage;

    @SuppressWarnings("unused") private final RoomPresenter mPresenter;

    MessageViewHolder(@NonNull Context context, @NonNull View itemView,
                             @NonNull RoomPresenter roomPresenter) {
        super(context, itemView, roomPresenter);
        ButterKnife.bind(this, itemView);
        mPresenter = roomPresenter;
    }

    @Override
    public void render(final BaseViewModel viewModel) {
        super.render(viewModel);
        MessageViewModel message = (MessageViewModel) viewModel;
        setStyle(message.isOwnMessage());
        renderText(message.text());
        renderTimestamp(message.timestamp());
        renderSender(message.senderName());
    }

    private void setStyle(boolean isOwnMessage) {
        setStyleForContent(isOwnMessage);
        setStyleForSender(isOwnMessage);
    }

    private void setStyleForContent(boolean isOwnMessage) {
        renderAlignment(tvMessage, isOwnMessage);
    }

    private void setStyleForSender(boolean isOwnMessage) {
        renderAlignment(tvSender, isOwnMessage);

        int color = ContextCompat.getColor(mContext,
                isOwnMessage ? R.color.brand_color : R.color.gray_dark);
        renderSenderColor(tvSender, color);
    }

    private void renderAlignment(TextView textView, boolean isOwnMessage) {
        textView.setTextAlignment(isOwnMessage
                ? TEXT_ALIGNMENT_VIEW_END : TEXT_ALIGNMENT_VIEW_START);
    }

    private void renderText(String value) {
        tvMessage.setText(value);
    }

    private void renderSenderColor(TextView textView, int color) {
        textView.setTextColor(color);
    }

    private void renderTimestamp(String value) {
        tvTimestamp.setText(value);
    }

    private void renderSender(String value) {
        tvSender.setText(value);
    }
}
