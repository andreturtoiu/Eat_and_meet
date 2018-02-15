package com.example.andreea.eat_and_meet;


import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Quontini on 12/02/2018.
 */

public class PersonFactory {

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
        //Utente 1
        p = new Person("Davide", "Contini", c, "pippopop", "dcont@ium.it", "Via Garibaldi 121", "Santa Giusta", "3485595491", R.drawable.utente1);
        listaUtenti.add(p);
        //Utente 2
        c.set(1996,8, 30);
        p = new Person("Riccardo", "Mameli", c, "AndroidStudio", "rmam@ium.it", "Via Roma 1", "Cagliari", "1234567892", R.drawable.utente2);
        listaUtenti.add(p);
        //Utente 3
        c.set(1995,5, 13);
        p = new Person("Andreea", "Turtoiu", c, "12_1211", "atur@ium.it", "Via Dante 32", "Quartu", "3465643633", R.drawable.utente3);
        listaUtenti.add(p);
        //Utente 4
        c.set(1989,7, 24);
        p = new Person("Michele", "Staffiere", c, "Ert453", "msta@ium.it", "Via Vai 67", "Cagliari", "3333233231", R.drawable.utente4);
        listaUtenti.add(p);
        //Utente 5
        c.set(1999,10, 4);
        p = new Person("Tizio", "Caio", c, "Ca_t1zio", "tcai@ium.it", "Via Fasulla 123", "Carbonia", "34411231313", R.drawable.utente5);
        listaUtenti.add(p);
        //Utente 6
        c.set(1980,11, 21);
        p = new Person("Aldo", "Jones", c, "SkyLab", "ajon@ium.it", "Via Washinton 53", "Pizzo Calabro", "577590838", R.drawable.utente3);
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
