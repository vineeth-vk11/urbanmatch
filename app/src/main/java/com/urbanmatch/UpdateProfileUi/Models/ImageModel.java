package com.urbanmatch.UpdateProfileUi.Models;

public class ImageModel {

    String image;
    String imageKey;

    public ImageModel() {
    }

    public ImageModel(String image, String imageKey) {
        this.image = image;
        this.imageKey = imageKey;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageKey() {
        return imageKey;
    }

    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }
}
