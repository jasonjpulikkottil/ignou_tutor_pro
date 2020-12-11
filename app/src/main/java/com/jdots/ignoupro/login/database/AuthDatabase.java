package com.jdots.ignoupro.login.database;

import com.jdots.ignoupro.login.data_model.Authentication;

import rx.Observable;

/**
 * Created for chatting on 27/07/16.
 */

public interface AuthDatabase {

    Observable<Authentication> readAuthentication();

    Observable<Authentication> loginWithGoogle(String idToken);

    Observable<Authentication> loginWithEmailAndPass(String email, String password);

    void sendPasswordResetEmail(String email);

}
