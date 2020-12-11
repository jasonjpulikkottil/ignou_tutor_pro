package com.jdots.ignoupro.conversation;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import com.jdots.ignoupro.BaseActivity;
import com.jdots.ignoupro.Dependencies;
import com.jdots.ignoupro.R;
import com.jdots.ignoupro.conversation.presenter.ConversationPresenter;
import com.jdots.ignoupro.conversation.view.ConversationDisplayer;
import com.jdots.ignoupro.conversation.view.ConversationView;
import com.jdots.ignoupro.navigation.AndroidNavigator;


/**
 * Created for chatting on 29/07/16.
 */

public class ConversationActivity extends BaseActivity {

    private ConversationPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ConversationDisplayer conversationDisplayer = (ConversationView) findViewById(R.id.conversationView);
        presenter = new ConversationPresenter(
                Dependencies.INSTANCE.getLoginService(),
                Dependencies.INSTANCE.getConversationService(),
                conversationDisplayer,
                Dependencies.INSTANCE.getUserService(),
                getIntent().getStringExtra("sender"),
                getIntent().getStringExtra("destination"),
                new AndroidNavigator(this)
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.startPresenting();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stopPresenting();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
