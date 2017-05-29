package com.dashwood.edrink;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    LottieAnimationView water_anim;
    Button goto_profile;
    Typeface typeface_regular;
    TextView water_percent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        typeface_regular = TypefaceProvider.getTypeFace(getApplicationContext(), "Circular_book.ttf");

        water_anim = (LottieAnimationView) findViewById(R.id.water_loading);
        water_percent = (TextView) findViewById(R.id.water_percent);
        water_percent.setTypeface(typeface_regular);
        goto_profile = (Button) findViewById(R.id.profile_btn);
        goto_profile.setTypeface(typeface_regular);

        goto_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

    }
}
