package com.urbanmatch.HomeHelper;

public class SliderItemModel {
    private String description;
    private String imageUrl;

    public SliderItemModel(String description, String imageUrl) {
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public SliderItemModel() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
