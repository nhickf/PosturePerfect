package com.example.le_androidapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ImageDialogueActivity extends AppCompatActivity{

    private ImageView hotlineDialogue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_dialogue);

        hotlineDialogue = (ImageView) findViewById(R.id.hotlines);
        hotlineDialogue.setClickable(true);
        hotlineDialogue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}