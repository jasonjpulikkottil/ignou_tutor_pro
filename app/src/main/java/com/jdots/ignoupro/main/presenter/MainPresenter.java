package com.jdots.ignoupro.main.presenter;

import android.content.Intent;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.jdots.ignoupro.R;
import com.jdots.ignoupro.login.data_model.Authentication;
import com.jdots.ignoupro.login.service.LoginService;
import com.jdots.ignoupro.main.service.CloudMessagingService;
import com.jdots.ignoupro.main.service.MainService;
import com.jdots.ignoupro.main.view.MainDisplayer;
import com.jdots.ignoupro.navigation.AndroidMainNavigator;
import com.jdots.ignoupro.user.data_model.User;
import com.jdots.ignoupro.user.service.UserService;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;

/**
 * Created for chatting on 16/08/16.
 */

public class MainPresenter {

    private final AppCompatActivity activity;

    private final LoginService loginService;
    private final UserService userService;
    private final MainDisplayer mainDisplayer;
    private final MainService mainService;
    private final CloudMessagingService messagingService;
    private final AndroidMainNavigator navigator;
    private final String token;
    private final MainDisplayer.DrawerActionListener drawerActionListener = new MainDisplayer.DrawerActionListener() {

        @Override
        public void onHeaderSelected() {
            navigator.toProfile();
        }

        @Override
        public void onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_conversations:
                    navigator.toConversations();
                    //mainDisplayer.setTitle(activity.getString(R.string.conversations_toolbar_title));
                    mainDisplayer.clearMenu();
                    break;
                case R.id.nav_users:
                    navigator.toUserList();
                    //mainDisplayer.setTitle(activity.getString(R.string.users_toolbar_title));
                    mainDisplayer.inflateMenu();
                    break;
                case R.id.nav_global:
                    navigator.toGlobalRoom();
                    //mainDisplayer.setTitle(activity.getString(R.string.global_toolbar_title));
                    mainDisplayer.clearMenu();
                    break;

                case R.id.profile:
                    navigator.toProfile();
                    break;
                case R.id.logout:
                    try {
                        if (mainService.getLoginProvider().equals("google.com"))
                            navigator.toGoogleSignOut(AndroidMainNavigator.LOGOUT_GOOGLE);
                        mainService.logout();
                        navigator.toLogin();
                    } catch (Exception e) {

                    }
            }
            mainDisplayer.closeDrawer();
        }

    };
    private final MainDisplayer.NavigationActionListener navigationActionListener = new MainDisplayer.NavigationActionListener() {

        @Override
        public void onHamburgerPressed() {
            mainDisplayer.openDrawer();
        }

    };
    private final MainDisplayer.SearchActionListener searchActionListener = new MainDisplayer.SearchActionListener() {

        @Override
        public void showFilteredUsers(String text) {
            Intent intent = new Intent("SEARCH");
            intent.putExtra("search",text);
            activity.sendBroadcast(intent);
        }

    };
    private Subscription loginSubscription;

    public MainPresenter(LoginService loginService,
                         UserService userService,
                         MainDisplayer mainDisplayer,
                         MainService mainService,
                         CloudMessagingService messagingService,
                         AndroidMainNavigator navigator,
                         String token,
                         AppCompatActivity activity) {
        this.loginService = loginService;
        this.userService = userService;
        this.mainDisplayer = mainDisplayer;
        this.mainService = mainService;
        this.messagingService = messagingService;
        this.navigator = navigator;
        this.token = token;
        this.activity = activity;
    }

    public void startPresenting() {
        navigator.init();
        mainDisplayer.attach(drawerActionListener,navigationActionListener,searchActionListener);

        loginSubscription = loginService.getAuthentication()
                .first()
                .flatMap(getUser())
                .subscribe(userSubscriber());
    }

    public void stopPresenting() {
        mainDisplayer.detach(drawerActionListener,navigationActionListener,searchActionListener);
        loginSubscription.unsubscribe();
    }

    private Func1<Authentication, Observable<User>> getUser() {
        return new Func1<Authentication, Observable<User>>() {
            @Override
            public Observable<User> call(Authentication authentication) {
                if (!authentication.isSuccess()) {
                    navigator.toLogin();
                    return Observable.empty();
                }
                return userService.getUser(authentication.getUser().getUid());
            }
        };
    }

    private Subscriber<User> userSubscriber() {
        return new Subscriber<User>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                navigator.toFirstLogin();
            }

            @Override
            public void onNext(User user) {
                messagingService.readToken(user)
                        .subscribe(tokenSubscriber(user));
                mainService.initLastSeen(user);
                mainDisplayer.setUser(user);
            }
        };
    }

    private Subscriber<String> tokenSubscriber(final User user) {
        return new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String currentToken) {
                if (currentToken == null || !currentToken.equals(token)) {
                    messagingService.setToken(user);
                }
            }
        };
    }

    public boolean onBackPressed() {
        return mainDisplayer.onBackPressed();
    }

}
