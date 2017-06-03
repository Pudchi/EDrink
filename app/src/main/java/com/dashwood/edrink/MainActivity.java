package com.dashwood.edrink;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    LottieAnimationView water_anim;
    Button goto_profile, goto_login;
    Typeface typeface_regular;
    TextView water_percent;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    //private FirebaseUser user;

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
        goto_login = (Button) findViewById(R.id.login_btn);
        goto_login.setTypeface(typeface_regular);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            goto_login.setVisibility(View.VISIBLE);
            goto_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            });
            //Toast.makeText(getApplicationContext(), "Account LOG OUT", Toast.LENGTH_SHORT).show();
        } else {
            goto_login.setText("LOG OUT");
            goto_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    auth.signOut();
                    goto_login.setText("LOGIN");
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            });
        }

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser == null) {
                    Toast.makeText(getApplicationContext(), "未登入", Toast.LENGTH_SHORT).show();
                }
            }
        };

        goto_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PersonalDataActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        water_anim.playAnimation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        water_anim.cancelAnimation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        water_anim.cancelAnimation();
    }
}
