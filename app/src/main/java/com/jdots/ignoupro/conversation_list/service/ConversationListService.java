package com.jdots.ignoupro.conversation_list.service;

import com.jdots.ignoupro.conversation.data_model.Message;
import com.jdots.ignoupro.user.data_model.User;
import com.jdots.ignoupro.user.data_model.Users;

import java.util.List;

import rx.Observable;

/**
 * Created for chatting on 29/07/16.
 */

public interface ConversationListService {

    Observable<Message> getLastMessageFor(User self, User destination);

    Observable<List<String>> getConversationsFor(User user);

    Observable<Users> getUsers(List<String> usersId);

}
