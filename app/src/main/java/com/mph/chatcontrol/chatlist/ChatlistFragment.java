package com.mph.chatcontrol.chatlist;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mph.chatcontrol.R;
import com.mph.chatcontrol.base.BaseFragment;
import com.mph.chatcontrol.chatlist.adapter.ChatsAdapter;
import com.mph.chatcontrol.chatlist.contract.ChatListPresenter;
import com.mph.chatcontrol.chatlist.contract.ChatListView;
import com.mph.chatcontrol.chatlist.viewmodel.ChatViewModel;

import java.util.List;

import butterknife.BindView;

import static com.google.common.base.Preconditions.checkNotNull;

public class ChatlistFragment extends BaseFragment implements ChatListView {
    @BindView(R.id.rv_elements) RecyclerView mListView;
    @BindView(R.id.ll_progress) View mProgressView;
    @BindView(R.id.ll_content) View mContentView;

    private ChatListPresenter mPresenter;
    private ChatsAdapter mAdapter;

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
        View view = super.inflate(inflater, R.layout.fragment_chatlist, container);
        initializeAdapter();
        initializeRecyclerView();
        return view;
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
    public void setChats(List<ChatViewModel> chats) {
        mAdapter.addAll(chats);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoadError() {
        Snackbar.make(mContentView, R.string.chat_load_error, Snackbar.LENGTH_LONG).show();
    }

    private void initializeAdapter() {
        mAdapter = new ChatsAdapter(mPresenter);
    }

    private void initializeRecyclerView() {
        mListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mListView.setHasFixedSize(true);
        mListView.setAdapter(mAdapter);
    }
}
