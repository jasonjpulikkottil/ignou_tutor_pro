package com.jdots.ignoupro.main.service;

import com.jdots.ignoupro.user.data_model.User;

import rx.Observable;

/**
 * Created for chatting on 17/08/16.
 */

public interface CloudMessagingService {

    Observable<String> readToken(User user);

    void setToken(User user);

}
