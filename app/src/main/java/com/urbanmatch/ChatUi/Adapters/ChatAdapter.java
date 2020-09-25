package com.urbanmatch.ChatUi.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.urbanmatch.ChatUi.MessagesFragment;
import com.urbanmatch.ChatUi.Models.ChatModel;
import com.urbanmatch.ChatUi.Models.MessageModel;
import com.urbanmatch.ChatUi.Models.UserImagesModel;
import com.urbanmatch.ChatUi.ViewHolders.ChatViewHolder;
import com.urbanmatch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {

    Context context;
    ArrayList<ChatModel> chatModelArrayList;
    ArrayList<UserImagesModel> userImagesModelArrayList = new ArrayList<>();
    ArrayList<MessageModel> messageModelArrayList = new ArrayList<>();

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
    public void onBindViewHolder(@NonNull final ChatViewHolder holder, final int position) {
        holder.name.setText(chatModelArrayList.get(position).getName());

        FirebaseDatabase db;
        db = FirebaseDatabase.getInstance();

        db.getReference().child("photos").child(chatModelArrayList.get(position).getOppositeUid().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("userId",chatModelArrayList.get(position).getOppositeUid());
                userImagesModelArrayList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    UserImagesModel userImagesModel = new UserImagesModel();
                    userImagesModel.setImage(dataSnapshot.child("imagePath").getValue().toString());

                    userImagesModelArrayList.add(userImagesModel);
                }
                Picasso.get().load(userImagesModelArrayList.get(0).getImage().toString()).into(holder.profileIcon);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        db.getReference().child("Chats").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(chatModelArrayList.get(position).getOppositeUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageModelArrayList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    MessageModel messageModel = new MessageModel();
                    messageModel.setMessage(dataSnapshot.child("message").getValue().toString());
                    messageModel.setSentUser(dataSnapshot.child("sentUser").getValue().toString());
                    messageModel.setReceivedUser(dataSnapshot.child("receivedUser").getValue().toString());

                    messageModelArrayList.add(messageModel);
                }

                if(messageModelArrayList.size()>1){
                    holder.lastMessage.setText(messageModelArrayList.get(messageModelArrayList.size()-1).getMessage().toString());
                }
                else {
                    holder.lastMessage.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
