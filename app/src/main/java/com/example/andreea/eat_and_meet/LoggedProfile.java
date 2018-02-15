package com.example.andreea.eat_and_meet;


import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by Andreea on 12/02/2018.
 */


public class LoggedProfile  extends AppCompatActivity{

    private String emailUser;
    private Person loggedUser;
    private ArrayList<String> aboutYou= new ArrayList<String>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        emailUser = PersonFactory.getInstance().getLoggedUser();
        loggedUser = PersonFactory.getInstance().getUserByEmail(emailUser);

        Calendar birth = loggedUser.getBirthdate();


        int dd = birth.get(Calendar.DAY_OF_MONTH);
        int mm = birth.get(Calendar.MONTH);
        int YY = birth.get(Calendar.YEAR);
        String strdate =  String.format("%d",dd)   + "/" + String.format("%d",mm) + "/" + String.format("%d",YY);

        ImageView img = (ImageView) findViewById(R.id.picProfile);
        img.setImageResource(loggedUser.getFoto());
        TextView name = (TextView) findViewById(R.id.nameProfile);
        name.setText(loggedUser.getName()+loggedUser.getSurname());

        TextView birthday = (TextView)findViewById(R.id.birthdateProfile);
        birthday.setText(strdate);

        TextView address = (TextView) findViewById(R.id.addressProfile);
        address.setText(loggedUser.getAddress()+"," + loggedUser.getCity());


        TextView email = (TextView) findViewById(R.id.emailProfile);
        email.setText(loggedUser.getEmail());

        TextView phone = (TextView) findViewById(R.id.telphoneProfile);
        phone.setText(loggedUser.getPhoneNumber());


        ArrayList<RatingLoggedProfile> r = loggedUser.getRatings();
        LinearLayout rl = (LinearLayout) findViewById(R.id.aboutYouContainer);

        RatingBar ratingBarGeneral = (RatingBar) findViewById(R.id.ratingBarGeneral);
        float stars = 0;  //numero di stelle della barra generale;
        int size = r.size();
        for(int i = 0; i < size; i++){
            stars = stars + r.get(i).getNumStars();
        }
        float avgStars = stars/r.size();

        ratingBarGeneral.setRating(avgStars);

       for(int i = 0; i < size; i++){
            LinearLayout inner = newRatingView(r.get(i),this);
            rl.addView(inner);
        }

    }

    public LinearLayout newRatingView (RatingLoggedProfile r, Context c){
        LinearLayout ratingView = (LinearLayout) LayoutInflater.from(c).inflate(R.layout.template_rating, null);
        ratingView.setId(View.generateViewId());

        ImageView img = ratingView.findViewById(R.id.otherUser);
        RatingBar rating = ratingView.findViewById(R.id.ratingBarUser);
        TextView aboutYou = ratingView.findViewById(R.id.contentRating);

        img.setImageResource(R.drawable.utente1);
        img.setId(View.generateViewId());

        rating.setId(View.generateViewId());
        rating.setRating(r.getNumStars());

        aboutYou.setId(View.generateViewId());
        aboutYou.setText(r.getAboutYou());

        return ratingView;
    }
}
