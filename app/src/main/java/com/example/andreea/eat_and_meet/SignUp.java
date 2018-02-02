package com.example.andreea.eat_and_meet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Andreea on 31/01/2018.
 */





public class SignUp extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        findViewById(R.id.signUpConfirm).setOnClickListener(new SignUp.handleButton());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    class handleButton implements View.OnClickListener {
        public void onClick(View v) {
            Intent intent = new Intent(SignUp.this,Login_activity.class);
            startActivity(intent);
        }
    }

}


