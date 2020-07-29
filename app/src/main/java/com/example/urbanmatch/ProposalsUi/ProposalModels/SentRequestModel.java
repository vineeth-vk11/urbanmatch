package com.example.urbanmatch.ProposalsUi.ProposalModels;

public class SentRequestModel {
    String name, age, relationship, profession, state, city, height, oppositeUserId, education;

    public SentRequestModel() {
    }

    public SentRequestModel(String name, String age, String relationship, String profession, String state, String city, String height, String oppositeUserId, String education) {
        this.name = name;
        this.age = age;
        this.relationship = relationship;
        this.profession = profession;
        this.state = state;
        this.city = city;
        this.height = height;
        this.oppositeUserId = oppositeUserId;
        this.education = education;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getOppositeUserId() {
        return oppositeUserId;
    }

    public void setOppositeUserId(String oppositeUserId) {
        this.oppositeUserId = oppositeUserId;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }
}
