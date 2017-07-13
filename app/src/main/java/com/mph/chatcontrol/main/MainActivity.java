package com.mph.chatcontrol.main;

import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.mph.chatcontrol.R;
import com.mph.chatcontrol.utils.CCUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener, MainView {
    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.bottom_nav_view) BottomNavigationView mBottomNavigationView;
    @BindView(R.id.content_frame) FrameLayout mFrameLayout;
    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        mPresenter = new MainPresenterImpl(this);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        mPresenter.onResume();
        super.onResume();
    }

    @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mPresenter.onMenuOptionSelected(item.getOrder());
        return true;
    }

    @Override public void showActiveChatView() {
//        CCUtils.startFragment(this, new Fragment(), mFrameLayout.getId());
    }

    @Override public void showArchivedChatView() {
//        CCUtils.startFragment(this, new Fragment(), mFrameLayout.getId());
    }

    @Override public void showGuestlistView() {
//        CCUtils.startFragment(this, new Fragment(), mFrameLayout.getId());
    }

    @Override public void showConfigView() {
//        CCUtils.startFragment(this, new Fragment(), mFrameLayout.getId());
    }

    @Override public void showProgress() {
    }

    @Override public void hideProgress() {

    }

    @Override
    public void showMenuError() {

    }
}
