package com.example.andreea.eat_and_meet;

import android.content.Context;
import android.media.Rating;
import android.widget.RatingBar;

/**
 * Created by Andreea on 15/02/2018.
 */

public class RatingLoggedProfile{
    private String emailFrom; //chi ha scritto la recensione
    private String aboutYou;
    int numStars;


    public RatingLoggedProfile(){
        this.setEmailFrom("");
        this.setAboutYou("");
        this.setNumStars(0);

    }

    public  RatingLoggedProfile(String emailFrom,String aboutYou, int numStars){
        setEmailFrom(emailFrom);
        setAboutYou(aboutYou);
        setNumStars(numStars);
    }

    public void setEmailFrom(String emailFrom){this.emailFrom = emailFrom;}
    public String getEmailFrom(){return this.emailFrom;}

    public int getNumStars(){return this.numStars;}
    public void setNumStars(int numStars) {
        this.numStars = numStars;
    }

    public String getAboutYou(){return this.aboutYou;}
    public void setAboutYou(String aboutYou) {this.aboutYou = aboutYou;}

}
