package com.dashwood.edrink;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class PersonalDataActivity extends AppCompatActivity {

    EditText name, height, weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);

        AlertDialog.Builder dialog = new AlertDialog.Builder(PersonalDataActivity.this, R.style.MyAlertDialogStyle);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.drawable.edrink);
        getSupportActionBar().hide();
        Button confirm = (Button)findViewById(R.id.comfirm);
        name=(EditText) findViewById(R.id.name);
        weight=(EditText) findViewById(R.id.weight);
        height=(EditText) findViewById(R.id.height);


        confirm.setOnClickListener(new View.OnClickListener() {
            //onclick listener
            @Override
            public void onClick(View v) {

                String gender;
                RadioButton SexMale = (RadioButton)findViewById(R.id.male);
                RadioButton SexFemale = (RadioButton)findViewById(R.id.female);
                //判斷選擇性別
                if(SexMale.isChecked()){
                    gender="Male";
                }
                else{
                    gender="Female";
                }
                Intent intent = new Intent();
                intent.setClass(PersonalDataActivity.this  , ProfileActivity.class);
                Bundle bundle=new Bundle();

                if(TextUtils.isEmpty(name.getText()) ||  TextUtils.isEmpty(height.getText()) || TextUtils.isEmpty(weight.getText())) {
                    //edittext輸入為空白或無輸入
                    Toast toast = Toast.makeText(PersonalDataActivity.this, "Please enter your information!!", Toast.LENGTH_LONG);
                    toast.show();
                }
                else {
                    //edittext有輸入
                    if (SexMale.isChecked()==false & SexFemale.isChecked()==false) {
                        Toast toast = Toast.makeText(PersonalDataActivity.this, "Please select your gender!!", Toast.LENGTH_LONG);
                        toast.show();
                    }
                    else {
                        //send data
                        bundle.putString("gender", gender);
                        bundle.putString("main_pro_name_data", name.getText().toString());
                        bundle.putString("main_pro_weight_data", weight.getText().toString());
                        bundle.putString("main_pro_height_data", height.getText().toString());
                        intent.putExtras(bundle);
                        startActivity(intent);
                        PersonalDataActivity.this.finish();
                    }
                }
            }
        });
    }


}
