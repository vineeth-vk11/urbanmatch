package com.example.urbanmatch.HomeUi;

import androidx.recyclerview.widget.DiffUtil;

import com.example.urbanmatch.HomeHelper.UserModel;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

public class CardStackCallBack extends DiffUtil.Callback {

    ArrayList<UserModel> oldUserArrayList, newUserArrayList;


    public CardStackCallBack(ArrayList<UserModel> oldUserArrayList, ArrayList<UserModel> newUserArrayList) {
        this.oldUserArrayList = oldUserArrayList;
        this.newUserArrayList = newUserArrayList;
    }

    @Override
    public int getOldListSize() {
        return oldUserArrayList.size();
    }

    @Override
    public int getNewListSize() {
        return newUserArrayList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldUserArrayList.get(oldItemPosition).getName() == newUserArrayList.get(newItemPosition).getName();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldUserArrayList.get(oldItemPosition) == newUserArrayList.get(newItemPosition);
    }
}
