package com.mph.chatcontrol.base;
/* Created by macmini on 19/07/2017. */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mph.chatcontrol.R;
import com.mph.chatcontrol.base.adapter.BaseListAdapter;
import com.mph.chatcontrol.base.presenter.BaseListPresenter;
import com.mph.chatcontrol.base.presenter.BaseListView;
import com.mph.chatcontrol.widget.DividerItemDecoration;

import butterknife.BindView;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class BaseListFragment extends BaseFragment implements BaseListView {

    @BindView(R.id.rv_elements) protected RecyclerView mListView;
    @BindView(R.id.ll_progress) View mProgressView;
    @BindView(R.id.ll_content) View mContentView;

    protected BaseListPresenter mPresenter;
    protected BaseListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.init(inflater, R.layout.fragment_list, container);
        initializeRecyclerView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    protected void setAdapter(BaseListAdapter adapter) {
        mAdapter = adapter;
        mListView.setAdapter(mAdapter);
    }

    private void initializeRecyclerView() {
        mListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mListView.addItemDecoration(
                new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
        mListView.setHasFixedSize(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void setPresenter(BaseListPresenter presenter) {
        mPresenter = checkNotNull(presenter);
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
    public void showLoadError() {
        Snackbar.make(mContentView, R.string.chat_load_error, Snackbar.LENGTH_LONG).show();
    }
}
