package com.jdots.ignoupro.registration;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import com.jdots.ignoupro.BaseActivity;
import com.jdots.ignoupro.Dependencies;
import com.jdots.ignoupro.R;
import com.jdots.ignoupro.navigation.AndroidRegistrationNavigator;
import com.jdots.ignoupro.registration.presenter.RegistrationPresenter;
import com.jdots.ignoupro.registration.view.RegistrationDisplayer;

/**
 * Created for chatting
 */

public class RegistrationActivity extends BaseActivity {

    private RegistrationPresenter presenter;
    private AndroidRegistrationNavigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        RegistrationDisplayer registrationDisplayer = (RegistrationDisplayer) findViewById(R.id.registrationView);
        navigator = new AndroidRegistrationNavigator(this);
        presenter = new RegistrationPresenter(
                Dependencies.INSTANCE.getRegistrationService(),
                registrationDisplayer,
                navigator);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            super.onActivityResult(requestCode, resultCode, data);

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
