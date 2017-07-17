package com.mph.chatcontrol.main;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.mph.chatcontrol.R;
import com.mph.chatcontrol.chatlist.ChatListPresenter;
import com.mph.chatcontrol.chatlist.ChatListPresenterImpl;
import com.mph.chatcontrol.chatlist.ChatlistFragment;
import com.mph.chatcontrol.chatlist.FindChatsInteractorImpl;
import com.mph.chatcontrol.chatlist.TestBFragment;
import com.mph.chatcontrol.chatlist.TestCFragment;
import com.mph.chatcontrol.utils.CCUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener, MainView {
    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.bottom_nav_view) BottomNavigationView mBottomNavigationView;
    @BindView(R.id.content_frame) FrameLayout mFrameLayout;
    private MainPresenter mPresenter;

    // TODO: 17/07/2017 Add fragments with tags using fragment manager. Check whether they exist
    private ChatlistFragment activeChatListFragment, fragmentD;
    private ChatListPresenter mActiveChatListPresenter;
    private TestBFragment fragmentB;
    private TestCFragment fragmentC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        mPresenter = new MainPresenterImpl(this);
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
        mActiveChatListPresenter = new ChatListPresenterImpl(activeChatListFragment, new FindChatsInteractorImpl());
        CCUtils.addFragmentToActivity(getFragmentManager(), activeChatListFragment, mFrameLayout.getId());
    }

    @Override
    public void showArchivedChatView() {
        Log.d(TAG, "showArchivedChatView: ");
        if (fragmentB == null) {
            fragmentB = new TestBFragment();
        }
        CCUtils.addFragmentToActivity(getFragmentManager(), fragmentB, mFrameLayout.getId());
    }

    @Override
    public void showGuestlistView() {
        Log.d(TAG, "showGuestlistView: ");
        if (fragmentC == null) {
            fragmentC = new TestCFragment();
        }
        CCUtils.addFragmentToActivity(getFragmentManager(), fragmentC, mFrameLayout.getId());
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
