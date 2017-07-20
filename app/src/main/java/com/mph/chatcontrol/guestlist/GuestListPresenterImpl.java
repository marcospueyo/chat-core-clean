package com.mph.chatcontrol.guestlist;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mph.chatcontrol.base.BaseViewModel;
import com.mph.chatcontrol.data.Guest;
import com.mph.chatcontrol.guestlist.contract.FindGuestsInteractor;
import com.mph.chatcontrol.guestlist.contract.GuestListPresenter;
import com.mph.chatcontrol.guestlist.contract.GuestListView;
import com.mph.chatcontrol.guestlist.viewmodel.GuestViewModel;
import com.mph.chatcontrol.guestlist.viewmodel.mapper.GuestViewModelToGuestMapper;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/* Created by macmini on 20/07/2017. */

public class GuestListPresenterImpl implements GuestListPresenter,
        FindGuestsInteractor.OnFinishedListener {

    private static final String TAG = GuestListPresenterImpl.class.getSimpleName();

    @NonNull private final GuestListView mGuestListView;

    @NonNull private final FindGuestsInteractor mFindGuestsInteractor;

    @NonNull private GuestViewModelToGuestMapper mMapper;

    public GuestListPresenterImpl(@NonNull GuestListView guestListView,
                                  @NonNull GuestViewModelToGuestMapper mapper,
                                  @NonNull FindGuestsInteractor findGuestsInteractor) {
        mGuestListView = checkNotNull(guestListView);
        mMapper = checkNotNull(mapper);
        mFindGuestsInteractor = checkNotNull(findGuestsInteractor);
    }

    @Override
    public void start() {
        mGuestListView.showProgress();
        mFindGuestsInteractor.findGuests(this);
    }

    @Override
    public void onItemClicked(BaseViewModel item) {
        Log.d(TAG, "onItemClicked: " + item.toString());
    }

    @Override
    public void onFinished(List<Guest> guests) {
        List<GuestViewModel> guestViewModels = mMapper.reverseMap(guests);
        mGuestListView.setItems(guestViewModels);
        mGuestListView.hideProgress();
    }

    @Override
    public void onDataNotAvailable() {
        mGuestListView.hideProgress();
        mGuestListView.showLoadError();
    }
}
