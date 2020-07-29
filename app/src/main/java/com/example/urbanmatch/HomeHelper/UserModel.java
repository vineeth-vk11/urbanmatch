package com.example.urbanmatch.HomeHelper;

public class UserModel {
    String city;
    String currentState;
    String dob;
    String age;
    String education;
    String gender;
    String height;
    String name;
    String nativeState;
    String occupation;
    String relationship;
    String uid;
    int fScore;

    public UserModel() {
    }


    public UserModel(String city, String currentState, String dob, String age, String education, String gender, String height, String name, String nativeState, String occupation, String relationship, int fScore, String uid) {
        this.city = city;
        this.currentState = currentState;
        this.dob = dob;
        this.age = age;
        this.education = education;
        this.gender = gender;
        this.height = height;
        this.name = name;
        this.nativeState = nativeState;
        this.occupation = occupation;
        this.relationship = relationship;
        this.fScore = fScore;
        this.uid = uid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNativeState() {
        return nativeState;
    }

    public void setNativeState(String nativeState) {
        this.nativeState = nativeState;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public int getfScore() {
        return fScore;
    }

    public void setfScore(int fScore) {
        this.fScore = fScore;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
