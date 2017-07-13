package com.mph.chatcontrol.main;
/* Created by Marcos on 13/07/2017.*/

import com.mph.chatcontrol.main.utils.MenuOptions;

public class MainPresenterImpl implements MainPresenter {
    private MainView mainView;
    //private MainInteractor mainInteractor;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
    }

    @Override public void onMenuOptionSelected(Integer order) {
        if (mainView != null) {
            MenuOptions.Option option = MenuOptions.getOption(order);
            if (option == null)
                mainView.showMenuError();
            else {
                switch (option) {
                    case ACTIVE:
                        mainView.showActiveChatView();
                        break;
                    case ARCHIVED:
                        mainView.showArchivedChatView();
                        break;
                    case GUESTLIST:
                        mainView.showGuestlistView();
                        break;
                    case CONFIG:
                        mainView.showConfigView();
                }
            }
        }
    }

    @Override public void onResume() {

    }

    @Override public void onDestroy() {
        mainView = null;
    }
}
