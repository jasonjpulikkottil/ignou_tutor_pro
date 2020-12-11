package com.jdots.ignoupro.user.database;

import com.jdots.ignoupro.user.data_model.User;
import com.jdots.ignoupro.user.data_model.Users;

import rx.Observable;

/**
 * Created for chatting on 27/07/16.
 */

public interface UserDatabase {

    Observable<Users> observeUsers();

    Observable<User> readUserFrom(String userId);

    Observable<Users> singleObserveUsers();

    Observable<User> observeUser(String userId);

    Observable<Boolean>  initUserLastSeen();

    void setUserLastSeen(String userId);

    void setUserName(String userId, String name);

    void setUserImage(String userId, String image);

}
