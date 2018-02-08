package com.example.andreea.eat_and_meet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Andreea on 30/01/2018.
 */

public class Login_activity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.signUpLink).setOnClickListener(new handleButton1());
        findViewById(R.id.loginButton).setOnClickListener(new handleButton2());

    }

    class handleButton1 implements View.OnClickListener {
        public void onClick(View v) {
            Intent intent = new Intent(Login_activity.this, SignUp.class);
            startActivity(intent);

        }
    }

    class handleButton2 implements View.OnClickListener {
        public void onClick(View v) {

            Intent intent2 = new Intent(Login_activity.this, NavigationMenu.class);
            startActivity(intent2);
        }
    }


}
