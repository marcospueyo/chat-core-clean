package com.mph.chatcontrol.room.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mph.chatcontrol.R;
import com.mph.chatcontrol.base.BaseViewModel;
import com.mph.chatcontrol.base.adapter.BaseViewHolderImpl;
import com.mph.chatcontrol.room.contract.RoomPresenter;
import com.mph.chatcontrol.room.viewmodel.MessageViewModel;
import com.mph.chatcontrol.utils.DisplayUtils;

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
    @BindView(R.id.ll_message) LinearLayout llMessage;

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
        setStyleForTimestamp(isOwnMessage);
    }

    private void setStyleForContent(boolean isOwnMessage) {
        renderAlignment(tvMessage, isOwnMessage);
    }

    private void setStyleForSender(boolean isOwnMessage) {
        renderAlignment(tvSender, isOwnMessage);

        int color = ContextCompat.getColor(mContext,
                isOwnMessage ? R.color.brand_color : R.color.gray_dark);
        renderTextColor(tvSender, color);

        int bgResource = isOwnMessage
                ? R.drawable.balloon_outgoing_triptips
                : R.drawable.balloon_incoming_triptips;
        renderSenderBackground(llMessage, bgResource);

        int gravity = isOwnMessage ? Gravity.END : Gravity.START;
        int leftMargin = isOwnMessage ? 10 : 15;
        int rightMargin = isOwnMessage ? 15 : 10;
        renderGravity(tvMessage, (LinearLayout.LayoutParams) llMessage.getLayoutParams(), gravity,
                leftMargin, rightMargin);
        renderGravity(tvTimestamp, (LinearLayout.LayoutParams) llMessage.getLayoutParams(), gravity,
                leftMargin, rightMargin);
    }

    private void setStyleForTimestamp(boolean isOwnMessage) {
        int color = ContextCompat.getColor(mContext,
                isOwnMessage ? R.color.gray_dark : R.color.white);
        renderTextColor(tvTimestamp, color);
    }

    private void renderAlignment(TextView textView, boolean isOwnMessage) {
        textView.setTextAlignment(isOwnMessage
                ? TEXT_ALIGNMENT_VIEW_END : TEXT_ALIGNMENT_VIEW_START);
    }

    private void renderText(String value) {
        tvMessage.setText(value);
    }

    private void renderTextColor(TextView textView, int color) {
        textView.setTextColor(color);
    }

    private void renderSenderBackground(View view, int color) {
        view.setBackgroundResource(color);
    }

    private void renderGravity(View view, LinearLayout.LayoutParams layoutParams, int gravity,
                               int left, int right) {
        layoutParams.gravity = gravity;
        view.setLayoutParams(getLayoutGravityParams(gravity, left, right));
    }

    private void renderTimestamp(String value) {
        tvTimestamp.setText(value);
    }

    private void renderSender(String value) {
        tvSender.setText(value);
    }

    private LinearLayout.LayoutParams getLayoutGravityParams(int gravity, int left, int right) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity= gravity;
        lp.leftMargin = DisplayUtils.dpToPx(left);
        lp.rightMargin = DisplayUtils.dpToPx(right);
        lp.topMargin = DisplayUtils.dpToPx(10);
        lp.topMargin = 0;
        return lp;
    }
}
