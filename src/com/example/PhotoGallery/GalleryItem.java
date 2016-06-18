package com.example.PhotoGallery;

/**
 * Created by gaieepo on 18/6/2016.
 */
public class GalleryItem {
    private String mId;
    private String mUrl;

    public String toString() {
        return mId;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }
}
