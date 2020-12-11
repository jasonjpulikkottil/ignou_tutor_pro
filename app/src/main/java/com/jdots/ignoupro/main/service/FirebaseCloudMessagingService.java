package com.jdots.ignoupro.main.service;

import com.jdots.ignoupro.main.database.CloudMessagingDatabase;
import com.jdots.ignoupro.user.data_model.User;

import rx.Observable;

/**
 * Created for chatting on 17/08/16.
 */

public class FirebaseCloudMessagingService implements CloudMessagingService {

    private final CloudMessagingDatabase messagingDatabase;

    public FirebaseCloudMessagingService(CloudMessagingDatabase messagingDatabase) {
        this.messagingDatabase = messagingDatabase;
    }

    @Override
    public Observable<String> readToken(User user) {
        return messagingDatabase.readToken(user);
    }

    @Override
    public void setToken(User user) {
        messagingDatabase.setToken(user);
    }
}
