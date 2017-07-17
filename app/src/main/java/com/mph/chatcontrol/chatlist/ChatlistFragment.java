package com.mph.chatcontrol.chatlist;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mph.chatcontrol.R;
import com.mph.chatcontrol.base.BaseFragment;
import com.mph.chatcontrol.data.Chat;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

public class ChatlistFragment extends BaseFragment implements ChatListView {
    @BindView(R.id.rv_elements) RecyclerView mListView;
    @BindView(R.id.ll_progress) View mProgressView;
    @BindView(R.id.ll_content) View mContentView;

    private ChatListPresenter mPresenter;

    public static ChatlistFragment newInstance() {
        return new ChatlistFragment();
    }

    public ChatlistFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void setPresenter(@NonNull ChatListPresenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.inflate(inflater, R.layout.fragment_chatlist, container);
    }

    @Override
    public void showProgress() {
        mProgressView.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        mProgressView.setVisibility(View.GONE);
        mListView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setChats(List<Chat> chats) {

    }

    @Override
    public void showLoadError() {
        Snackbar.make(mContentView, R.string.chat_load_error, Snackbar.LENGTH_LONG).show();
    }
}
