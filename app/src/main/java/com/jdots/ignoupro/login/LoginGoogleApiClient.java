package com.jdots.ignoupro.login;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.jdots.ignoupro.R;

/**
 * Created for chatting on 27/07/16.
 */

public class LoginGoogleApiClient {

    private final AppCompatActivity activity;

    private GoogleApiClient apiClient;

    public LoginGoogleApiClient(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void setupGoogleApiClient() {
        String string = activity.getString(R.string.default_web_client_id);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(string)
                .requestEmail()
                .build();
        apiClient = new GoogleApiClient.Builder(activity)
                .enableAutoManage(
                        activity, new GoogleApiClient.OnConnectionFailedListener() {
                            @Override
                            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                                Log.d("FireChat", "Failed to connect to GMS");
                            }
                        })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    public Intent getSignInIntent() {
        return Auth.GoogleSignInApi.getSignInIntent(apiClient);
    }

    public GoogleSignInResult getSignInResultFromIntent(Intent data) {
        return Auth.GoogleSignInApi.getSignInResultFromIntent(data);
    }

}

