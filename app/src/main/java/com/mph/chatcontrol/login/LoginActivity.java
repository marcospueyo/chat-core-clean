package com.mph.chatcontrol.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.mph.chatcontrol.MainActivity;
import com.mph.chatcontrol.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginView, View.OnClickListener {
    public static final String TAG = "LoginActivity";

    @BindView(R.id.etEmail) EditText etEmail;
    @BindView(R.id.etPassword) EditText etPassword;
    @BindView(R.id.btnLogin) Button btnLogin;
    @BindView(R.id.pbLogin) ProgressBar progressBar;

    private LoginPresenter mPresenter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        btnLogin.setOnClickListener(this);

        mPresenter = new LoginPresenterImpl(this);
    }

    @Override protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }
    @Override public void onClick(View view) {
        mPresenter.validateCredentials(etEmail.getText().toString(), etPassword.getText().toString());
    }

    @Override public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override public void setEmailError() {
        etEmail.setError(getString(R.string.email_error));
    }

    @Override public void setPasswordError() {
        etPassword.setError(getString(R.string.password_error));
    }

    @Override public void navigateToHome() {
        // TODO: 13/07/2017 Create static method to get the MainActivity intent already instantiated
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
