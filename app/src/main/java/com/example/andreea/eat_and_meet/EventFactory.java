package com.example.andreea.eat_and_meet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    public Event getEventById(int id){
        for( Event e : listaEventi)
            if (e.getId() == id)
                return e;
        return null;
    }

    public void editEvent(Event evento){
        int id = evento.getId();
        for(Event e:listaEventi)
            if (e.getId() == id) e.setDescrizione(evento.getDescrizione());
    }

    public void deleteEventById(int id){
        Event position = null;
        for(Event e:this.listaEventi){
            if (e.getId() == id ) position = e;
        }
        this.listaEventi.remove(position);
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

    public ArrayList<Event> searchEventsByFilter(String cucina,String citta,int pranzo_cena){
        ArrayList<Event> filter_cucina = new ArrayList<Event>();
        ArrayList<Event> filter_citta = new ArrayList<Event>();
        ArrayList<Event> filter_pranzo_cena = new ArrayList<Event>();
        //Filtro per cucina;
        if(cucina != null)
            for(Event e:listaEventi)
                if (e.getCucina().equals(cucina))
                    filter_cucina.add(e);
        //Filtro citta
        if(citta != null)
            for(Event e:filter_cucina)
                if (e.getIndirizzo().contains(citta))
                    filter_citta.add(e);
        //Filtro pranzo_cena
        for(Event e:filter_citta)
            if (e.getPranzo_cena()==pranzo_cena)
                filter_pranzo_cena.add(e);
        //Ritorno selezione
        return filter_pranzo_cena;
    }

    public LinearLayout newEventView (Event e, View.OnClickListener ocl, Context c){
        LinearLayout eventView = (LinearLayout) LayoutInflater.from(c).inflate(R.layout.template_event, null);
        TextView title = (TextView) eventView.findViewById(R.id.template_event_title);
        TextView info = (TextView) eventView.findViewById(R.id.template_event_info);
        ImageView photo = (ImageView) eventView.findViewById(R.id.template_event_photo);
        title.setText(e.getTitolo());
        title.setId(View.generateViewId());
        info.setText(e.getDescrizione());
        info.setId(View.generateViewId());
        photo.setImageResource(R.drawable.logo);
        photo.setId(View.generateViewId());
        eventView.setId(View.generateViewId());
        //Aggiungo listener
        eventView.setOnClickListener(ocl);
        return eventView;
    }

    private EventFactory() {
        Event e;
        Calendar c = Calendar.getInstance();
        c.set(2018,1,1,12,0);
        //Evento 1
        e = new Event( 0,0,"Evento  0","Descrizione  0","Italiana",c,Event.PRANZO,"Via  0");
        e.addPartecipante(1);
        e.addPartecipante(2);
        e.addFoto(R.drawable.logo);
        listaEventi.add(e);
        //Evento 2
        e = new Event( 1,0,"Evento  1","Descrizione  1","Cucina  1",c,Event.PRANZO,"Via  1");
        e.addFoto(R.drawable.logo);
        listaEventi.add(e);
        //Evento 3
        e = new Event( 2,0,"Evento  2","Descrizione  2","Cucina  2",c,Event.PRANZO,"Via  2");
        e.addPartecipante(2);
        e.addPartecipante(3);
        e.addFoto(R.drawable.logo);
        listaEventi.add(e);
        //Evento 4
        e = new Event( 3,0,"Evento  3","Descrizione  3","Cucina  3",c,Event.PRANZO,"Via  3");
        e.addFoto(R.drawable.logo);
        listaEventi.add(e);
        //Evento 5
        e = new Event( 4,0,"Evento  4","Descrizione  4","Cucina  4",c,Event.PRANZO,"Via  4");
        e.addPartecipante(1);
        e.addPartecipante(3);
        e.addFoto(R.drawable.logo);
        listaEventi.add(e);
        //Evento 6
        e = new Event( 5,1,"Evento  5","Descrizione  5","Cucina  5",c,Event.PRANZO,"Via  5");
        e.addFoto(R.drawable.logo);
        listaEventi.add(e);
        //Evento 7
        e = new Event( 6,1,"Evento  6","Descrizione  6","Cucina  6",c,Event.PRANZO,"Via  6");
        e.addPartecipante(0);
        e.addPartecipante(2);
        e.addPartecipante(3);
        e.addFoto(R.drawable.logo);
        listaEventi.add(e);
        //Evento 8
        e = new Event( 7,1,"Evento  7","Descrizione  7","Cucina  7",c,Event.PRANZO,"Via  7");
        e.addFoto(R.drawable.logo);
        listaEventi.add(e);
        //Evento 9
        e = new Event( 8,2,"Evento  8","Descrizione  8","Cucina  8",c,Event.PRANZO,"Via  8");
        e.addFoto(R.drawable.logo);
        listaEventi.add(e);
        //Evento 10
        e = new Event( 9,2,"Evento  9","Descrizione  9","Cucina  9",c,Event.PRANZO,"Via  9");
        e.addPartecipante(0);
        e.addPartecipante(3);
        e.addFoto(R.drawable.logo);
        listaEventi.add(e);
        //Evento 11
        e = new Event(10,2,"Evento 10","Descrizione 10","Cucina 10",c,Event.PRANZO,"Via 10");
        e.addPartecipante(1);
        e.addPartecipante(3);
        e.addFoto(R.drawable.logo);
        listaEventi.add(e);
        //Evento 12
        e = new Event(11,2,"Evento 11","Descrizione 11","Cucina 11",c,Event.PRANZO,"Via 11");
        e.addFoto(R.drawable.logo);
        listaEventi.add(e);
        //Evento 13
        e = new Event(12,3,"Evento 12","Descrizione 12","Cucina 12",c,Event.PRANZO,"Via 12");
        e.addPartecipante(0);
        e.addPartecipante(1);
        e.addFoto(R.drawable.logo);
        listaEventi.add(e);
        //Evento 14
        e = new Event(13,3,"Evento 13","Descrizione 13","Cucina 13",c,Event.PRANZO,"Via 13");
        e.addPartecipante(0);
        e.addPartecipante(2);
        e.addFoto(R.drawable.logo);
        listaEventi.add(e);
        //Evento 15
        e = new Event(14,3,"Evento 14","Descrizione 14","Cucina 14",c,Event.PRANZO,"Via 14");
        e.addPartecipante(1);
        e.addPartecipante(3);
        e.addFoto(R.drawable.logo);
        listaEventi.add(e);
        //Evento 16
        e = new Event(15,3,"Evento 15","Descrizione 15","Cucina 15",c,Event.PRANZO,"Via 15");
        e.addFoto(R.drawable.logo);
        listaEventi.add(e);
    }

}
