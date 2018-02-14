package com.example.andreea.eat_and_meet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by Andreea on 12/02/2018.
 */


public class LoggedProfile  extends AppCompatActivity{

    private String emailUser;
    private Person loggedUser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        emailUser = PersonFactory.getInstance().getLoggedUser();
        loggedUser = PersonFactory.getInstance().getUserByEmail(emailUser);

        Calendar birth = loggedUser.getBirthdate();


        String strdate = null;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        if (birth != null) {
            strdate = sdf.format(birth.getTime());
        }

        TextView name = (TextView) findViewById(R.id.nameProfile);
        name.setText(loggedUser.getName()+loggedUser.getSurname());

        TextView birthday = (TextView)findViewById(R.id.birthdateProfile);
        birthday.setText(strdate + "Data sbagliata");

        TextView address = (TextView) findViewById(R.id.addressProfile);
        address.setText(loggedUser.getAddress()+"," + loggedUser.getCity());


        TextView email = (TextView) findViewById(R.id.emailProfile);
        email.setText(loggedUser.getEmail());

        TextView phone = (TextView) findViewById(R.id.telphoneProfile);
        phone.setText(loggedUser.getPhoneNumber());


    }
}
