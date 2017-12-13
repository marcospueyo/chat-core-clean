package com.mph.chatcontrol.main;
/* Created by Marcos on 13/07/2017.*/

import android.support.annotation.NonNull;

import com.mph.chatcontrol.utils.MenuOptions;
import com.mph.chatcontrol.utils.Router;

import static com.google.common.base.Preconditions.checkNotNull;

public class MainPresenterImpl implements MainPresenter {

    @NonNull
    private final MainView mView;

    @NonNull
    private final Router mRouter;

    //private MainInteractor mainInteractor;

    public MainPresenterImpl(@NonNull MainView mainView, @NonNull Router router) {
        mView = checkNotNull(mainView);
        mRouter = checkNotNull(router);
    }

    @Override
    public void onMenuOptionSelected(Integer order) {
        MenuOptions.Option option = MenuOptions.getOption(order);
        if (option == null)
            mView.showMenuError();
        else {
            switch (option) {
                case ACTIVE:
                    mView.showActiveChatView();
                    break;
                case ARCHIVED:
                    mView.showArchivedChatView();
                    break;
                case GUESTLIST:
                    mView.showGuestlistView();
                    break;
                case CONFIG:
                    mView.showConfigView();
            }
        }
    }

    @Override
    public void onRefresh(Integer order) {
        MenuOptions.Option option = MenuOptions.getOption(order);
        if (option == null)
            mView.showMenuError();
        else {
            switch (option) {
                case ACTIVE:
                    mRouter.refreshActiveRooms();
                    break;
                case ARCHIVED:
                    mRouter.refreshArchivedRooms();
                    break;
                case GUESTLIST:
                    mRouter.refreshGuests();
                    break;
                case CONFIG:
                    break;
            }
        }
    }

    @Override
    public void onSearch(Integer order, String query) {
        MenuOptions.Option option = MenuOptions.getOption(order);
        if (option == null)
            mView.showMenuError();
        else {
            switch (option) {
                case ACTIVE:
                    mRouter.showActiveRoomSearch(query);
                    break;
                case ARCHIVED:
                    mRouter.showArchivedRoomSearch(query);
                    break;
                case GUESTLIST:
                    mRouter.showGuestSearch(query);
                    break;
                case CONFIG:
                    break;
            }
        }
    }

    @Override
    public void onSearchDisabled(Integer order) {
        MenuOptions.Option option = MenuOptions.getOption(order);
        if (option == null)
            mView.showMenuError();
        else {
            switch (option) {
                case ACTIVE:
                    mRouter.hideActiveRoomSearch();
                    break;
                case ARCHIVED:
                    mRouter.hideArchivedRoomSearch();
                    break;
                case GUESTLIST:
                    mRouter.hideGuestSearch();
                    break;
                case CONFIG:
                    break;
            }
        }
    }

    @Override
    public void onStart() {
        mView.showActiveChatView();
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void onLogout() {
        mView.navigateToLogin();
    }

    @Override
    public void onOpenChat(String chatID) {
        mView.showRoom(chatID);
    }
}
