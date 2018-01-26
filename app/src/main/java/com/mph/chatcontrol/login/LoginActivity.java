package com.mph.chatcontrol.login;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.mph.chatcontrol.ChatcontrolApplication;
import com.mph.chatcontrol.base.BaseActivity;
import com.mph.chatcontrol.login.contract.LoginPresenter;
import com.mph.chatcontrol.login.contract.LoginView;
import com.mph.chatcontrol.login.contract.SharedPreferencesRepository;
import com.mph.chatcontrol.main.MainActivity;
import com.mph.chatcontrol.R;
import com.mph.chatcontrol.network.TokenServiceImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements LoginView, View.OnClickListener {
    public static final String TAG = "LoginActivity";

    @BindView(R.id.etEmail) EditText etEmail;
    @BindView(R.id.etPassword) EditText etPassword;
    @BindView(R.id.btnLogin) Button btnLogin;
    @BindView(R.id.pbLogin) ProgressBar progressBar;

    @BindView(R.id.cl_login) View mView;

    private LoginPresenter mPresenter;

    public static Intent newInstance(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        btnLogin.setOnClickListener(this);

        mPresenter = new LoginPresenterImpl(
                this,
                new LoginInteractorImpl(
                        getSharedPreferencesRepository(),
                        new FirebaseLoginServiceImpl(FirebaseAuth.getInstance()),
                        ((ChatcontrolApplication) getApplication()).getTokenService(this)));
    }

    public SharedPreferencesRepository getSharedPreferencesRepository() {
        return ((ChatcontrolApplication) getApplication()).getSharedPreferencesRepository(this);
    }

    @Override
    protected String getViewName() {
        return "Login";
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onClick(View view) {
        mPresenter.validateCredentials(etEmail.getText().toString(), etPassword.getText().toString());
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setEmailError() {
        etEmail.setError(getString(R.string.email_error));
    }

    @Override
    public void setPasswordError() {
        etPassword.setError(getString(R.string.password_error));
    }

    @Override
    public void showLoginError() {
        Snackbar.make(mView, getString(R.string.login_error), Snackbar.LENGTH_LONG).show();
    }

    @Override public void navigateToHome() {
        startActivity(MainActivity.newInstance(this));
        finish();
    }
}
