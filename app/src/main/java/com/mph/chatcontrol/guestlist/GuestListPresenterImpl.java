package com.mph.chatcontrol.guestlist;

import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;

import com.mph.chatcontrol.base.BaseViewModel;
import com.mph.chatcontrol.chatlist.viewmodel.mapper.ChatViewModelToChatMapper;
import com.mph.chatcontrol.data.Chat;
import com.mph.chatcontrol.data.ChatInfo;
import com.mph.chatcontrol.data.Guest;
import com.mph.chatcontrol.events.RefreshGuestsEvent;
import com.mph.chatcontrol.events.SearchGuestsEvent;
import com.mph.chatcontrol.guestlist.contract.FindGuestsInteractor;
import com.mph.chatcontrol.guestlist.contract.GuestListPresenter;
import com.mph.chatcontrol.guestlist.contract.GuestListView;
import com.mph.chatcontrol.guestlist.viewmodel.GuestViewModel;
import com.mph.chatcontrol.guestlist.viewmodel.mapper.GuestViewModelToGuestMapper;
import com.mph.chatcontrol.room.contract.GetRoomInteractor;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/* Created by macmini on 20/07/2017. */

public class GuestListPresenterImpl implements GuestListPresenter,
        FindGuestsInteractor.OnFinishedListener, GetRoomInteractor.OnFinishedListener {

    private static final String TAG = GuestListPresenterImpl.class.getSimpleName();

    @NonNull
    private final GuestListView mGuestListView;

    @NonNull
    private final FindGuestsInteractor mFindGuestsInteractor;

    @NonNull
    private final GetRoomInteractor mGetRoomInteractor;

    @NonNull
    private GuestViewModelToGuestMapper mGuestMapper;

    @NonNull
    private ChatViewModelToChatMapper mChatMapper;

    @NonNull
    private final EventBus mEventBus;

    public GuestListPresenterImpl(@NonNull GuestListView guestListView,
                                  @NonNull GuestViewModelToGuestMapper guestMapper,
                                  @NonNull ChatViewModelToChatMapper chatMapper,
                                  @NonNull FindGuestsInteractor findGuestsInteractor,
                                  @NonNull GetRoomInteractor getRoomInteractor,
                                  @NonNull EventBus eventBus) {
        mGuestMapper = checkNotNull(guestMapper);
        mChatMapper = chatMapper;
        mFindGuestsInteractor = checkNotNull(findGuestsInteractor);
        mGetRoomInteractor = checkNotNull(getRoomInteractor);
        mGuestListView = checkNotNull(guestListView);
        mEventBus = checkNotNull(eventBus);
        mGuestListView.setPresenter(this);
    }

    @Override
    public void start() {
        mEventBus.register(this);
        loadGuests();
    }

    private void loadGuests() {
        mGuestListView.showProgress();
        mFindGuestsInteractor.findGuests(this);
    }

    @Override
    public void stop() {
        mEventBus.unregister(this);
    }

    @Override
    public void onItemClicked(BaseViewModel item) {
        Log.d(TAG, "onItemClicked: " + item.toString());
    }

    @Override
    public void onCallClicked(GuestViewModel guest) {
        Log.d(TAG, "onCallClicked: " + guest.toString());
        mGuestListView.callGuest(guest);
    }

    @Override
    public void onChatClicked(GuestViewModel guest) {
        Log.d(TAG, "onChatClicked: " + guest.toString());
        mGetRoomInteractor.execute(guest.relatedRoomId(), this);
    }

    @Override
    public void onFinished(List<Guest> guests) {
        List<GuestViewModel> guestViewModels = mGuestMapper.reverseMap(guests);
        mGuestListView.setItems(guestViewModels);
        mGuestListView.hideProgress();
    }

    @Override
    public void onDataNotAvailable() {
        mGuestListView.hideProgress();
        mGuestListView.showLoadError();
    }

    @Override
    public void onRoomLoaded(Pair<Chat, ChatInfo> bundle) {
        mGuestListView.openChat(bundle.first.getId());
    }

    @Override
    public void onRoomChanged(Pair<Chat, ChatInfo> bundle) {
        // Might be interesting to apply UI changes.
    }

    @Override
    public void onRoomLoadError() {
        mGuestListView.showRoomLoadError();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshGuestsEvent(RefreshGuestsEvent event) {
        loadGuests();
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onSearchGuestsEvent(SearchGuestsEvent event) {
        Log.d(TAG, "onSearchGuestsEvent: ");
    }
}
