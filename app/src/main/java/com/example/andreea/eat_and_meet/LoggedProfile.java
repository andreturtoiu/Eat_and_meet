package com.example.andreea.eat_and_meet;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
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
    private String emailFrom;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        emailUser = PersonFactory.getInstance().getLoggedUser();
        loggedUser = PersonFactory.getInstance().getUserByEmail(emailUser);

        ImageView img = (ImageView) findViewById(R.id.picProfile);
        if(loggedUser.getFoto()== (R.drawable.logo_2) && loggedUser.getFoto2() == null){
            TextView requestWarning = (TextView) findViewById(R.id.changePhoto);
            String message = "Inserisci la tua foto";
            requestWarning.setText(message);
            requestWarning.setOnClickListener(new HandleRequest(emailUser));
            requestWarning.setGravity(Gravity.CENTER_HORIZONTAL);
        }else if(loggedUser.getFoto() != (R.drawable.logo_2) && loggedUser.getFoto2() == null){
            img.setImageResource(loggedUser.getFoto());
        }else if(loggedUser.getFoto2() != null) {
        BitmapDataObject pic = loggedUser.getFoto2();
        ImageView foto = new ImageView(this);
        img.setImageBitmap(pic.getBitmap());
        foto.setScaleType(ImageView.ScaleType.FIT_XY);
    }
        //Prende la data
        Calendar birth = loggedUser.getBirthdate();
        int dd = birth.get(Calendar.DAY_OF_MONTH);
        int mm = birth.get(Calendar.MONTH);
        int YY = birth.get(Calendar.YEAR);
        String strdate =  String.format("%d",dd)   + "/" + String.format("%d",mm) + "/" + String.format("%d",YY);

        //Informazioni dell'utente loggato

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
    class HandleRequest implements View.OnClickListener{
        String email;
        @Override
        public void onClick(View v){
            Intent addPhoto = new Intent(LoggedProfile.this,AddProfilePhoto.class);
            addPhoto.putExtra("EXTRA_PHOTO",email);
            startActivity(addPhoto);
        }
        public HandleRequest(String email){
            this.email= email;
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
        TextView name = ratingView.findViewById(R.id.nameUser);
        int imgRes = PersonFactory.getInstance().getUserByEmail(r.getEmailFrom()).getFoto();
        img.setImageResource(imgRes);
        img.setId(View.generateViewId());


        rating.setId(View.generateViewId());
        rating.setRating(r.getRating());

        name.setText(PersonFactory.getInstance().getUserByEmail(r.getEmailFrom()).getName() + " " +
        PersonFactory.getInstance().getUserByEmail(r.getEmailFrom()).getSurname());
        name.setId(View.generateViewId());

        aboutYou.setId(View.generateViewId());
        aboutYou.setText(r.getAboutYou());
        ratingView.setOnClickListener(i);
        return ratingView;
    }
}
