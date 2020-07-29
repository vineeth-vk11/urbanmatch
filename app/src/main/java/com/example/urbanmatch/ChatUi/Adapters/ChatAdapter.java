package com.example.urbanmatch.ChatUi.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urbanmatch.ChatUi.MessagesFragment;
import com.example.urbanmatch.ChatUi.Models.ChatModel;
import com.example.urbanmatch.ChatUi.ViewHolders.ChatViewHolder;
import com.example.urbanmatch.R;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {

    Context context;
    ArrayList<ChatModel> chatModelArrayList;

    public ChatAdapter(Context context, ArrayList<ChatModel> chatModelArrayList) {
        this.context = context;
        this.chatModelArrayList = chatModelArrayList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_chat,parent,false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, final int position) {
        holder.name.setText(chatModelArrayList.get(position).getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MessagesFragment messagesFragment = new MessagesFragment();

                Bundle data = new Bundle();
                data.putString("name",chatModelArrayList.get(position).getName());
                data.putString("uid",chatModelArrayList.get(position).getUid());
                data.putString("oppositeUid",chatModelArrayList.get(position).getOppositeUid());
                messagesFragment.setArguments(data);

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_frame,messagesFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatModelArrayList.size();
    }
}
