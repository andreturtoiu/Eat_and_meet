package com.example.andreea.eat_and_meet;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private String emailFrom;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        emailUser = PersonFactory.getInstance().getLoggedUser();
        loggedUser = PersonFactory.getInstance().getUserByEmail(emailUser);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();

                if (item.getItemId()== R.id.events) {
                    Intent t = new Intent(LoggedProfile.this, HomePage.class);
                    startActivity(t);
                }else if (item.getItemId() == R.id.create_event) {
                    Intent t = new Intent(LoggedProfile.this, CreateEvent.class);
                    startActivity(t);

                } else if (item.getItemId() == R.id.search_events) {
                    Intent t = new Intent(LoggedProfile.this,SearchEvents.class);
                    startActivity(t);

                } else if (item.getItemId() == R.id.notifies) {


                } else if (item.getItemId() == R.id.logout) {
                    Intent t = new Intent(LoggedProfile.this, Login_activity.class);
                    startActivity(t);
                }else if (item.getItemId() == R.id.imageView){
                    Intent t = new Intent (LoggedProfile.this, LoggedProfile.class);
                    startActivity(t);
                }

                return false;
            }
        });

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Profilo");
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //Prende la data
        Calendar birth = loggedUser.getBirthdate();
        int dd = birth.get(Calendar.DAY_OF_MONTH);
        int mm = birth.get(Calendar.MONTH);
        int YY = birth.get(Calendar.YEAR);
        String strdate =  String.format("%d",dd)   + "/" + String.format("%d",mm) + "/" + String.format("%d",YY);

        //Informazioni dell'utente loggato
        ImageView img = (ImageView) findViewById(R.id.picProfile);
        img.setImageResource(loggedUser.getFoto());
        TextView name = (TextView) findViewById(R.id.nameProfile);
        name.setText(loggedUser.getName()+ " "+ loggedUser.getSurname());
        TextView birthday = (TextView)findViewById(R.id.birthdateProfile);
        birthday.setText(strdate);
        TextView address = (TextView) findViewById(R.id.addressProfile);
        address.setText(loggedUser.getAddress()+"," + loggedUser.getCity());
        TextView email = (TextView) findViewById(R.id.emailProfile);
        email.setText(loggedUser.getEmail());
        TextView phone = (TextView) findViewById(R.id.telphoneProfile);
        phone.setText(loggedUser.getPhoneNumber());

        //Ratings relativi all'utente loggato
        ArrayList<RatingLoggedProfile> r = loggedUser.getRatings();
        LinearLayout rl = (LinearLayout) findViewById(R.id.aboutYouContainer);

        //Stelle barra generale con la media delle recensioni
        RatingBar ratingBarGeneral = (RatingBar) findViewById(R.id.ratingBarGeneral);
        float stars = 0;  //numero di stelle della barra generale;
        int size = r.size();
        for(int i = 0; i < size; i++){
            stars = stars + r.get(i).getRating();
        }
        float avgStars = stars/r.size();

        ratingBarGeneral.setRating(avgStars);


       //Rating di ciascun utente
       for(int i = 0; i < size; i++){
           emailFrom = r.get(i).getEmailFrom();
           LinearLayout inner = newRatingView(r.get(i),this,new Inner(emailFrom));
           rl.addView(inner);
       }


}

    class Inner implements View.OnClickListener {
        String emailFrom;
        public Inner(String emailFrom){
            this.emailFrom = emailFrom;
        }

        public void onClick(View v) {
            Intent intent = new Intent(LoggedProfile.this, UserProfile.class);
            intent.putExtra("EMAIL_EXTRA",emailFrom);
            startActivity(intent);
        }

    }

    public LinearLayout newRatingView (RatingLoggedProfile r, Context c, View.OnClickListener i ){
        LinearLayout ratingView = (LinearLayout) LayoutInflater.from(c).inflate(R.layout.template_rating, null);
        ratingView.setId(View.generateViewId());

        ImageView img = ratingView.findViewById(R.id.otherUser);
        RatingBar rating = ratingView.findViewById(R.id.ratingBarUser);
        TextView aboutYou = ratingView.findViewById(R.id.contentRating);

        int imgRes = PersonFactory.getInstance().getUserByEmail(r.getEmailFrom()).getFoto();
        img.setImageResource(imgRes);
        img.setId(View.generateViewId());


        rating.setId(View.generateViewId());
        rating.setRating(r.getRating());

        aboutYou.setId(View.generateViewId());
         aboutYou.setText(r.getAboutYou());
        ratingView.setOnClickListener(i);
        return ratingView;
    }
}
