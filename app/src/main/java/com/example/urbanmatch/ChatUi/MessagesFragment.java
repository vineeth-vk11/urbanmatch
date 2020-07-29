package com.example.urbanmatch.ChatUi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.urbanmatch.ChatUi.Adapters.MessageAdapter;
import com.example.urbanmatch.ChatUi.Models.MessageModel;
import com.example.urbanmatch.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;


public class MessagesFragment extends Fragment {

    String sentUser;
    String receivedUser;

    ImageButton sendButtom;
    EditText txtMessage;

    String messageSent;

    RecyclerView recyclerView;
    ArrayList<MessageModel> messageModelArrayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_messages, container, false);

        sendButtom = view.findViewById(R.id.sendButton);
        txtMessage = view.findViewById(R.id.message_edit);

        messageSent = txtMessage.getText().toString().trim();

        Bundle bundle = this.getArguments();
        sentUser = bundle.getString("uid").trim();
        receivedUser = bundle.getString("oppositeUid").trim();

        sendButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageSent = txtMessage.getText().toString().trim();
                if(messageSent != ""){
                    sendMessage(sentUser, receivedUser, messageSent);
                }
                else if(messageSent == ""){
                    Toast.makeText(getActivity(),"Enter a message",Toast.LENGTH_SHORT).show();
                    return;
                }

                txtMessage.setText("");
            }
        });

        messageModelArrayList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.message_recycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        ;getMessages();
//        getAllMessages();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Chats");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getMessages();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

    private void getAllMessages(){
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Chats").child(sentUser).child(receivedUser);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    MessageModel messageModel = new MessageModel();
                    messageModel.setMessage(dataSnapshot.child("message").getValue().toString());
                    messageModel.setSentUser(dataSnapshot.child("sentUser").getValue().toString());
                    messageModel.setReceivedUser(dataSnapshot.child("receivedUser").getValue().toString());

                    messageModelArrayList.add(messageModel);
                }
                MessageAdapter messageAdapter = new MessageAdapter(getContext(),messageModelArrayList);
                recyclerView.setAdapter(messageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getMessages() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Chats").child(sentUser).child(receivedUser);

        databaseReference.addValueEventListener(new ValueEventListener() {
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

                MessageAdapter messageAdapter = new MessageAdapter(getContext(),messageModelArrayList);
                recyclerView.setAdapter(messageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendMessage(String sentUser, String receivedUser, String messageSent) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference();

        HashMap<String , Object> message = new HashMap<>();
        message.put("message",messageSent);
        message.put("sentUser",sentUser);
        message.put("receivedUser",receivedUser);

        databaseReference.child("Chats").child(sentUser).child(receivedUser).push().setValue(message);
        databaseReference1.child("Chats").child(receivedUser).child(sentUser).push().setValue(message);

    }
}