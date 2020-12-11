package com.jdots.ignoupro.global.service;

import com.jdots.ignoupro.database.DatabaseResult;
import com.jdots.ignoupro.global.data_model.Chat;
import com.jdots.ignoupro.global.data_model.Message;
import com.jdots.ignoupro.global.database.GlobalDatabase;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created for chatting on 08/08/16.
 */

public class PersistedGlobalService implements GlobalService {

    private final GlobalDatabase globalDatabase;

    public PersistedGlobalService(GlobalDatabase globalDatabase) {
        this.globalDatabase = globalDatabase;
    }

    private static Func1<Chat, DatabaseResult<Chat>> asDatabaseResult() {
        return new Func1<Chat, DatabaseResult<Chat>>() {
            @Override
            public DatabaseResult<Chat> call(Chat chat) {
                return new DatabaseResult<>(chat);
            }
        };
    }

    @Override
    public Observable<Chat> getOldMessages(String key) {
        return globalDatabase.observeOldMessages(key);
    }

    @Override
    public Observable<Message> getNewMessages(String key) {
        return globalDatabase.observeNewMessages(key);
    }

    @Override
    public Observable<DatabaseResult<Chat>> getChat() {
        return globalDatabase.observeChat()
                .map(asDatabaseResult())
                .onErrorReturn(DatabaseResult.errorAsDatabaseResult());
    }

    @Override
    public void sendMessage(Message message) {
        globalDatabase.sendMessage(message);
    }

}
