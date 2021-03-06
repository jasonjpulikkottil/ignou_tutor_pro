package com.jdots.ignoupro.conversation_list.database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jdots.ignoupro.Constants;
import com.jdots.ignoupro.rx.FirebaseObservableListeners;
import com.jdots.ignoupro.user.data_model.User;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created for chatting on 29/07/16.
 */

public class FirebaseConversationListDatabase implements ConversationListDatabase {

    private final DatabaseReference firebaseDatabase;
    private final FirebaseObservableListeners firebaseObservableListeners;

    public FirebaseConversationListDatabase(FirebaseDatabase firebaseDatabase, FirebaseObservableListeners firebaseObservableListeners) {
        this.firebaseDatabase = firebaseDatabase.getReference();
        this.firebaseObservableListeners = firebaseObservableListeners;
    }

    private static Func1<DataSnapshot, User> asUser() {
        return new Func1<DataSnapshot, User>() {
            @Override
            public User call(DataSnapshot dataSnapshot) {
                return dataSnapshot.getValue(User.class);
            }
        };
    }

    @Override
    public Observable<List<String>> observeConversationsFor(User user) {
        return firebaseObservableListeners.listenToValueEvents(firebaseDatabase
            .child(Constants.FIREBASE_CHAT).child(user.getUid()), getConversations());
    }

    private Func1<DataSnapshot, List<String>> getConversations() {
        return new Func1<DataSnapshot, List<String>>() {
            @Override
            public List<String> call(DataSnapshot dataSnapshot) {
                final List<String> conversationsUserUid = new ArrayList<>();
                if (dataSnapshot.hasChildren()) {
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                    for (final DataSnapshot child : children) {
                        conversationsUserUid.add(child.getKey());
                    }
                }
                return conversationsUserUid;
            }
        };
    }

}
