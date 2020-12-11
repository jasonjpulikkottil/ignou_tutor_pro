package com.jdots.ignoupro.global.view;

import com.jdots.ignoupro.global.data_model.Chat;
import com.jdots.ignoupro.global.data_model.Message;
import com.jdots.ignoupro.user.data_model.User;
import com.jdots.ignoupro.user.data_model.Users;

/**
 * Created for chatting on 08/08/16.
 */

// TODO open conversation on click
public interface GlobalDisplayer {

    void displayOldMessages(Chat chat, Users users, User user);

    void display(Chat chat, Users users, User user);

    void addToDisplay(Message message, User sender, User user);

    void attach(GlobalActionListener globalActionListener);

    void detach(GlobalActionListener globalActionListener);

    void enableInteraction();

    void disableInteraction();

    interface GlobalActionListener {

        void onPullMessages();

        void onUpPressed();

        void onMessageLengthChanged(int messageLength);

        void onSubmitMessage(String message);

    }

    interface GlobalMessageListener {

        void onUserSelected();

    }

}
