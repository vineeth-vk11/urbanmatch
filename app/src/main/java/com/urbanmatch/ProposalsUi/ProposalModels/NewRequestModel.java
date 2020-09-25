package com.urbanmatch.ProposalsUi.ProposalModels;

public class NewRequestModel {

    String name, age, city, nativeState, currentState, education, relationship, height, occupation, uid;

    public NewRequestModel() {
    }

    public NewRequestModel(String name, String age, String city, String nativeState, String currentState, String education, String relationship, String height, String occupation, String uid) {
        this.name = name;
        this.age = age;
        this.city = city;
        this.nativeState = nativeState;
        this.currentState = currentState;
        this.education = education;
        this.relationship = relationship;
        this.height = height;
        this.occupation = occupation;
        this.uid = uid;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNativeState() {
        return nativeState;
    }

    public void setNativeState(String nativeState) {
        this.nativeState = nativeState;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
