package com.jdots.ignoupro.user.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jdots.ignoupro.R;
import com.jdots.ignoupro.user.data_model.Users;

/**
 * Created for chatting on 19/08/16.
 */

public class UsersView extends LinearLayout implements UsersDisplayer {

    private final UsersAdapter usersAdapter;
    private final UsersAdapter usersFilteredAdapter;
    private UserInteractionListener usersInteractionListener;

    private RecyclerView conversations;

    public UsersView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        usersAdapter = new UsersAdapter(LayoutInflater.from(context));
        usersFilteredAdapter = new UsersAdapter(LayoutInflater.from(context));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View.inflate(getContext(), R.layout.merge_conversation_list_view, this);
        conversations = (RecyclerView) this.findViewById(R.id.conversationsRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        conversations.setLayoutManager(layoutManager);
        conversations.setAdapter(usersAdapter);

    }

    @Override
    public void display(Users users) {
        usersAdapter.update(users);
    }

    @Override
    public void attach(UserInteractionListener userInteractionListener) {
        this.usersInteractionListener = userInteractionListener;
        usersAdapter.attach(userInteractionListener);
        usersFilteredAdapter.attach(usersInteractionListener);
    }

    @Override
    public void detach(UserInteractionListener userInteractionListener) {
        this.usersInteractionListener = null;
        usersAdapter.detach(userInteractionListener);
        usersFilteredAdapter.detach(userInteractionListener);
    }

    @Override
    public void filter(String text) {
        if (text.equals(""))
            conversations.setAdapter(usersAdapter);
        else {
            usersFilteredAdapter.update(usersAdapter.getUsers());
            usersFilteredAdapter.filter(text);
            conversations.setAdapter(usersFilteredAdapter);
        }
    }
}
