package com.jdots.ignoupro.user;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.jdots.ignoupro.Dependencies;
import com.jdots.ignoupro.R;
import com.jdots.ignoupro.navigation.AndroidConversationsNavigator;
import com.jdots.ignoupro.navigation.AndroidNavigator;
import com.jdots.ignoupro.user.presenter.UsersPresenter;
import com.jdots.ignoupro.user.view.UsersDisplayer;

/**
 * Created for chatting on 19/08/16.
 */

public class UsersFragment extends Fragment {

    private UsersPresenter presenter;
    private final BroadcastReceiver searchReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String text = intent.getStringExtra("search");
            presenter.filterUsers(text);
        }
    };
    private AndroidConversationsNavigator navigator;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_users, container, false);
        getActivity().setTitle(R.string.users_toolbar_title);

        navigator = new AndroidConversationsNavigator((AppCompatActivity)getActivity(),new AndroidNavigator(getActivity()));
        presenter = new UsersPresenter(
                (UsersDisplayer) rootView.findViewById(R.id.usersView),
                navigator,
                Dependencies.INSTANCE.getLoginService(),
                Dependencies.INSTANCE.getUserService()
        );

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.startPresenting();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stopPresenting();
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter("SEARCH");
        getActivity().registerReceiver(searchReceiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(searchReceiver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
