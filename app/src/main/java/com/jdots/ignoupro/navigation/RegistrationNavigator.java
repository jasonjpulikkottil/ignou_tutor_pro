package com.jdots.ignoupro.navigation;

/**
 * Created for chatting
 */

public interface RegistrationNavigator extends Navigator {

    void toLogin();

    void attach(RegistrationResultListener registrationResultListener);

    void detach(RegistrationResultListener registrationResultListener);

    interface RegistrationResultListener {

        void onRegistrationSuccess(String statusMessage);

        void onRegistrationFailed(String statusMessage);

    }
}
