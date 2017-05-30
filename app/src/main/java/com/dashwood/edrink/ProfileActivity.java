package com.dashwood.edrink;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class ProfileActivity extends AppCompatActivity {

    TextView name_lbl, gender_lbl, hw_lbl, goal_lbl, name_txt, gender_txt, hw_txt, goal_txt;
    Typeface typeface_regular;
    LottieAnimationView profile_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        typeface_regular = TypefaceProvider.getTypeFace(getApplicationContext(), "Circular_book.ttf");

        profile_pic = (LottieAnimationView) findViewById(R.id.animation_view);

        name_lbl = (TextView) findViewById(R.id.name_lbl);
        name_lbl.setTypeface(typeface_regular);
        gender_lbl = (TextView) findViewById(R.id.gender_lbl);
        gender_lbl.setTypeface(typeface_regular);
        hw_lbl = (TextView) findViewById(R.id.hw_lbl);
        hw_lbl.setTypeface(typeface_regular);
        goal_lbl = (TextView) findViewById(R.id.goal_lbl);
        goal_lbl.setTypeface(typeface_regular);
        name_txt = (TextView) findViewById(R.id.name_txt);
        name_txt.setTypeface(typeface_regular);
        gender_txt = (TextView) findViewById(R.id.gender_txt);
        gender_txt.setTypeface(typeface_regular);
        hw_txt = (TextView) findViewById(R.id.hw_txt);
        hw_txt.setTypeface(typeface_regular);
        goal_txt = (TextView) findViewById(R.id.goal_txt);
        goal_txt.setTypeface(typeface_regular);
    }
}
