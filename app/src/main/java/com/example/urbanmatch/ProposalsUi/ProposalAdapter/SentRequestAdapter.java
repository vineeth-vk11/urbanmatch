package com.example.urbanmatch.ProposalsUi.ProposalAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urbanmatch.ProposalsUi.ProposalModels.SentRequestModel;
import com.example.urbanmatch.ProposalsUi.ProposalViewHolder.SentRequestViewHolder;
import com.example.urbanmatch.R;

import java.util.ArrayList;

public class SentRequestAdapter extends RecyclerView.Adapter<SentRequestViewHolder> {
    Context context;
    ArrayList<SentRequestModel> sentRequestModelArrayList;

    public SentRequestAdapter(Context context, ArrayList<SentRequestModel> sentRequestModelArrayList) {
        this.context = context;
        this.sentRequestModelArrayList = sentRequestModelArrayList;
    }

    @NonNull
    @Override
    public SentRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_sent,parent,false);
        return new SentRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SentRequestViewHolder holder, int position) {
        holder.sentName.setText(sentRequestModelArrayList.get(position).getName());
        holder.sentAge.setText(sentRequestModelArrayList.get(position).getAge());
        holder.sentHeight.setText(sentRequestModelArrayList.get(position).getHeight());
        holder.sentEducation.setText(sentRequestModelArrayList.get(position).getEducation());
        holder.sentOccupation.setText(sentRequestModelArrayList.get(position).getProfession());
        holder.sentLocation.setText(String.format("%s,%s", sentRequestModelArrayList.get(position).getCity(), sentRequestModelArrayList.get(position).getState()));
    }

    @Override
    public int getItemCount() {
        return sentRequestModelArrayList.size();
    }
}
