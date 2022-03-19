package com.solie.projecte.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.solie.projecte.R;
import com.solie.projecte.fragment.MovieInfoFragment;

public class GalleryImage extends AppCompatActivity
{

    PhotoView photoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_image);

        MovieInfoFragment.toolbarTitle.setText("사진 보기");

        photoView = findViewById(R.id.photoView);
        if(getIntent() != null) {
            String imageUrl = getIntent().getExtras().getString("ImageResource");
            Glide.with(getApplicationContext()).load(imageUrl).into(photoView);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 105) {
            finish();
        }
    }
}
