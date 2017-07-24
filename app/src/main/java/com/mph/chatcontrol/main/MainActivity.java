package com.mph.chatcontrol.main;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.mph.chatcontrol.R;
import com.mph.chatcontrol.chatlist.contract.ChatListPresenter;
import com.mph.chatcontrol.chatlist.ChatListPresenterImpl;
import com.mph.chatcontrol.chatlist.ChatlistFragment;
import com.mph.chatcontrol.chatlist.FindChatsInteractorImpl;
import com.mph.chatcontrol.chatlist.viewmodel.mapper.ChatViewModelToChatMapper;
import com.mph.chatcontrol.events.LogoutEvent;
import com.mph.chatcontrol.events.OpenChatEvent;
import com.mph.chatcontrol.guestlist.FindGuestsInteractorImpl;
import com.mph.chatcontrol.guestlist.GuestListFragment;
import com.mph.chatcontrol.guestlist.GuestListPresenterImpl;
import com.mph.chatcontrol.guestlist.contract.GuestListPresenter;
import com.mph.chatcontrol.guestlist.viewmodel.mapper.GuestViewModelToGuestMapper;
import com.mph.chatcontrol.login.LoginActivity;
import com.mph.chatcontrol.room.RoomActivity;
import com.mph.chatcontrol.settings.GetNotificationPreferenceInteractorImpl;
import com.mph.chatcontrol.settings.LogoutInteractorImpl;
import com.mph.chatcontrol.settings.SetNotificationPreferenceInteractorImpl;
import com.mph.chatcontrol.settings.SettingsFragment;
import com.mph.chatcontrol.settings.SettingsPresenterImpl;
import com.mph.chatcontrol.settings.contract.SettingsPresenter;
import com.mph.chatcontrol.utils.CCUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener, MainView {
    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.bottom_nav_view) BottomNavigationView mBottomNavigationView;
    @BindView(R.id.content_frame) FrameLayout mFrameLayout;
    private Toolbar mToolbar;
    private ImageButton ibRefresh;
    private ImageButton ibSearch;
    private MainPresenter mPresenter;

    // TODO: 17/07/2017 Add fragments with tags using fragment manager. Check whether they exist
    // https://stackoverflow.com/questions/9294603/get-currently-displayed-fragment
    private ChatlistFragment activeChatListFragment;
    private ChatlistFragment archivedChatListFragment;
    private GuestListFragment mGuestListFragment;
    private SettingsFragment mSettingsFragment;

    private ChatListPresenter mActiveChatListPresenter;
    private ChatListPresenter mArchivedChatListPresenter;
    private GuestListPresenter mGuestListPresenter;
    private SettingsPresenter mSettingsPresenter;

    public static Intent newInstance(Context context) {
        return new Intent(context, MainActivity.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupToolbar();

        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        mPresenter = new MainPresenterImpl(this);
    }

    private void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowHomeEnabled(false);
        ab.setDisplayHomeAsUpEnabled(false);
        ab.setDisplayShowCustomEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        setupToolbarButtons();
    }

    private void setupToolbarButtons() {
        ibRefresh = (ImageButton) mToolbar.findViewById(R.id.ib_refresh);
        ibSearch = (ImageButton) mToolbar.findViewById(R.id.ib_search);

        ibRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: refresh");
            }
        });

        ibSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: search");
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int order = 0;
        switch (item.getItemId()) {
            case R.id.action_active:
                order = 0;
                break;
            case R.id.action_archived:
                order = 1;
                break;
            case R.id.action_guests:
                order = 2;
                break;
            case R.id.action_config:
                order = 3;
                break;
        }
        mPresenter.onMenuOptionSelected(order);
        return true;
    }

    @Override
    public void showActiveChatView() {
        if (activeChatListFragment == null) {
            activeChatListFragment = ChatlistFragment.newInstance();
        }
        mActiveChatListPresenter = new ChatListPresenterImpl(
                activeChatListFragment,
                new ChatViewModelToChatMapper(),
                new FindChatsInteractorImpl(),
                true /* should load active chats */);

        showFragment(activeChatListFragment);
    }

    @Override
    public void showArchivedChatView() {
        if (archivedChatListFragment == null) {
            archivedChatListFragment = ChatlistFragment.newInstance();
        }
        mArchivedChatListPresenter = new ChatListPresenterImpl(
                archivedChatListFragment,
                new ChatViewModelToChatMapper(),
                new FindChatsInteractorImpl(),
                false /* should NOT load active chats */);

        showFragment(archivedChatListFragment);
    }

    @Override
    public void showGuestlistView() {
        if (mGuestListFragment == null)
            mGuestListFragment = GuestListFragment.newInstance();

        mGuestListPresenter = new GuestListPresenterImpl(
                mGuestListFragment,
                new GuestViewModelToGuestMapper(),
                new FindGuestsInteractorImpl());

        showFragment(mGuestListFragment);
    }

    @Override
    public void showConfigView() {
        if (mSettingsFragment == null)
            mSettingsFragment = SettingsFragment.newInstance();

        mSettingsPresenter = new SettingsPresenterImpl(
                mSettingsFragment,
                new LogoutInteractorImpl(),
                new SetNotificationPreferenceInteractorImpl(),
                new GetNotificationPreferenceInteractorImpl());
        showFragment(mSettingsFragment);
    }

    private void showFragment(Fragment fragment) {
        CCUtils.addFragmentToActivity(getFragmentManager(), fragment, mFrameLayout.getId());
    }

    @Override
    public void showMenuError() {

    }

    @Override
    public void navigateToLogin() {
        startActivity(LoginActivity.newInstance(getApplicationContext()));
        finish();
    }

    @Override
    public void showRoom(String roomID) {
        startActivity(RoomActivity.getIntent(getApplicationContext(), roomID));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogoutEvent(LogoutEvent event) {
        mPresenter.onLogout();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOpenChatEvent(OpenChatEvent event) {
        mPresenter.onOpenChat(event.chatID());
    }
}
