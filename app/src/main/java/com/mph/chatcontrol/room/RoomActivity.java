package com.mph.chatcontrol.room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mph.chatcontrol.R;
import com.mph.chatcontrol.base.adapter.BaseListAdapter;
import com.mph.chatcontrol.base.presenter.BaseListPresenter;
import com.mph.chatcontrol.chatlist.viewmodel.ChatViewModel;
import com.mph.chatcontrol.chatlist.viewmodel.mapper.ChatViewModelToChatMapper;
import com.mph.chatcontrol.room.adapter.MessagesAdapter;
import com.mph.chatcontrol.room.contract.RoomPresenter;
import com.mph.chatcontrol.room.contract.RoomView;
import com.mph.chatcontrol.room.viewmodel.MessageViewModel;
import com.mph.chatcontrol.room.viewmodel.mapper.MessageViewModelToMessageMapper;
import com.mph.chatcontrol.utils.CCUtils;
import com.mph.chatcontrol.widget.DividerItemDecoration;
import com.mph.chatcontrol.widget.MPHEditText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/* Created by macmini on 24/07/2017. */

public class RoomActivity extends AppCompatActivity implements RoomView {

    public static final String ROOM_ID_KEY = "room_id_key";

    @SuppressWarnings("unused")
    private static final String TAG = RoomActivity.class.getSimpleName();

    @BindView(R.id.toolbar) Toolbar mToolbar;

    @BindView(R.id.listview) RecyclerView mListView;

    @BindView(R.id.progressbar) ProgressBar mProgressBar;

    @BindView(R.id.floatingActionButton) FloatingActionButton mSendButton;

    @BindView(R.id.etMessage) MPHEditText mMessageInput;

    private RoomPresenter mPresenter;
    private BaseListAdapter mAdapter;

    public static Intent getIntent(Context context, String roomID) {
        Intent intent = new Intent(context, RoomActivity.class);
        intent.putExtra(ROOM_ID_KEY, roomID);
        return intent;
    }

    private String getRoomID() {
        return getIntent().getExtras().getString(ROOM_ID_KEY);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        ButterKnife.bind(this);
        setupToolbar();
        initializePresenter();
        initializeListView();
        onSendListener();
    }

    private void initializePresenter() {
        mPresenter = new RoomPresenterImpl(
                this,
                getRoomID(),
                new ChatViewModelToChatMapper(),
                new MessageViewModelToMessageMapper(),
                new GetRoomInteractorImpl(),
                new GetMessagesInteractorImpl(),
                new SendMessageInteractorImpl());
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayShowHomeEnabled(false);
        ab.setDisplayHomeAsUpEnabled(false);
        ab.setDisplayShowCustomEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        mToolbar.findViewById(R.id.ib_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setToolbarTitle(String title) {
        TextView tvTitle = (TextView) mToolbar.findViewById(R.id.tv_title);
        tvTitle.setText(title);
    }

    @Override
    protected void onDestroy() {
        mPresenter.unbindView();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(BaseListPresenter presenter) {
        mPresenter = (RoomPresenter) presenter;
    }

    @Override
    public void setRoom(ChatViewModel room) {
        setToolbarTitle(getFormattedRoomTitle(room));
    }

    @Override
    public void setMessages(List<MessageViewModel> messages) {
        mAdapter.updateItemList(messages);
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void addMessage(MessageViewModel message) {
        mAdapter.addItem(message);
    }

    @Override
    public void showLoadError() {
        Snackbar.make(findViewById(android.R.id.content), getString(R.string.room_load_error),
                Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setChatEnabled(boolean enabled) {
    }

    @Override
    public void handleMessageSendSuccess() {
        clearViewAfterSentMessage();
    }

    @Override
    public void handleMessageSendError() {
        Snackbar.make(findViewById(android.R.id.content), getString(R.string.message_send_error),
                Snackbar.LENGTH_SHORT).show();
    }

    private void initializeListView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        initializeRecyclerView(layoutManager);
        initializeAdapter(layoutManager);
    }

    private void initializeRecyclerView(LinearLayoutManager layoutManager) {
        layoutManager.setStackFromEnd(true);
        mListView.setLayoutManager(layoutManager);
        mListView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST,
                        ContextCompat.getColor(this, R.color.brand_color)));
        mListView.setHasFixedSize(true);
    }

    private void initializeAdapter(LinearLayoutManager layoutManager) {
        mAdapter = new MessagesAdapter(this, mPresenter);
        mAdapter.registerAdapterDataObserver(
                CCUtils.getScrolldownObserver(mAdapter, layoutManager, mListView));
        mListView.setAdapter(mAdapter);
    }

    private void onSendListener() {
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onMessageSendClick(mMessageInput.getTrimmedText());
            }
        });
    }

    private void clearViewAfterSentMessage() {
        mMessageInput.setText("");
    }

    private void showPendingMsg() {
        Snackbar.make(findViewById(android.R.id.content), "Pendiente",
                Snackbar.LENGTH_SHORT).show();
    }

    private static String getFormattedRoomTitle(ChatViewModel chat) {
        return chat.description() + " | " + chat.title();
    }
}
