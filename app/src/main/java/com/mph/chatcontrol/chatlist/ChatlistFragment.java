package com.mph.chatcontrol.chatlist;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mph.chatcontrol.R;
import com.mph.chatcontrol.base.BaseListFragment;
import com.mph.chatcontrol.base.presenter.BaseListPresenter;
import com.mph.chatcontrol.chatlist.adapter.ChatsAdapter;
import com.mph.chatcontrol.chatlist.contract.ChatListPresenter;
import com.mph.chatcontrol.chatlist.contract.ChatListView;
import com.mph.chatcontrol.chatlist.viewmodel.ChatViewModel;
import com.mph.chatcontrol.events.OpenChatEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class ChatlistFragment extends BaseListFragment implements ChatListView {

    @SuppressWarnings("unused")
    private static final String TAG = ChatlistFragment.class.getSimpleName();

    public static ChatlistFragment newInstance() {
        return new ChatlistFragment();
    }

    public ChatlistFragment() {
        // Required empty public constructor
    }

    @Override
    public void setPresenter(@NonNull BaseListPresenter presenter) {
        if (!(presenter instanceof ChatListPresenter))
            throw new IllegalArgumentException();
        super.setPresenter(presenter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        Log.d(TAG, "onCreateView: fired");
        initializeAdapter();
        return view;
    }

    @Override
    public void setItems(List<ChatViewModel> chats) {
        mAdapter.updateItemList(chats);
    }

    @Override
    public void updateItem(ChatViewModel chat) {
        Log.d(TAG, "updateItem: fired");
        mAdapter.updateItem(chat);
    }

    @Override
    public void openChat(ChatViewModel chat) {
        EventBus.getDefault().post(OpenChatEvent.create(chat.id()));
    }

    @Override
    public void showUpdateError() {
        Snackbar.make(mContentView, R.string.chat_update_error, Snackbar.LENGTH_LONG).show();

    }

    private void initializeAdapter() {
        super.setAdapter(new ChatsAdapter((ChatListPresenter) mPresenter, mContext));
    }

}
