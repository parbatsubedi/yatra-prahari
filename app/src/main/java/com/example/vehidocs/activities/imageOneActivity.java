package com.example.vehidocs.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.vehidocs.R;

public class imageOneActivity extends AppCompatActivity {
    public static final String ownerImageParameter = "ownerImage";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_one);

        Intent i = getIntent();
        String ownerImage = i.getStringExtra(ownerImageParameter);
        ImageView image = findViewById(R.id.imageView);

        Glide.with(imageOneActivity.this)
                .load(ownerImage)
                .apply(new RequestOptions().override(1500, 2000))
                .centerCrop()
                .into(image);


    }
}