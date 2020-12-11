package com.jdots.ignoupro.user.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.jdots.ignoupro.R;
import com.jdots.ignoupro.user.data_model.User;
import com.jdots.ignoupro.user.data_model.Users;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created for chatting on 19/08/16.
 */

public class UsersAdapter extends RecyclerView.Adapter<UserViewHolder> {

    private final LayoutInflater inflater;
    private Users users = new Users(new ArrayList<User>());
    private UsersDisplayer.UserInteractionListener usersInteractionListener;
    private final UserViewHolder.UserSelectionListener clickListener = new UserViewHolder.UserSelectionListener() {
        @Override
        public void onUserSelected(User user) {
            usersInteractionListener.onUserSelected(user);
        }
    };

    UsersAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public void update(Users users){
        this.users = users;
        this.users = users.sortedByName();
        notifyDataSetChanged();
    }

    public void filter(String text) {
        Iterator<User> it = users.getUsers().iterator();
        while (it.hasNext()) {
            if (!it.next().getName().toLowerCase().contains(text.toLowerCase()))
                it.remove();
        }
        notifyDataSetChanged();
    }

    public Users getUsers() {
        return users;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserViewHolder((UserView) inflater.inflate(R.layout.users_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.bind(users.getUserAt(position), clickListener);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @Override
    public long getItemId(int position) {
        return users.getUserAt(position).hashCode();
    }

    public void attach(UsersDisplayer.UserInteractionListener userInteractionListener) {
        this.usersInteractionListener = userInteractionListener;
    }

    public void detach(UsersDisplayer.UserInteractionListener userInteractionListener) {
        this.usersInteractionListener = null;
    }

}
