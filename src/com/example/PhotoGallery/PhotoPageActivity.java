package com.example.PhotoGallery;

import android.support.v4.app.Fragment;

/**
 * Created by gaieepo on 3/7/2016.
 */
public class PhotoPageActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new PhotoPageFragment();
    }
}
