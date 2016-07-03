package com.example.PhotoGallery;

import android.net.Uri;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by gaieepo on 17/6/2016.
 */
public class PixabayFetchr {
    public static final String TAG = "PixabayFetchr";

    public static final String PREF_SEARCH_QUERY = "searchQuery";
    public static final String PREF_LAST_RESULT_ID = "lastResultId";

    private static final String ENDPOINT = "https://pixabay.com/api/";
    private static final String API_KEY = "2773707-6b9eab3a9f01c7d3e40fa9a59";

    byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public String getUrl(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public ArrayList<GalleryItem> downloadGalleryItems(String url) {
        ArrayList<GalleryItem> items = new ArrayList<>();
        try {
            String jsonString = getUrl(url);
            Log.i(TAG, "Received json: " + jsonString);
            parseItems(items, jsonString);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        }
        return items;
    }

    public ArrayList<GalleryItem> fetchItems() {
        String url = Uri.parse(ENDPOINT).buildUpon()
                .appendQueryParameter("key", API_KEY)
                .appendQueryParameter("q", "cat")
                .appendQueryParameter("image_type", "photo")
                .appendQueryParameter("per_page", "30")
                .appendQueryParameter("pretty", "true")
                .build().toString();
        return downloadGalleryItems(url);
    }

    public ArrayList<GalleryItem> search(String query) {
        String formattedQuery = formatQuery(query);
        String url = Uri.parse(ENDPOINT).buildUpon()
                .appendQueryParameter("key", API_KEY)
                .appendQueryParameter("q", formattedQuery)
                .appendQueryParameter("image_type", "photo")
                .appendQueryParameter("per_page", "30")
                .appendQueryParameter("pretty", "true")
                .build().toString();
        Log.i(TAG, url);
        return downloadGalleryItems(url);
    }

    private String formatQuery(String query) {
        return query.replace(' ', '+');
    }

    private void parseItems(ArrayList<GalleryItem> items, String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("hits");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject galleryObject = jsonArray.getJSONObject(i);
                String id = galleryObject.getString("id");
                String photoUrl = galleryObject.getString("previewURL");
                GalleryItem item = new GalleryItem();
                item.setId(id);
                item.setPhotoUrl(photoUrl);
                items.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
