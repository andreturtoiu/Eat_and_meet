package com.example.andreea.eat_and_meet;

import java.util.ArrayList;

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
        for(int i = 0;i<10;i++){
            Event e = new Event();
            e.setId(i);
            e.setTitolo("Titolo"+i);
            e.setDescrizione("Descrizione"+i);
            listaEventi.add(e);
        }
    }

    public Event getEventById(int id){
        return listaEventi.get(id);
    }
    public ArrayList<Event> getEventList(){
        return this.listaEventi;
    }

}
