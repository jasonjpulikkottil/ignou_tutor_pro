package com.jdots.ignoupro.conversation.service;

import com.jdots.ignoupro.conversation.data_model.Chat;
import com.jdots.ignoupro.conversation.data_model.Message;
import com.jdots.ignoupro.database.DatabaseResult;

import rx.Observable;

/**
 * Created for chatting on 29/07/16.
 */

public interface ConversationService {

    Observable<Chat> getOldMessages(String self, String destination, String key);

    Observable<Message> getNewMessages(String self, String destination, String key);

    Observable<DatabaseResult<Chat>> getChat(String self, String destination);

    void sendMessage(String user, Message message);

    Observable<Boolean> getTyping(String self, String destination);

    void setTyping(String self, String destination, Boolean value);

}
