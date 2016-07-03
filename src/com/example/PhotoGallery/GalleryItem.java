package com.example.PhotoGallery;

/**
 * Created by gaieepo on 18/6/2016.
 */
public class GalleryItem {
    private String mId;
    private String mPhotoUrl;

    public String toString() {
        return mId;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        mPhotoUrl = photoUrl;
    }
}
