package com.example.andreea.eat_and_meet;

import android.content.Context;
import android.media.Rating;
import android.widget.RatingBar;

/**
 * Created by Andreea on 15/02/2018.
 */

public class RatingLoggedProfile{
    private String emailFrom; //chi ha scritto la recensione
    private String emailTo; //Sul profilo di chi
    private String aboutYou;
    float rating;


    public RatingLoggedProfile(){
        this.setEmailFrom("");
        this.setEmailTo("");
        this.setAboutYou("");
        this.setRating(0);

    }

    public  RatingLoggedProfile(String emailFrom,String emailTo,String aboutYou, float rating){
        setEmailFrom(emailFrom);
        setEmailTo(emailTo);
        setAboutYou(aboutYou);
        setRating(rating);
    }

    public void setEmailFrom(String emailFrom){this.emailFrom = emailFrom;}
    public String getEmailFrom(){return this.emailFrom;}

    public void setEmailTo(String emailTo){this.emailTo = emailTo;}
    public String getEmailTo(){return this.emailTo;}

    public float getRating(){return this.rating;}
    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getAboutYou(){return this.aboutYou;}
    public void setAboutYou(String aboutYou) {this.aboutYou = aboutYou;}

}
