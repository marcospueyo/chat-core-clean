package com.mph.chatcontrol.base.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mph.chatcontrol.base.BaseViewModel;
import com.mph.chatcontrol.base.presenter.BaseListPresenter;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/* Created by macmini on 20/07/2017. */

public abstract class BaseListAdapter<VH extends BaseViewHolderImpl> extends RecyclerView.Adapter<VH> {

    @NonNull protected final Context mContext;

    @NonNull protected final BaseListPresenter mPresenter;

    private int mResourceID;

    protected List<BaseViewModel> mItemList;

    public BaseListAdapter(@NonNull BaseListPresenter presenter, @NonNull Context context, int resID) {
        mPresenter = checkNotNull(presenter);
        mContext = checkNotNull(context);
        mItemList = new ArrayList<>();
        mResourceID = resID;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.render(mItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    protected View getInflatedItemView(ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return inflater.inflate(mResourceID, viewGroup, false);
    }

    public void updateItemList(List<BaseViewModel> collection) {
        mItemList.clear();
        mItemList.addAll(collection);
        notifyDataSetChanged();
    }

    public void addItem(BaseViewModel item) {
        mItemList.add(item);
        notifyItemInserted(mItemList.size() - 1);
    }
}
