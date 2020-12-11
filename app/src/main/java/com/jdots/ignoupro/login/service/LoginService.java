package com.jdots.ignoupro.login.service;


import com.jdots.ignoupro.login.data_model.Authentication;

import rx.Observable;

/**
 * Created for chatting on 27/07/16.
 */

public interface LoginService {

    Observable<Authentication> getAuthentication();

    void loginWithGoogle(String idToken);

    void loginWithEmailAndPass(String email, String password);

    void sendPasswordResetEmail(String email);

}
