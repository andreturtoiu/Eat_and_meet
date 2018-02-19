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
        Calendar c = Calendar.getInstance();
        c.set(1994,1, 3);
        ArrayList<RatingLoggedProfile> r = new ArrayList<RatingLoggedProfile>();
        r.add(new RatingLoggedProfile("rmam@ium.it","dcont@ium.it", "Non esiste nulla di più bello che essere accolti da un sorriso e da un buon cibo!! Serata dove si è subito creata un' atmosfera piacevole fra i commensali.", 4));
        r.add(new RatingLoggedProfile("atur@ium.it","dcont@ium.it", "Davide è un cuoco e padrone di casa eccezionale, il menu era veramente eccellente e così le birre artigianali in abbinamento. Ambiente molto accogliente e famigliare, compagnia piacevole e cordiale. Una serata da ricordare!", 5));
        //Utente 1
        p = new Person("Davide", "Contini", c, "pippopop", "dcont@ium.it", "Via Garibaldi 121", "Santa Giusta", "3485595491",r, R.drawable.utente1);
        listaUtenti.add(p);

        //Utente 2
        ArrayList<RatingLoggedProfile> r1 = new ArrayList<RatingLoggedProfile>();
        r1.add(new RatingLoggedProfile("ajon@ium.it","rmam@ium.it", "É stata una serata molto piacevole e ricca di argomenti interessanti . Beh, quanto al cibo : eccezionale!!!", 4));
        r1.add(new RatingLoggedProfile("dcont@ium.it", "rmam@ium.it","Bellissima atmosfera in una casa accogliente. Riccardo è un ottimo padrone di casa.", 4));
        Calendar c1 = Calendar.getInstance();
        c1.set(1996,8, 30);
        p = new Person("Riccardo", "Mameli", c1, "AndroidStudio", "rmam@ium.it", "Via Roma 1", "Cagliari", "1234567892",r1,R.drawable.utente2);
        listaUtenti.add(p);
        //Utente 3

        ArrayList<RatingLoggedProfile> r2 = new ArrayList<RatingLoggedProfile>();
        r2.add(new RatingLoggedProfile("ajon@ium.it","atur@ium.it" ,"Andreea è una perfetta padrona di casa oltre ad essere un ottima chef. La qualità del cibo servito e la sua gentilezza hanno reso quest'esperienza davvero piacevole. Verdadera cocina Mexicana!! Alla prossima!!!", 5));
        r2.add(new RatingLoggedProfile("msta@ium.it","atur@ium.it","Quanto è divertente Andreea e quanto è brava!!! Abbiamo divorato i tuoi antipasti al gorgonzola e i tuoi deliziosi ravioli! I tuoi racconti ci hanno fatto fare un piacevole tuffo nella storia della Roma Antica e in quella degli anni 50! Grazie di tutto.Ci rivedremo presto!", 4));
        Calendar c2 = Calendar.getInstance();
        c2.set(1995,5, 13);
        p = new Person("Andreea", "Turtoiu", c2, "12_1211", "atur@ium.it", "Via Dante 32", "Quartu", "3465643633",r2,R.drawable.utente3);
        listaUtenti.add(p);

        //Utente 4
        ArrayList<RatingLoggedProfile> r3 = new ArrayList<RatingLoggedProfile>();
        r3.add(new RatingLoggedProfile("ajon@ium.it","msta@ium.it", "Seconda volta a casa di Michele e mi sono trovata benissimo. La cena era molto buona, il dessert particolarissimo e tutto abbondante, vino compreso. La compagnia davvero piacevole e Michele è bravissimo a mettere tutti a proprio agio. Ci rivediamo presto", 4));
        r3.add(new RatingLoggedProfile("dcont@ium.it", "msta@ium.it","Serata deliziosa, cucina preparata con grande competenza. Compagnia piacevole, allegria spensierata. Grazie Michele, perdonami per il ritardo. Spero di recuperare una bella serata insieme a te.", 5));
        Calendar c3 = Calendar.getInstance();
        c3.set(1989,7, 24);
        p = new Person("Michele", "Staffiere", c3, "Ert453", "msta@ium.it", "Via Vai 67", "Cagliari", "3333233231",r3,R.drawable.utente4);
        listaUtenti.add(p);

        //Utente 5
        ArrayList<RatingLoggedProfile> r4 = new ArrayList<RatingLoggedProfile>();
        r4.add(new RatingLoggedProfile("ajon@ium.it","tcai@ium.it", "Testo Esperienza1", 3));
        r4.add(new RatingLoggedProfile("msta@ium.it","tcai@ium.it", "Testo Esperienza2", 4));
        Calendar c4 = Calendar.getInstance();
        c4.set(1999,10, 4);
        p = new Person("Tizio", "Caio", c4, "Ca_t1zio", "tcai@ium.it", "Via Fasulla 123", "Carbonia", "34411231313",r4,R.drawable.utente5);
        listaUtenti.add(p);


        //Utente 6
        ArrayList<RatingLoggedProfile> r5 = new ArrayList<RatingLoggedProfile>();
        r5.add(new RatingLoggedProfile("atur@ium.it","ajon@ium.it", "Serata bella e divertente come ogni cena da Aldo, compagnia piacevolissima e già appena usciti la curiosità di sapere: a quando la prossima cena!!??", 4));
        r5.add(new RatingLoggedProfile("msta@ium.it", "ajon@ium.it","Piacevolissima serata in buona compagnia e cena squisita. grazie Aldo!", 4));
        Calendar c5 = Calendar.getInstance();
        c5.set(1980,11, 21);
        p = new Person("Aldo", "Jones", c5, "SkyLab", "ajon@ium.it", "Via Washinton 53", "Pizzo Calabro", "577590838",r5,R.drawable.utente1);
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
