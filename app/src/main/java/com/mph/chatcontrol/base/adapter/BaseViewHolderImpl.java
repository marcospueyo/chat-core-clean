package com.mph.chatcontrol.base.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mph.chatcontrol.base.BaseViewHolder;
import com.mph.chatcontrol.base.BaseViewModel;
import com.mph.chatcontrol.base.presenter.BaseListPresenter;

import static com.google.common.base.Preconditions.checkNotNull;

/* Created by macmini on 20/07/2017. */

public abstract class BaseViewHolderImpl extends RecyclerView.ViewHolder implements BaseViewHolder {

    @NonNull protected final Context mContext;

    @NonNull BaseListPresenter mPresenter;

    public BaseViewHolderImpl(@NonNull Context context, @NonNull View itemView,
                              @NonNull BaseListPresenter baseListPresenter) {
        super(itemView);
        mContext = checkNotNull(context);
        mPresenter = checkNotNull(baseListPresenter);
    }

    @Override
    public void render(BaseViewModel viewModel) {
        onItemClick(viewModel);
    }


    public void onItemClick(final BaseViewModel viewModel) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onItemClicked(viewModel);
            }
        });
    }
}
