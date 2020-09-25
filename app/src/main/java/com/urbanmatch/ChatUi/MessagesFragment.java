package com.urbanmatch.ChatUi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.urbanmatch.ChatUi.Adapters.MessageAdapter;
import com.urbanmatch.ChatUi.Models.MessageModel;
import com.urbanmatch.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        Bundle bundle = this.getArguments();
        sentUser = bundle.getString("uid").trim();
        receivedUser = bundle.getString("oppositeUid").trim();

        sendButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        messageModelArrayList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.message_recycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

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
                recyclerView.getLayoutManager().scrollToPosition(messageModelArrayList.size()-1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendMessage() {

        String messageSent = txtMessage.getText().toString().trim();
        String sentUserFinal = sentUser;
        String recivedUserFinal = receivedUser;

        if(messageSent.equals("")){
            Toast.makeText(getContext(),"Enter a message",Toast.LENGTH_SHORT).show();
            return;
        }
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference();

        HashMap<String , Object> message = new HashMap<>();
        message.put("message",messageSent);
        message.put("sentUser",sentUserFinal);
        message.put("receivedUser",recivedUserFinal);

        databaseReference.child("Chats").child(sentUser).child(receivedUser).push().setValue(message);
        databaseReference1.child("Chats").child(receivedUser).child(sentUser).push().setValue(message);
        txtMessage.setText("");

        recyclerView.getLayoutManager().scrollToPosition(messageModelArrayList.size()-1);
    }
}