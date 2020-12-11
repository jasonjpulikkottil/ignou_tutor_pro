package com.jdots.ignoupro.user.view;

import com.jdots.ignoupro.user.data_model.User;
import com.jdots.ignoupro.user.data_model.Users;

/**
 * Created for chatting on 01/09/16.
 */

public interface UsersDisplayer {

    void display(Users users);

    void attach(UserInteractionListener userInteractionListener);

    void detach(UserInteractionListener userInteractionListener);

    void filter(String text);

    interface UserInteractionListener {

        void onUserSelected(User user);

    }

}
