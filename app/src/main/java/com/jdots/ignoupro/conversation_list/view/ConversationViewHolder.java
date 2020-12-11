package com.jdots.ignoupro.conversation_list.view;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.jdots.ignoupro.conversation_list.data_model.Conversation;

/**
 * Created for chatting on 29/07/16.
 */

public class ConversationViewHolder extends RecyclerView.ViewHolder {

    private final ConversationView conversationView;

    public ConversationViewHolder(ConversationView itemView) {
        super(itemView);
        this.conversationView = itemView;
    }

    public void bind(final Conversation conversation, final ConversationSelectionListener listener) {
        conversationView.display(conversation);
        conversationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onConversationSelected(conversation);
            }
        });
    }

    public interface ConversationSelectionListener {
        void onConversationSelected(Conversation conversation);
    }

}