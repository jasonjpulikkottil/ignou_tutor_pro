package com.jdots.ignoupro.main.database;

import com.jdots.ignoupro.user.data_model.User;

import rx.Observable;

/**
 * Created for chatting on 17/08/16.
 */

public interface CloudMessagingDatabase {

    Observable<String> readToken(User user);

    void setToken(User user);

    void enableToken(String userId);

    void disableToken(String userId);

}
