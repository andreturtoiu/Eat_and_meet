package com.example.andreea.eat_and_meet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by Michele on 03/02/2018.
 */

public class EventFactory {
    //Pattern Design Singleton
    private static EventFactory singleton;
    private String connectionString;

    public static EventFactory getInstance() {
        if (singleton == null) {
            singleton = new EventFactory();
        }
        return singleton;
    }

    private ArrayList<Event> listaEventi = new ArrayList<Event>();

    private EventFactory() {
        Event e;
        e = new Event( 0,0,"Evento  0","Descrizione  0","Cucina  0","01/01/2018","12:00","Via  0");
        e.addPartecipante(1);
        e.addPartecipante(2);
        e.addFoto(R.drawable.logo);
        listaEventi.add(e);
        e = new Event( 1,0,"Evento  1","Descrizione  1","Cucina  1","01/01/2018","12:00","Via  1");
        e.addFoto(R.drawable.logo);
        listaEventi.add(e);
        e = new Event( 2,0,"Evento  2","Descrizione  2","Cucina  2","01/01/2018","12:00","Via  2");
        e.addPartecipante(2);
        e.addPartecipante(3);
        e.addFoto(R.drawable.logo);
        listaEventi.add(e);
        e = new Event( 3,0,"Evento  3","Descrizione  3","Cucina  3","01/01/2018","12:00","Via  3");
        e.addFoto(R.drawable.logo);
        listaEventi.add(e);
        e = new Event( 4,0,"Evento  4","Descrizione  4","Cucina  4","01/01/2018","12:00","Via  4");
        e.addPartecipante(1);
        e.addPartecipante(3);
        e.addFoto(R.drawable.logo);
        listaEventi.add(e);
        e = new Event( 5,1,"Evento  5","Descrizione  5","Cucina  5","01/01/2018","12:00","Via  5");
        e.addFoto(R.drawable.logo);
        listaEventi.add(e);
        e = new Event( 6,1,"Evento  6","Descrizione  6","Cucina  6","01/01/2018","12:00","Via  6");
        e.addPartecipante(0);
        e.addPartecipante(2);
        e.addPartecipante(3);
        e.addFoto(R.drawable.logo);
        listaEventi.add(e);
        e = new Event( 7,1,"Evento  7","Descrizione  7","Cucina  7","01/01/2018","12:00","Via  7");
        e.addFoto(R.drawable.logo);
        listaEventi.add(e);
        e = new Event( 8,2,"Evento  8","Descrizione  8","Cucina  8","01/01/2018","12:00","Via  8");
        e.addFoto(R.drawable.logo);
        listaEventi.add(e);
        e = new Event( 9,2,"Evento  9","Descrizione  9","Cucina  9","01/01/2018","12:00","Via  9");
        e.addPartecipante(0);
        e.addPartecipante(3);
        e.addFoto(R.drawable.logo);
        listaEventi.add(e);
        e = new Event(10,2,"Evento 10","Descrizione 10","Cucina 10","01/01/2018","12:00","Via 10");
        e.addPartecipante(1);
        e.addPartecipante(3);
        e.addFoto(R.drawable.logo);
        listaEventi.add(e);
        e = new Event(11,2,"Evento 11","Descrizione 11","Cucina 11","01/01/2018","12:00","Via 11");
        e.addFoto(R.drawable.logo);
        listaEventi.add(e);
        e = new Event(12,3,"Evento 12","Descrizione 12","Cucina 12","01/01/2018","12:00","Via 12");
        e.addPartecipante(0);
        e.addPartecipante(1);
        e.addFoto(R.drawable.logo);
        listaEventi.add(e);
        e = new Event(13,3,"Evento 13","Descrizione 13","Cucina 13","01/01/2018","12:00","Via 13");
        e.addPartecipante(0);
        e.addPartecipante(2);
        e.addFoto(R.drawable.logo);
        listaEventi.add(e);
        e = new Event(14,3,"Evento 14","Descrizione 14","Cucina 14","01/01/2018","12:00","Via 14");
        e.addPartecipante(1);
        e.addPartecipante(3);
        e.addFoto(R.drawable.logo);
        listaEventi.add(e);
        e = new Event(15,3,"Evento 15","Descrizione 15","Cucina 15","01/01/2018","12:00","Via 15");
        e.addFoto(R.drawable.logo);
        listaEventi.add(e);
    }

    public Event getEventById(int id){
        for( Event e : listaEventi)
            if (e.getId() == id)
                return e;
        return null;
    }

    public ArrayList<Event> getEventList(){
        return this.listaEventi;
    }

    public ArrayList<Event> getEventsByUser(int userID){
        ArrayList<Event> l = new ArrayList<>();
        for(Event e:listaEventi){
            if (e.getUser() == userID){
                l.add(e);
            }
        }
        return l;
    }

    public ArrayList<Event> getBookingsByUser(int userID){ //PLACEHOLDER
        ArrayList<Event> l = new ArrayList<>();
        for(Event e:listaEventi){
            if (e.isBooked(userID)){
                l.add(e);
            }
        }
        return l;
    }

}
