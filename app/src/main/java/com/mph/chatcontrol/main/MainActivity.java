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

import com.mph.chatcontrol.ChatcontrolApplication;
import com.mph.chatcontrol.R;
import com.mph.chatcontrol.chatlist.contract.ChatListPresenter;
import com.mph.chatcontrol.chatlist.ChatListPresenterImpl;
import com.mph.chatcontrol.chatlist.ChatlistFragment;
import com.mph.chatcontrol.chatlist.FindChatsInteractorImpl;
import com.mph.chatcontrol.chatlist.contract.FindChatsInteractor;
import com.mph.chatcontrol.chatlist.viewmodel.mapper.ChatViewModelToChatMapper;
import com.mph.chatcontrol.data.ChatInfoRepository;
import com.mph.chatcontrol.data.ChatInfoRepositoryImpl;
import com.mph.chatcontrol.data.ChatsRepository;
import com.mph.chatcontrol.data.ChatsRepositoryImpl;
import com.mph.chatcontrol.data.GuestRepository;
import com.mph.chatcontrol.data.GuestRepositoryImpl;
import com.mph.chatcontrol.events.LogoutEvent;
import com.mph.chatcontrol.events.OpenChatEvent;
import com.mph.chatcontrol.guestlist.FindGuestsInteractorImpl;
import com.mph.chatcontrol.guestlist.GuestListFragment;
import com.mph.chatcontrol.guestlist.GuestListPresenterImpl;
import com.mph.chatcontrol.guestlist.contract.FindGuestsInteractor;
import com.mph.chatcontrol.guestlist.contract.GuestListPresenter;
import com.mph.chatcontrol.guestlist.viewmodel.mapper.GuestViewModelToGuestMapper;
import com.mph.chatcontrol.login.LoginActivity;
import com.mph.chatcontrol.login.contract.SharedPreferencesRepository;
import com.mph.chatcontrol.network.ChatComparator;
import com.mph.chatcontrol.network.ChatcontrolService;
import com.mph.chatcontrol.network.FirebaseDatabaseDataImpl;
import com.mph.chatcontrol.network.GuestService;
import com.mph.chatcontrol.network.GuestServiceCloudImpl;
import com.mph.chatcontrol.network.RestGuestToGuestMapper;
import com.mph.chatcontrol.network.RestRoomToChatMapper;
import com.mph.chatcontrol.network.RoomFirebaseServiceImpl;
import com.mph.chatcontrol.network.RoomListenerRepositoryManagerImpl;
import com.mph.chatcontrol.network.RoomRealtimeService;
import com.mph.chatcontrol.network.RoomRestServiceImpl;
import com.mph.chatcontrol.network.RoomService;
import com.mph.chatcontrol.room.GetRoomInteractorImpl;
import com.mph.chatcontrol.room.RoomActivity;
import com.mph.chatcontrol.room.contract.GetRoomInteractor;
import com.mph.chatcontrol.settings.GetNotificationPreferenceInteractorImpl;
import com.mph.chatcontrol.settings.LogoutInteractorImpl;
import com.mph.chatcontrol.settings.SetNotificationPreferenceInteractorImpl;
import com.mph.chatcontrol.settings.SettingsFragment;
import com.mph.chatcontrol.settings.SettingsPresenterImpl;
import com.mph.chatcontrol.settings.contract.GetNotificationPreferenceInteractor;
import com.mph.chatcontrol.settings.contract.LogoutInteractor;
import com.mph.chatcontrol.settings.contract.SetNotificationPreferenceInteractor;
import com.mph.chatcontrol.settings.contract.SettingsPresenter;
import com.mph.chatcontrol.utils.CCUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.requery.Persistable;
import io.requery.sql.EntityDataStore;

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

    private ChatViewModelToChatMapper mChatViewModelToChatMapper;
    private RestRoomToChatMapper mRestRoomToChatMapper;
    private GuestViewModelToGuestMapper mGuestViewModelToGuestMapper;

    private ChatComparator mChatComparator;


    private FindChatsInteractor mFindChatsInteractor;
    private FindGuestsInteractor mFindGuestsInteractor;
    private GetRoomInteractor mGetRoomInteractor;
    private LogoutInteractor mLogoutInteractor;
    private SetNotificationPreferenceInteractor mSetNotificationPreferenceInteractor;
    private GetNotificationPreferenceInteractor mGetNotificationPreferenceInteractor;

    private ChatsRepository mChatsRepository;
    private ChatInfoRepository mChatInfoRepository;
    private GuestRepository mGuestRepository;


    private RoomService mRoomService;
    private RoomRealtimeService mRoomRealtimeService;

    private RestGuestToGuestMapper mRestGuestToGuestMapper;
    private GuestService mGuestService;

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
        if (ab == null) throw new AssertionError();
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
        mActiveChatListPresenter = getActiveChatListPresenter();

        showFragment(activeChatListFragment);
    }

    @Override
    public void showArchivedChatView() {
        if (archivedChatListFragment == null) {
            archivedChatListFragment = ChatlistFragment.newInstance();
        }
        mArchivedChatListPresenter = getArchivedChatListPresenter();

        showFragment(archivedChatListFragment);
    }

    @Override
    public void showGuestlistView() {
        if (mGuestListFragment == null)
            mGuestListFragment = GuestListFragment.newInstance();

        mGuestListPresenter = getGuestListPresenter();

        showFragment(mGuestListFragment);
    }

    @Override
    public void showConfigView() {
        if (mSettingsFragment == null)
            mSettingsFragment = SettingsFragment.newInstance();

        mSettingsPresenter = getSettingsPresenter();
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

    private EntityDataStore<Persistable> getDataStore() {
        return ((ChatcontrolApplication) getApplication()).getData();
    }

    public ChatListPresenter getActiveChatListPresenter() {
        if (mActiveChatListPresenter == null) {
            mActiveChatListPresenter = new ChatListPresenterImpl(
                    activeChatListFragment,
                    getChatViewModelToChatMapper(),
                    getChatComparator(),
                    getFindChatsInteractor(),
                    true /* should load active chats */);
        }
        return mActiveChatListPresenter;
    }

    public ChatListPresenter getArchivedChatListPresenter() {
        if (mArchivedChatListPresenter == null) {
            mArchivedChatListPresenter = new ChatListPresenterImpl(
                    archivedChatListFragment,
                    getChatViewModelToChatMapper(),
                    getChatComparator(),
                    getFindChatsInteractor(),
                    false /* should NOT load active chats */);
        }
        return mArchivedChatListPresenter;
    }

    public GuestListPresenter getGuestListPresenter() {
        if (mGuestListPresenter == null) {
            mGuestListPresenter = new GuestListPresenterImpl(
                    mGuestListFragment,
                    getGuestViewModelToGuestMapper(),
                    getChatViewModelToChatMapper(),
                    getFindGuestsInteractor(),
                    getGetRoomInteractor()
            );
        }

        return mGuestListPresenter;
    }

    public SettingsPresenter getSettingsPresenter() {
        if (mSettingsPresenter == null) {
            mSettingsPresenter = new SettingsPresenterImpl(
                    mSettingsFragment,
                    getLogoutInteractor(),
                    getSetNotificationPreferenceInteractor(),
                    getGetNotificationPreferenceInteractor());
        }
        return mSettingsPresenter;
    }

    public ChatComparator getChatComparator() {
        if (mChatComparator == null) {
            mChatComparator = new ChatComparator();
        }
        return mChatComparator;
    }

    public ChatViewModelToChatMapper getChatViewModelToChatMapper() {
        if (mChatViewModelToChatMapper == null) {
            mChatViewModelToChatMapper = new ChatViewModelToChatMapper();
        }
        return mChatViewModelToChatMapper;
    }

    public RestRoomToChatMapper getRestRoomToChatMapper() {
        if (mRestRoomToChatMapper == null) {
            mRestRoomToChatMapper = new RestRoomToChatMapper();
        }
        return mRestRoomToChatMapper;
    }

    public GuestViewModelToGuestMapper getGuestViewModelToGuestMapper() {
        if (mGuestViewModelToGuestMapper == null) {
            mGuestViewModelToGuestMapper = new GuestViewModelToGuestMapper();
        }
        return mGuestViewModelToGuestMapper;
    }

    public FindChatsInteractor getFindChatsInteractor() {
        if (mFindChatsInteractor == null) {
            mFindChatsInteractor = new FindChatsInteractorImpl(
                    getChatsRepository(),
                    getChatInfoRepository());
        }
        return mFindChatsInteractor;
    }

    public FindGuestsInteractor getFindGuestsInteractor() {
        if (mFindGuestsInteractor == null) {
            mFindGuestsInteractor = new FindGuestsInteractorImpl(getGuestRepository());
        }
        return mFindGuestsInteractor;
    }

    public GetRoomInteractor getGetRoomInteractor() {
        if (mGetRoomInteractor == null) {
            mGetRoomInteractor = new GetRoomInteractorImpl(
                    getChatsRepository(),
                    getChatInfoRepository());
        }
        return mGetRoomInteractor;
    }

    public LogoutInteractor getLogoutInteractor() {
        if (mLogoutInteractor == null) {
            mLogoutInteractor = new LogoutInteractorImpl();
        }
        return mLogoutInteractor;
    }

    public SetNotificationPreferenceInteractor getSetNotificationPreferenceInteractor() {
        if (mSetNotificationPreferenceInteractor == null) {
            mSetNotificationPreferenceInteractor = new SetNotificationPreferenceInteractorImpl();
        }
        return mSetNotificationPreferenceInteractor;
    }

    public GetNotificationPreferenceInteractor getGetNotificationPreferenceInteractor() {
        if (mGetNotificationPreferenceInteractor == null) {
            mGetNotificationPreferenceInteractor = new GetNotificationPreferenceInteractorImpl();
        }
        return mGetNotificationPreferenceInteractor;
    }

    public ChatsRepository getChatsRepository() {
        if (mChatsRepository == null) {
            mChatsRepository = new ChatsRepositoryImpl(
                    getSharedPreferencesRepository(),
                    getDataStore(),
                    getRoomService(),
                    getRoomRealtimeService(),
                    getRestRoomToChatMapper());
        }
        return mChatsRepository;
    }

    public ChatInfoRepository getChatInfoRepository() {
        if (mChatInfoRepository == null) {
            mChatInfoRepository = new ChatInfoRepositoryImpl(getDataStore());
        }
        return mChatInfoRepository;
    }

    public GuestRepository getGuestRepository() {
        if (mGuestRepository == null) {
            mGuestRepository = new GuestRepositoryImpl(
                    getSharedPreferencesRepository(),
                    getDataStore(),
                    getGuestService(),
                    getRestGuestToGuestMapper()
            );
        }
        return mGuestRepository;
    }

    public SharedPreferencesRepository getSharedPreferencesRepository() {
        return ((ChatcontrolApplication) getApplication()).getSharedPreferencesRepository(this);
    }

    public RoomService getRoomService() {
        if (mRoomService == null) {
            mRoomService = new RoomRestServiceImpl(getService(), getSharedPreferencesRepository());
        }
        return mRoomService;
    }

    public RoomRealtimeService getRoomRealtimeService() {
        if (mRoomRealtimeService == null) {
            mRoomRealtimeService = new RoomFirebaseServiceImpl(
                    new FirebaseDatabaseDataImpl(
                            ((ChatcontrolApplication) getApplication())
                                    .getFirebaseRootDatabaseReference()),
                    new RoomListenerRepositoryManagerImpl());
        }
        return mRoomRealtimeService;
    }

    public RestGuestToGuestMapper getRestGuestToGuestMapper() {
        if (mRestGuestToGuestMapper == null) {
            mRestGuestToGuestMapper = new RestGuestToGuestMapper();
        }
        return mRestGuestToGuestMapper;
    }

    public GuestService getGuestService() {
        if (mGuestService == null) {
            mGuestService = new GuestServiceCloudImpl(getService(),
                    getSharedPreferencesRepository());
        }
        return mGuestService;
    }

    public ChatcontrolService getService() {
        return ((ChatcontrolApplication) getApplication()).getService();
    }
}
