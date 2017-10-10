package com.mph.chatcontrol.room;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
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

import com.google.firebase.database.DatabaseReference;
import com.mph.chatcontrol.ChatcontrolApplication;
import com.mph.chatcontrol.R;
import com.mph.chatcontrol.base.adapter.BaseListAdapter;
import com.mph.chatcontrol.base.presenter.BaseListPresenter;
import com.mph.chatcontrol.chatlist.viewmodel.ChatViewModel;
import com.mph.chatcontrol.chatlist.viewmodel.mapper.ChatViewModelToChatMapper;
import com.mph.chatcontrol.data.ChatsRepository;
import com.mph.chatcontrol.data.ChatsRepositoryImpl;
import com.mph.chatcontrol.data.MessagesRepository;
import com.mph.chatcontrol.data.MessagesRepositoryImpl;
import com.mph.chatcontrol.login.contract.SharedPreferencesRepository;
import com.mph.chatcontrol.network.RestRoomToChatMapper;
import com.mph.chatcontrol.network.RoomFirebaseServiceImpl;
import com.mph.chatcontrol.room.adapter.MessagesAdapter;
import com.mph.chatcontrol.room.contract.RoomPresenter;
import com.mph.chatcontrol.room.contract.RoomView;
import com.mph.chatcontrol.room.viewmodel.MessageViewModel;
import com.mph.chatcontrol.room.viewmodel.mapper.MessageViewModelToMessageMapper;
import com.mph.chatcontrol.utils.CCUtils;
import com.mph.chatcontrol.widget.MPHEditText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.requery.Persistable;
import io.requery.sql.EntityDataStore;

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

    @BindView(R.id.cl_edit_area) View mInputArea;

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

    private DatabaseReference getDatabaseReference() {
        return ((ChatcontrolApplication) getApplication()).getChatDatabaseReference();
    }

    private void initializePresenter() {
        ChatsRepository chatsRepository =
                new ChatsRepositoryImpl(
                        getSharedPreferencesRepository(),
                        ((ChatcontrolApplication) getApplication()).getData(),
                        new RoomFirebaseServiceImpl(getDatabaseReference()),
                        new RestRoomToChatMapper()
                );

        MessagesRepository messagesRepository =
                ((ChatcontrolApplication) getApplication()).getMessagesRepository(this);

        mPresenter = new RoomPresenterImpl(
                this,
                getRoomID(),
                new ChatViewModelToChatMapper(),
                new MessageViewModelToMessageMapper(getSharedPreferencesRepository()),
                new GetRoomInteractorImpl(chatsRepository),
                new GetMessagesInteractorImpl(messagesRepository),
                new SendMessageInteractorImpl(messagesRepository),
                new UpdateSeenStatusInteractorImpl(chatsRepository));
    }

    private SharedPreferencesRepository getSharedPreferencesRepository() {
        return ((ChatcontrolApplication) getApplication()).getSharedPreferencesRepository(this);
    }

    public EntityDataStore<Persistable> getDataStore() {
        return (((ChatcontrolApplication) getApplication()).getData());
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
    public void enableChat() {
        setInputAreaEnableState(true);
    }

    @Override
    public void disableChat() {
        setInputAreaEnableState(false);
    }

    private void setInputAreaEnableState(boolean enabled) {
        int color = ContextCompat.getColor(this, enabled
                ? R.color.brand_color_brighter : R.color.gray_dark);
        int backgroundColor = ContextCompat.getColor(this, enabled
                ? R.color.brand_color : R.color.gray_dark);

        mSendButton.setBackgroundTintList(ColorStateList.valueOf(color));
        mInputArea.setBackgroundColor(backgroundColor);
        mMessageInput.setEnabled(enabled);
        mMessageInput.setHint(getString(enabled
                ? R.string.write_message_hint : R.string.write_message_disabled_hint));
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
        mListView.setHasFixedSize(true);
    }

    private void initializeAdapter(LinearLayoutManager layoutManager) {
        mAdapter = new MessagesAdapter(this, mPresenter);
        mAdapter.registerAdapterDataObserver(
                CCUtils.getScrolldownObserver(mAdapter, layoutManager, mListView));

        mListView.addOnLayoutChangeListener(
                CCUtils.getScrolldownLayoutObserver(mAdapter, mListView));
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

    private static String getFormattedRoomTitle(ChatViewModel chat) {
        return chat.description() + " | " + chat.title();
    }
}
