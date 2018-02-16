package com.example.andreea.eat_and_meet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Andreea on 16/02/2018.
 */

public class UserProfile extends AppCompatActivity{
    String emailUser;
    Person user;
    String emailFrom;
    RatingBar ratingBarGeneral2;
    EditText inputText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_user_profile);

        Intent intent = getIntent();
        emailUser = intent.getStringExtra("EMAIL_EXTRA");
        user = PersonFactory.getInstance().getUserByEmail(emailUser);

        //Prende la data
        Calendar birth = user.getBirthdate();
        int dd = birth.get(Calendar.DAY_OF_MONTH);
        int mm = birth.get(Calendar.MONTH);
        int YY = birth.get(Calendar.YEAR);
        String strdate =  String.format("%d",dd)   + "/" + String.format("%d",mm) + "/" + String.format("%d",YY);

     //Informazioni dell'utente
        ImageView img = (ImageView) findViewById(R.id.picProfile);
        img.setImageResource(user.getFoto());
        TextView name = (TextView) findViewById(R.id.nameProfile);
        name.setText(user.getName()+ " "+ user.getSurname());
        TextView birthday = (TextView)findViewById(R.id.birthdateProfile);
        birthday.setText(strdate);
        TextView address = (TextView) findViewById(R.id.addressProfile);
        address.setText(user.getAddress()+"," + user.getCity());
        TextView email = (TextView) findViewById(R.id.emailProfile);
        email.setText(user.getEmail());
        TextView phone = (TextView) findViewById(R.id.telphoneProfile);
        phone.setText(user.getPhoneNumber());

        //Ratings relativi all'utente
        ArrayList<RatingLoggedProfile> r = user.getRatings();
        LinearLayout rl = (LinearLayout) findViewById(R.id.aboutYouContainer);

        //Stelle barra generale con la media delle recensioni
        RatingBar ratingBarGeneral = (RatingBar) findViewById(R.id.ratingBarGeneral);
        final float[] stars = {0};  //numero di stelle della barra generale;
        int size = r.size();
        for(int i = 0; i < size; i++){
            stars[0] = stars[0] + r.get(i).getNumStars();
        }
        float avgStars = stars[0] /r.size();

        ratingBarGeneral.setRating(avgStars);


        //Rating di ciascun utente
        for(int i = 0; i < size; i++){
            emailFrom = r.get(i).getEmailFrom();
            LinearLayout inner = newRatingView(r.get(i),this,new Inner(emailFrom));
            rl.addView(inner);

        }


        ratingBarGeneral2 = (RatingBar) findViewById(R.id.ratingBarGeneral2);
        inputText = (EditText) findViewById(R.id.writeYourExp);
        Button invia = (Button) findViewById(R.id.button);


        ratingBarGeneral2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            int stars;
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                stars = ratingBarGeneral2.getNumStars();
                ratingBarGeneral2.setNumStars(stars);

            }
        });





        invia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   if(checkRate(ratingBarGeneral2,inputText)){
                    int stars = ratingBarGeneral2.getNumStars();
                    String text = inputText.getText().toString();
                    RatingLoggedProfile r = new RatingLoggedProfile(emailFrom,emailUser,text,stars);
                    updateRatings(user,r);
               // }
            }
        });




    }


    public class Stars{
        int stars;
        public Stars(int stars){
            this.stars = stars;
        }
    }

    private void updateRatings(Person p,RatingLoggedProfile r){
        p.addRatings(r);

    }
    public boolean checkRate(RatingBar r , EditText t){
        if(r.getRating() ==0 &&  t.getText() == null){
            return false;
        }
        if(r.getRating() == 0  || t.getText() == null){
            return false;
        }

        if(r.getRating() != 0 &&  t.getText() != null){
            return true;
        }
        return true;
    }




    class Inner implements View.OnClickListener {
        String emailFrom;
        public Inner(String emailFrom){
            this.emailFrom = emailFrom;
        }

        public void onClick(View v) {
            Intent intent = new Intent(UserProfile.this, UserProfile.class);
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
        rating.setRating(r.getNumStars());

        aboutYou.setId(View.generateViewId());
        aboutYou.setText(r.getAboutYou());
        ratingView.setOnClickListener(i);
        return ratingView;
    }


}