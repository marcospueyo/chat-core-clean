package com.mph.chatcontrol.guestlist;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import com.mph.chatcontrol.base.BaseViewModel;
import com.mph.chatcontrol.chatlist.viewmodel.mapper.ChatViewModelToChatMapper;
import com.mph.chatcontrol.data.Chat;
import com.mph.chatcontrol.data.ChatInfo;
import com.mph.chatcontrol.data.Guest;
import com.mph.chatcontrol.events.RefreshGuestsEvent;
import com.mph.chatcontrol.events.SearchGuestsDisableEvent;
import com.mph.chatcontrol.events.SearchGuestsEvent;
import com.mph.chatcontrol.guestlist.contract.FindGuestsInteractor;
import com.mph.chatcontrol.guestlist.contract.GuestListPresenter;
import com.mph.chatcontrol.guestlist.contract.GuestListView;
import com.mph.chatcontrol.guestlist.viewmodel.GuestViewModel;
import com.mph.chatcontrol.guestlist.viewmodel.mapper.GuestViewModelToGuestMapper;
import com.mph.chatcontrol.room.contract.GetRoomInteractor;
import com.mph.chatcontrol.utils.StringComparator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
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
    private final StringComparator mStringComparator;

    @NonNull
    private final EventBus mEventBus;

    private boolean mFilterEnabled;

    private String mQueryFilter;

    private List<Guest> mGuests;

    public GuestListPresenterImpl(@NonNull GuestListView guestListView,
                                  @NonNull GuestViewModelToGuestMapper guestMapper,
                                  @NonNull ChatViewModelToChatMapper chatMapper,
                                  @NonNull FindGuestsInteractor findGuestsInteractor,
                                  @NonNull GetRoomInteractor getRoomInteractor,
                                  @NonNull EventBus eventBus,
                                  @NonNull StringComparator stringComparator) {
        mGuestMapper = checkNotNull(guestMapper);
        mChatMapper = chatMapper;
        mFindGuestsInteractor = checkNotNull(findGuestsInteractor);
        mGetRoomInteractor = checkNotNull(getRoomInteractor);
        mGuestListView = checkNotNull(guestListView);
        mEventBus = checkNotNull(eventBus);
        mStringComparator = checkNotNull(stringComparator);

        mGuestListView.setPresenter(this);

        mGuests = new ArrayList<>();
        mFilterEnabled = false;
    }

    @Override
    public void start() {
        mEventBus.register(this);
        loadGuests();
    }

    private void loadGuests() {
        if (cacheContainsGuests()) {
            showGuests();
        }
        else {
            mGuestListView.showProgress();
        }

        mFindGuestsInteractor.findGuests(this);
    }

    private boolean cacheContainsGuests() {
        return mGuests.size() > 0;
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
        saveFetchResults(guests);
        showGuests();
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
        enableFilter(event.query());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSearchGuestsDisableEvent(SearchGuestsDisableEvent event) {
        disableFilter();
    }

    private void saveFetchResults(List<Guest> guests) {
        mGuests.clear();
        mGuests.addAll(guests);
    }

    private void enableFilter(String queryFilter) {
        mFilterEnabled = true;
        mQueryFilter = queryFilter;
        showGuests();
    }

    private void disableFilter() {
        mFilterEnabled = false;
        mQueryFilter = null;
        showGuests();
    }

    private void showGuests() {
        if (mustFilter()) {
            showFilteredGuests(mQueryFilter);
        }
        else {
            showGuestList(mGuests);
        }

    }

    private void showFilteredGuests(String queryFilter) {
        List<Guest> list = new ArrayList<>();
        for (Guest guest : mGuests) {
            if (searchConditionIsMet(guest, queryFilter)) {
                list.add(guest);
            }
        }
        showGuestList(list);
    }
    private boolean searchConditionIsMet(Guest guest, String queryFilter) {
        return mStringComparator.stringsAreAlike(guest.getName(), queryFilter);
    }

    private void showGuestList(List<Guest> guestList) {
        mGuestListView.setItems(mGuestMapper.reverseMap(guestList));
    }

    private boolean mustFilter() {
        return mFilterEnabled && !TextUtils.isEmpty(mQueryFilter);
    }
}
