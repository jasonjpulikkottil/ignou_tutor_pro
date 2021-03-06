package com.jdots.ignoupro.conversation_list.presenter;

import android.os.Bundle;
import android.util.Pair;

import com.jdots.ignoupro.conversation.data_model.Message;
import com.jdots.ignoupro.conversation_list.data_model.Conversation;
import com.jdots.ignoupro.conversation_list.service.ConversationListService;
import com.jdots.ignoupro.conversation_list.view.ConversationListDisplayer;
import com.jdots.ignoupro.login.data_model.Authentication;
import com.jdots.ignoupro.login.service.LoginService;
import com.jdots.ignoupro.navigation.AndroidConversationsNavigator;
import com.jdots.ignoupro.user.data_model.User;
import com.jdots.ignoupro.user.service.UserService;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created for chatting on 29/07/16.
 */

public class ConversationListPresenter {

    private static final String SENDER = "sender";
    private static final String DESTINATION = "destination";

    private final ConversationListDisplayer conversationListDisplayer;
    private final ConversationListService conversationListService;
    private final AndroidConversationsNavigator navigator;
    private final LoginService loginService;
    private final UserService userService;

    private User self;
    private final ConversationListDisplayer.ConversationInteractionListener conversationInteractionListener = new ConversationListDisplayer.ConversationInteractionListener() {

        @Override
        public void onConversationSelected(Conversation conversation) {
            Bundle bundle = new Bundle();
            bundle.putString(SENDER, self.getUid());
            bundle.putString(DESTINATION,conversation.getUid());
            navigator.toSelectedConversation(bundle);
        }

    };

    public ConversationListPresenter(
            ConversationListDisplayer conversationListDisplayer,
            ConversationListService conversationListService,
            AndroidConversationsNavigator navigator,
            LoginService loginService,
            UserService userService) {
        this.conversationListDisplayer = conversationListDisplayer;
        this.conversationListService = conversationListService;
        this.navigator = navigator;
        this.loginService = loginService;
        this.userService = userService;
    }

    public void startPresenting() {
        conversationListDisplayer.attach(conversationInteractionListener);

        loginService.getAuthentication()
                .filter(successfullyAuthenticated())
                .doOnNext(getAuthentication())
                .flatMap(getConversations())
                .flatMap(getConversation())
                .forEach(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        userService.getUser(s)
                                .flatMap(getLastMessage(), asPair())
                                .subscribe(lastMessageSubscriber());
                    }
                });

    }

    public void stopPresenting() {
        conversationListDisplayer.detach(conversationInteractionListener);
    }

    private Func1<Authentication, Boolean> successfullyAuthenticated() {
        return new Func1<Authentication, Boolean>() {
            @Override
            public Boolean call(Authentication authentication) {
                return authentication.isSuccess();
            }
        };
    }

    private Action1<Authentication> getAuthentication() {
        return new Action1<Authentication>() {
            @Override
            public void call(Authentication authentication) {
                self = authentication.getUser();
            }
        };
    }

    private Func1<Authentication, Observable<List<String>>> getConversations() {
        return new Func1<Authentication, Observable<List<String>>>() {
            @Override
            public Observable<List<String>> call(Authentication authentication) {
                return conversationListService.getConversationsFor(self);
            }
        };
    }

    private Func1<List<String>, Observable<String>> getConversation() {
        return new Func1<List<String>, Observable<String>>() {
            @Override
            public Observable<String> call(List<String> strings) {
                return Observable.from(strings);
            }
        };
    }

    private Func1<User, Observable<Message>> getLastMessage() {
        return new Func1<User, Observable<Message>>() {
            @Override
            public Observable<Message> call(User user) {
                return conversationListService.getLastMessageFor(self, user);
            }
        };
    }

    private Func2<User, Message, Pair<User, Message>> asPair() {
        return new Func2<User, Message, Pair<User, Message>>() {
            @Override
            public Pair<User, Message> call(User user, Message message) {
                return new Pair<>(user, message);
            }
        };
    }

    private Subscriber<Pair<User, Message>> lastMessageSubscriber() {
        return new Subscriber<Pair<User, Message>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Pair<User, Message> pair) {
                User user = pair.first;
                Message message = pair.second;
                conversationListDisplayer.addToDisplay(
                        new Conversation(user.getUid(),user.getName(),user.getImage(),message.getMessage(),message.getTimestamp()));
            }
        };
    }

}
