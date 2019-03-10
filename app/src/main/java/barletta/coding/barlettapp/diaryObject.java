package barletta.coding.barlettapp;

public class diaryObject {


    String title, description, photoEncoded;

    public diaryObject() {
    }

    public diaryObject(String photo, String title, String description) {

        this.photoEncoded = photo;
        this.title = title;
        this.description = description;

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPhotoEncoded(String photoEncoded) {
        this.photoEncoded = photoEncoded;
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
