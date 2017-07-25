package com.mph.chatcontrol.room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mph.chatcontrol.R;
import com.mph.chatcontrol.base.presenter.BaseListPresenter;
import com.mph.chatcontrol.chatlist.viewmodel.ChatViewModel;
import com.mph.chatcontrol.chatlist.viewmodel.mapper.ChatViewModelToChatMapper;
import com.mph.chatcontrol.room.contract.RoomPresenter;
import com.mph.chatcontrol.room.contract.RoomView;

import java.util.List;

import butterknife.ButterKnife;

/* Created by macmini on 24/07/2017. */

public class RoomActivity extends AppCompatActivity implements RoomView {

    public static final String ROOM_ID_KEY = "room_id_key";

    private static final String TAG = RoomActivity.class.getSimpleName();

    private Toolbar mToolbar;

    private RoomPresenter mPresenter;

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

        mPresenter = new RoomPresenterImpl(
                this,
                getRoomID(),
                new ChatViewModelToChatMapper(),
                new GetRoomInteractorImpl());
    }



    private void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
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

    }

    @Override
    public void setMessages(List<MessageViewModel> messages) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void addMessage(MessageViewModel message) {

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

    }

    @Override
    public void handleMessageSendError() {

    }
}
