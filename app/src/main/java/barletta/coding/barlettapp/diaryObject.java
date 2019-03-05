package barletta.coding.barlettapp;

import android.graphics.Bitmap;

public class diaryObject {


    String title, description, photoEncoded;


    public diaryObject(String photo, String title, String description){

        this.photoEncoded = photo;
        this.title = title;
        this.description = description;

    }

    public String getPhoto() {
        return photoEncoded;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }
}
