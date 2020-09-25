package com.urbanmatch.HomeUi;

import androidx.recyclerview.widget.DiffUtil;

import com.urbanmatch.HomeHelper.UserModel;

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
