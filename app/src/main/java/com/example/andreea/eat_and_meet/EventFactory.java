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
        for(int i = 0;i<20;i++){
            Event e = new Event();
            e.setId(i);
            e.setUser((int) (Math.random()*10));
            e.setTitolo("Titolo "+i);
            e.setDescrizione("Descrizione "+i);
            e.setCucina("Cucina "+i);
            e.setIndirizzo("Indirizzo "+i);
            e.setData(2018,1,1);
            int partecipanti = (int) (Math.random()*5);
            for(int j = 0;j<partecipanti;j++)
                e.addPartecipante(j);
            for(int j = 0;j<10;j++)
                e.addFoto(R.drawable.logo);
            listaEventi.add(e);
        }
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
