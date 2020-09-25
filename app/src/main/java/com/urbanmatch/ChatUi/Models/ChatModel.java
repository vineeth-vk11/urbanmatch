package com.urbanmatch.ChatUi.Models;

public class ChatModel {
    String name;
    String uid;
    String oppositeUid;

    public ChatModel() {
    }

    public ChatModel(String name, String uid, String oppositeUid) {
        this.name = name;
        this.uid = uid;
        this.oppositeUid = oppositeUid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOppositeUid() {
        return oppositeUid;
    }

    public void setOppositeUid(String oppositeUid) {
        this.oppositeUid = oppositeUid;
    }
}
