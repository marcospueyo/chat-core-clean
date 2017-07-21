package com.mph.chatcontrol.main;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.mph.chatcontrol.R;
import com.mph.chatcontrol.chatlist.contract.ChatListPresenter;
import com.mph.chatcontrol.chatlist.ChatListPresenterImpl;
import com.mph.chatcontrol.chatlist.ChatlistFragment;
import com.mph.chatcontrol.chatlist.FindChatsInteractorImpl;
import com.mph.chatcontrol.chatlist.TestCFragment;
import com.mph.chatcontrol.chatlist.viewmodel.mapper.ChatViewModelToChatMapper;
import com.mph.chatcontrol.guestlist.FindGuestsInteractorImpl;
import com.mph.chatcontrol.guestlist.GuestListFragment;
import com.mph.chatcontrol.guestlist.GuestListPresenterImpl;
import com.mph.chatcontrol.guestlist.contract.GuestListPresenter;
import com.mph.chatcontrol.guestlist.viewmodel.mapper.GuestViewModelToGuestMapper;
import com.mph.chatcontrol.utils.CCUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener, MainView {
    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.bottom_nav_view) BottomNavigationView mBottomNavigationView;
    @BindView(R.id.content_frame) FrameLayout mFrameLayout;
    private Toolbar mToolbar;
    private MainPresenter mPresenter;

    // TODO: 17/07/2017 Add fragments with tags using fragment manager. Check whether they exist
    // https://stackoverflow.com/questions/9294603/get-currently-displayed-fragment
    private ChatlistFragment activeChatListFragment, fragmentD;
    private ChatlistFragment archivedChatListFragment;
    private GuestListFragment mGuestListFragment;

    private ChatListPresenter mActiveChatListPresenter;
    private ChatListPresenter mArchivedChatListPresenter;
    private GuestListPresenter mGuestListPresenter;

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
    /*    final ImageButton profileButton = (ImageButton) toolbar.findViewById(R.id.ico_perfil);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), EditProfileActivity.class);
                startActivity(i);
            }
        });

        final ImageButton alertButton = (ImageButton) toolbar.findViewById(R.id.ico_alerts);
        alertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), AlertsActivity.class);
                startActivity(i);
            }
        });
        */
    }


    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
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
        CCUtils.addFragmentToActivity(getFragmentManager(), activeChatListFragment,
                mFrameLayout.getId());
    }

    @Override
    public void showArchivedChatView() {
        Log.d(TAG, "showArchivedChatView: ");
        if (archivedChatListFragment == null) {
            archivedChatListFragment = ChatlistFragment.newInstance();
        }
        mArchivedChatListPresenter = new ChatListPresenterImpl(
                archivedChatListFragment,
                new ChatViewModelToChatMapper(),
                new FindChatsInteractorImpl(),
                false /* should NOT load active chats */);

        CCUtils.addFragmentToActivity(getFragmentManager(), archivedChatListFragment,
                mFrameLayout.getId());
    }

    @Override
    public void showGuestlistView() {
        Log.d(TAG, "showGuestlistView: ");
        if (mGuestListFragment == null)
            mGuestListFragment = GuestListFragment.newInstance();

        mGuestListPresenter = new GuestListPresenterImpl(
                mGuestListFragment,
                new GuestViewModelToGuestMapper(),
                new FindGuestsInteractorImpl());

        CCUtils.addFragmentToActivity(getFragmentManager(), mGuestListFragment, mFrameLayout.getId());
    }

    @Override
    public void showConfigView() {
        Log.d(TAG, "showConfigView: ");
        /*if (fragmentD == null) {
            fragmentD = new ChatlistFragment();
        }
        CCUtils.addFragmentToActivity(getFragmentManager(), fragmentD, mFrameLayout.getId());*/
    }

    @Override
    public void showMenuError() {

    }
}
