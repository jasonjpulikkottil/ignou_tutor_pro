package com.jdots.ignoupro.registration.service;

import rx.Observable;

/**
 * Created for chatting
 */

public interface RegistrationService {

    Observable<Boolean> getRegistration();

    void registerWithEmailAndPass(String email, String password);

}
