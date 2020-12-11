package com.jdots.ignoupro.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import com.jdots.ignoupro.BaseActivity;
import com.jdots.ignoupro.Dependencies;
import com.jdots.ignoupro.R;
import com.jdots.ignoupro.login.presenter.LoginPresenter;
import com.jdots.ignoupro.login.view.LoginDisplayer;
import com.jdots.ignoupro.navigation.AndroidLoginNavigator;
import com.jdots.ignoupro.navigation.AndroidNavigator;

/**
 * Created for chatting on 27/07/16.
 */

public class LoginActivity extends BaseActivity {

    private LoginPresenter presenter;
    private AndroidLoginNavigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        LoginDisplayer loginDisplayer = (LoginDisplayer) findViewById(R.id.loginView);
        LoginGoogleApiClient loginGoogleApiClient = new LoginGoogleApiClient(this);
        loginGoogleApiClient.setupGoogleApiClient();
        navigator = new AndroidLoginNavigator(this, loginGoogleApiClient, new AndroidNavigator(this));
        presenter = new LoginPresenter(
                Dependencies.INSTANCE.getLoginService(),
                loginDisplayer,
                navigator);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!navigator.onActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.startPresenting();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stopPresenting();
    }

}

