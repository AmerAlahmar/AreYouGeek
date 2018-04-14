package com.testspace.amer.areyougeek;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class WelcomeActivity extends AppCompatActivity {
    Button startButton;//the start button to start the quiz.
    ImageView geekImageView;//image to be animated (shake).

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //Initialize and connect Layout, TextViews and Button
        startButton = findViewById(R.id.startButton);
        geekImageView = findViewById(R.id.geekImageView);
        //declare new Animation and load "shaker" from anim resource to it
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shaker);
        //start Animation (to infinity)
        geekImageView.startAnimation(shake);
        //On clicking start button move to activity_quiz using new Intent
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });
    }
}