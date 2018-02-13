package com.example.andreea.eat_and_meet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Andreea on 30/01/2018.
 */

public class Login_activity extends AppCompatActivity {

    PersonFactory users = PersonFactory.getInstance();
    EditText email, password;
    Button loginbutton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.signUpLink).setOnClickListener(new handleButton1());
        email = (EditText) findViewById(R.id.userEmail);
        password = (EditText) findViewById(R.id.userPassword);

        loginbutton = (Button) findViewById(R.id.loginButton);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInput()){

                    Intent intent2 = new Intent(Login_activity.this, HomePage.class);
                    startActivity(intent2);

                }
            }
        });

    }

    private boolean checkInput() {

        int errors=0;

        if(email.getText() == null || email.getText().length()==0){

            email.setError("Inserire la propria email");
            errors++;


        }
        else {
            email.setError(null);
        }

        if(password.getText() == null || password.getText().length()==0){

            email.setError("Inserire la propria password");
            errors++;


        }
        else {
            password.setError(null);
        }

        if(errors==0) {
            Person person = users.getUserByEmail(email.getText().toString());
            if(person!=null){

                email.setError(null);
                if(person.getPassword().equals(password.getText().toString())) {

                    users.setLoggedUser(person.getEmail());
                    password.setError(null);
                }
                else {
                    errors++;
                    password.setError("Password Errata");

                }
            }
            else {

                email.setError("Email Errata");
                errors++;

            }

        }

        return errors==0;
    }

    class handleButton1 implements View.OnClickListener {
        public void onClick(View v) {
            Intent intent = new Intent(Login_activity.this, SignUp.class);
            startActivity(intent);

        }
    }
/*
    class handleButton2 implements View.OnClickListener {
        public void onClick(View v) {

            Intent intent2 = new Intent(Login_activity.this, HomePage.class);
            startActivity(intent2);
        }
    }
*/

}
