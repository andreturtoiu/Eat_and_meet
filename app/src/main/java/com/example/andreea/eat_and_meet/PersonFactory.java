package com.example.andreea.eat_and_meet;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Quontini on 12/02/2018.
 */

public class PersonFactory{

    private static PersonFactory singleton;
    private ArrayList<Person> listaUtenti = new ArrayList<Person>();
    private String loggedUser;

    public static PersonFactory getInstance(){


        if(singleton==null){

            singleton = new PersonFactory();
        }
        return singleton;
    }

    public boolean isUserRegistered(String email){

        boolean isregistered=false;

        for (Person p:listaUtenti){
            if(p.getEmail().equals(email)) {
                isregistered = true;
            }

        }
        return isregistered;
    }

    public ArrayList<Person> getListaUtenti(){return this.listaUtenti;}

    private PersonFactory(){

        Person p;


        //Utente 1
        ArrayList<RatingLoggedProfile> r = new ArrayList<RatingLoggedProfile>();
        ArrayList<Notifications> n = new ArrayList<Notifications>();
        n.add(new Notifications("msta@ium.it", 0, Notifications.RICHIESTA));
        n.add(new Notifications("ajon@ium.it", 1, Notifications.RICHIESTA));
        r.add(new RatingLoggedProfile("rmam@ium.it","dcont@ium.it", "Testo Esperienza1", 3));
        r.add(new RatingLoggedProfile("atur@ium.it","dcont@ium.it", "Testo Esperienza2", 5));
        Calendar c = Calendar.getInstance();
        c.set(1994,1, 3);
        p = new Person("Davide", "Contini", c, "pippopop", "dcont@ium.it", "Via Garibaldi 121", "Santa Giusta", "3485595491", r, n, R.drawable.utente1);
        listaUtenti.add(p);

        //Utente 2
        ArrayList<RatingLoggedProfile> r1 = new ArrayList<RatingLoggedProfile>();
        ArrayList<Notifications> n1 = new ArrayList<Notifications>();
        n1.add(new Notifications("dcont@ium.it", 5, Notifications.RICHIESTA));
        n1.add(new Notifications("ajon@ium.it", 6, Notifications.RICHIESTA));
        r1.add(new RatingLoggedProfile("ajon@ium.it","rmam@ium.it", "Testo Esperienza1", 3));
        r1.add(new RatingLoggedProfile("dcont@ium.it", "rmam@ium.it","Testo Esperienza2", 4));
        Calendar c1 = Calendar.getInstance();
        c1.set(1996,8, 30);
        p = new Person("Riccardo", "Mameli", c1, "AndroidStudio", "rmam@ium.it", "Via Roma 1", "Cagliari", "1234567892", r1, n1,R.drawable.utente2);
        listaUtenti.add(p);

        //Utente 3
        ArrayList<RatingLoggedProfile> r2 = new ArrayList<RatingLoggedProfile>();
        ArrayList<Notifications> n2 = new ArrayList<Notifications>();
        n2.add(new Notifications("msta@ium.it", 8, Notifications.RICHIESTA));
        n2.add(new Notifications("tcai@ium.it", 9, Notifications.RICHIESTA));
        r2.add(new RatingLoggedProfile("ajon@ium.it","atur@ium.it" ,"Testo Esperienza1", 3));
        r2.add(new RatingLoggedProfile("msta@ium.it","atur@ium.it","Testo Esperienza2", 4));
        Calendar c2 = Calendar.getInstance();
        c2.set(1995,5, 13);
        p = new Person("Andreea", "Turtoiu", c2, "12_1211", "atur@ium.it", "Via Dante 32", "Quartu", "3465643633", r2, n2, R.drawable.utente3);
        listaUtenti.add(p);

        //Utente 4
        ArrayList<RatingLoggedProfile> r3 = new ArrayList<RatingLoggedProfile>();
        ArrayList<Notifications> n3 = new ArrayList<Notifications>();
        n3.add(new Notifications("tcai@ium.it", 13, Notifications.RICHIESTA));
        n3.add(new Notifications("ajon@ium.it", 14, Notifications.RICHIESTA));
        r3.add(new RatingLoggedProfile("ajon@ium.it","msta@ium.it", "Testo Esperienza1", 3));
        r3.add(new RatingLoggedProfile("dcont@ium.it", "msta@ium.it","Testo Esperienza2", 5));
        Calendar c3 = Calendar.getInstance();
        c3.set(1989,7, 24);
        p = new Person("Michele", "Staffiere", c3, "Ert453", "msta@ium.it", "Via Vai 67", "Cagliari", "3333233231", r3, n3, R.drawable.utente4);
        listaUtenti.add(p);

        //Utente 5
        ArrayList<RatingLoggedProfile> r4 = new ArrayList<RatingLoggedProfile>();
        ArrayList<Notifications> empty = new ArrayList<Notifications>();
        r4.add(new RatingLoggedProfile("ajon@ium.it","tcai@ium.it", "Testo Esperienza1", 3));
        r4.add(new RatingLoggedProfile("msta@ium.it","tcai@ium.it", "Testo Esperienza2", 4));
        Calendar c4 = Calendar.getInstance();
        c4.set(1999,10, 4);
        p = new Person("Tizio", "Caio", c4, "Ca_t1zio", "tcai@ium.it", "Via Fasulla 123", "Carbonia", "34411231313",r4, empty,R.drawable.utente5);
        listaUtenti.add(p);


        //Utente 6
        ArrayList<RatingLoggedProfile> r5 = new ArrayList<RatingLoggedProfile>();
        r5.add(new RatingLoggedProfile("atur@ium.it","ajon@ium.it", "Testo Esperienza1", 3));
        r5.add(new RatingLoggedProfile("msta@ium.it", "ajon@ium.it","Testo Esperienza2", 4));
        Calendar c5 = Calendar.getInstance();
        c5.set(1980,11, 21);
        p = new Person("Aldo", "Jones", c5, "SkyLab", "ajon@ium.it", "Via Washinton 53", "Pizzo Calabro", "577590838",r5, empty,R.drawable.utente1);
        listaUtenti.add(p);
    }

    public boolean addUser(Person p) {

        boolean flag=true;

        if (!isUserRegistered(p.getEmail())) listaUtenti.add(p);
        else flag=false;

        return flag;
    }

    public Person getUserByEmail(String email){

        Person person = null;

        for (Person p:listaUtenti){
            if(p.getEmail().equals(email)) {
                person=p;
            }

        }
        return person;

    }

    public void setLoggedUser(String email){

        loggedUser = email;
    }

    public String getLoggedUser() {
        return loggedUser;
    }


}
