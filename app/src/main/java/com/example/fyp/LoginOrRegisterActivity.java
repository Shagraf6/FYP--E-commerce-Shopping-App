package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginOrRegisterActivity extends AppCompatActivity {
    Button login_btn, logup_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);
        init();
        logup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                i.putExtra("checker",0);
                startActivity(i);


            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);

            }
        });

    }
    public void OnclickServerLogin(View v)
    {
        Intent i = new Intent(this, LoginActivity.class);
        i.putExtra("checker",1);
        startActivity(i);
    }

    public void init() {
        login_btn = findViewById(R.id.Login_btn);
        logup_btn = findViewById(R.id.Signup_btn);
    }

}

