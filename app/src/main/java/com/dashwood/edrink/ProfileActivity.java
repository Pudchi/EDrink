package com.dashwood.edrink;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class ProfileActivity extends AppCompatActivity {

    TextView name_lbl, gender_lbl, hw_lbl, goal_lbl, name_txt, gender_txt, height_txt, weight_txt, goal_txt;
    Typeface typeface_regular;
    LottieAnimationView profile_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        typeface_regular = TypefaceProvider.getTypeFace(getApplicationContext(), "Circular_book.ttf");

        profile_pic = (LottieAnimationView) findViewById(R.id.animation_view);

        TextView main_pro_name=(TextView)findViewById(R.id.name_txt);
        TextView main_pro_weight=(TextView)findViewById(R.id.weight_txt);
        TextView main_pro_height=(TextView)findViewById(R.id.height_txt);
        TextView main_pro_gender=(TextView)findViewById(R.id.gender_txt);
        TextView main_pro_goal=(TextView)findViewById(R.id.goal_txt);
        Bundle bundle_get=this.getIntent().getExtras();
        String gender = bundle_get.getString("gender");
        String genderText="";
        if(gender.equals("Male")){
            genderText="Male";
        }else {
            genderText = "Female";
        }
        main_pro_name.setText(bundle_get.getString("main_pro_name_data"));
        main_pro_gender.setText(genderText);
        main_pro_height.setText(bundle_get.getString("main_pro_height_data"));
        main_pro_weight.setText(bundle_get.getString("main_pro_weight_data"));
        int height = Integer.parseInt(bundle_get.getString("main_pro_height_data"));
        int weight = Integer.parseInt(bundle_get.getString("main_pro_weight_data"));
        //goal
        if(gender.equals("Male")){
            main_pro_goal.setText(String.valueOf((height+weight)*10)+"cc");;
        }else{
            main_pro_goal.setText(String.valueOf((height+weight)*10-500)+"cc");;
        }

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
        height_txt = (TextView) findViewById(R.id.height_txt);
        height_txt.setTypeface(typeface_regular);
        weight_txt = (TextView) findViewById(R.id.weight_txt);
        height_txt.setTypeface(typeface_regular);
        goal_txt = (TextView) findViewById(R.id.goal_txt);
        goal_txt.setTypeface(typeface_regular);
    }
}
