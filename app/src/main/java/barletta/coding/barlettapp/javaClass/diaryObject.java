package barletta.coding.barlettapp.javaClass;

public class diaryObject {


    private String title, description, photoEncoded;
    private int id;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
