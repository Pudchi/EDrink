package com.dashwood.edrink;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button login;
    EditText input_mail, input_password;
    Typeface typeface_regular;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null)
        {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        setContentView(R.layout.activity_login);

        typeface_regular = TypefaceProvider.getTypeFace(getApplicationContext(), "Circular_book.ttf");

        input_mail = (EditText) findViewById(R.id.email_input);
        input_mail.setTypeface(typeface_regular);
        input_password = (EditText) findViewById(R.id.password_input);
        input_password.setTypeface(typeface_regular);

        login = (Button) findViewById(R.id.login_btn);
        login.setTypeface(typeface_regular);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = input_mail.getText().toString().trim();
                final String passwd = input_password.getText().toString().trim();

                if (TextUtils.isEmpty(mail))
                {
                    input_mail.setError("請輸入帳號(Mail)!");
                    //Toast.makeText(getApplicationContext(), "請輸入帳號(Mail)!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(passwd))
                {
                    input_password.setError("請輸入密碼!");
                    //Toast.makeText(getApplicationContext(), "請輸入密碼!", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.signInWithEmailAndPassword(mail, passwd)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (!task.isSuccessful())
                                {
                                    if (passwd.length() < 6)
                                    {
                                        input_password.setError("密碼太短!! 至少6個字元");
                                        //Toast.makeText(getApplicationContext(), "密碼太短!!需大於6個字元", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(LoginActivity.this, "登入失敗! 檢查帳號密碼或前往註冊!", Toast.LENGTH_LONG).show();
                                    }
                                }
                                else
                                {
                                    Intent intent = new Intent(LoginActivity.this, PersonalDataActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });

    }
}
