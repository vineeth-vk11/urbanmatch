package com.example.urbanmatch.HomeUi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.urbanmatch.ChatUi.Adapters.ChatAdapter;
import com.example.urbanmatch.ChatUi.Models.ChatModel;
import com.example.urbanmatch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ChatFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<ChatModel> chatModelArrayList;
    FirebaseFirestore db;
    FirebaseUser firebaseUser;
    String uid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        chatModelArrayList = new ArrayList<>();
        db= FirebaseFirestore.getInstance();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();

        recyclerView = view.findViewById(R.id.chat_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getChatRecepients();

        return view;
    }

    private void getChatRecepients() {
        CollectionReference collectionReference = db.collection("userData").document(uid).collection("Accepted");

        if(collectionReference == null){
            Toast.makeText(getContext(),"No chats",Toast.LENGTH_SHORT).show();
        }
        else if(collectionReference != null){
            collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(DocumentSnapshot documentSnapshot: task.getResult()){
                        ChatModel chatModel = new ChatModel();
                        chatModel.setName(documentSnapshot.getString("name"));
                        chatModel.setOppositeUid(documentSnapshot.getString("uid"));
                        chatModel.setUid(uid);

                        chatModelArrayList.add(chatModel);
                    }

                    ChatAdapter chatAdapter = new ChatAdapter(getContext(),chatModelArrayList);
                    recyclerView.setAdapter(chatAdapter);
                }
            });
        }
    }
}