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

    public void unSubscribeFromEvent(int idEvent,int idUser){
        for(Event e:listaEventi){
            if (e.getId()==idEvent){
                e.unSubscribe(idUser);
            }
        }
    }

    public void SubscribeToEvent(int idEvent,int idUser){
        for(Event e:listaEventi){
            if (e.getId()==idEvent){
                e.Subscribe(idUser);
            }
        }
    }

    public boolean isEventFull(int idEvent){
        for (Event e:listaEventi){
            if (e.getId()==idEvent){
                e.isFull();
            }
        }
        return true;
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
        ArrayList<Event> filter_free = new ArrayList<Event>();
        //Filtro per cucina;
        if(cucina != null)
            for(Event e:listaEventi)
                if (e.getCucina().equals(cucina) || cucina.equals("Nessuna Preferenza"))
                    filter_cucina.add(e);
        //Filtro citta
        if(citta != null)
            for(Event e:filter_cucina)
                if (e.getIndirizzo().contains(citta))
                    filter_citta.add(e);
        //Filtro pranzo_cena
        for(Event e:filter_citta)
            if (e.getPranzo_cena()==pranzo_cena || pranzo_cena == -1)
                filter_pranzo_cena.add(e);
        //Filtro liberi
        for(Event e:filter_pranzo_cena){
            if(!e.isFull())
                filter_free.add(e);
        }
        //Ritorno selezione
        return filter_free;
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
        //Evento 0
        e = new Event();
        e.setUser(0);
        e.setTitolo("Evento 0");
        e.setDescrizione("Descrizione 0");
        e.setCucina("Italiana");
        e.setPranzo_cena(Event.PRANZO);
        e.setIndirizzo("Via 0");
        c.set(2018,1,1,12,0);
        e.setData(c);
        e.addPartecipante(1);
        e.addPartecipante(2);
        e.addFoto(R.drawable.logo);
        e.setMaxBookings(4);
        listaEventi.add(e);
        //Evento 1
        e = new Event();
        e.setUser(0);
        e.setTitolo("Evento 1");
        e.setDescrizione("Descrizione 1");
        e.setCucina("Indiana");
        e.setPranzo_cena(Event.CENA);
        e.setIndirizzo("Via 1");
        c.set(2018,1,1,19,0);
        e.setData(c);
        e.addFoto(R.drawable.logo);
        e.setMaxBookings(4);
        listaEventi.add(e);
        //Evento 2
        e = new Event();
        e.setUser(0);
        e.setTitolo("Evento 2");
        e.setDescrizione("Descrizione  2");
        e.setCucina("Francese");
        e.setPranzo_cena(Event.PRANZO);
        e.setIndirizzo("Via 2");
        c.set(2018,1,1,12,0);
        e.setData(c);
        e.addPartecipante(2);
        e.addPartecipante(3);
        e.addFoto(R.drawable.logo);
        e.setMaxBookings(4);
        listaEventi.add(e);
        //Evento 3
        e = new Event();
        e.setUser(0);
        e.setTitolo("Evento 3");
        e.setDescrizione("Descrizione  3");
        e.setCucina("Cinese");
        e.setPranzo_cena(Event.CENA);
        e.setIndirizzo("Via 3");
        c.set(2018,1,1,19,0);
        e.setData(c);
        e.addFoto(R.drawable.logo);
        e.setMaxBookings(4);
        listaEventi.add(e);
        //Evento 4
        e = new Event();
        e.setUser(0);
        e.setTitolo("Evento 4");
        e.setDescrizione("Descrizione  4");
        e.setCucina("Tedesca");
        e.setPranzo_cena(Event.PRANZO);
        e.setIndirizzo("Via 4");
        c.set(2018,1,1,12,0);
        e.setData(c);
        e.addPartecipante(1);
        e.addPartecipante(3);
        e.addFoto(R.drawable.logo);
        e.setMaxBookings(4);
        listaEventi.add(e);
        //Evento 5
        e = new Event();
        e.setUser(1);
        e.setTitolo("Evento 5");
        e.setDescrizione("Descrizione 5");
        e.setCucina("Messicana");
        e.setPranzo_cena(Event.CENA);
        e.setIndirizzo("Via 5");
        c.set(2018,1,1,19,0);
        e.setData(c);
        e.addFoto(R.drawable.logo);
        e.setMaxBookings(4);
        listaEventi.add(e);
        //Evento 6
        e = new Event();
        e.setUser(1);
        e.setTitolo("Evento 6");
        e.setDescrizione("Descrizione 6");
        e.setCucina("Giapponese");
        e.setPranzo_cena(Event.PRANZO);
        e.setIndirizzo("Via 6");
        c.set(2018,1,1,12,0);
        e.setData(c);
        e.addPartecipante(0);
        e.addPartecipante(2);
        e.addPartecipante(3);
        e.addFoto(R.drawable.logo);
        e.setMaxBookings(4);
        listaEventi.add(e);
        //Evento 7
        e = new Event();
        e.setUser(1);
        e.setTitolo("Evento 7");
        e.setDescrizione("Descrizione 7");
        e.setCucina("Italiana");
        e.setPranzo_cena(Event.CENA);
        e.setIndirizzo("Via 7");
        c.set(2018,1,1,19,0);
        e.setData(c);
        e.addFoto(R.drawable.logo);
        e.setMaxBookings(4);
        listaEventi.add(e);
        //Evento 8
        e = new Event();
        e.setUser(2);
        e.setTitolo("Evento 8");
        e.setDescrizione("Descrizione 8");
        e.setCucina("Francese");
        e.setPranzo_cena(Event.PRANZO);
        e.setIndirizzo("Via 8");
        c.set(2018,1,1,12,0);
        e.setData(c);
        e.addFoto(R.drawable.logo);
        e.setMaxBookings(4);
        listaEventi.add(e);
        //Evento 9
        e = new Event();
        e.setUser(2);
        e.setTitolo("Evento 9");
        e.setDescrizione("Descrizione 9");
        e.setCucina("Indiana");
        e.setPranzo_cena(Event.CENA);
        e.setIndirizzo("Via 9");
        c.set(2018,1,1,19,0);
        e.setData(c);
        e.addPartecipante(0);
        e.addPartecipante(3);
        e.addFoto(R.drawable.logo);
        e.setMaxBookings(4);
        listaEventi.add(e);
        //Evento 10
        e = new Event();
        e.setUser(2);
        e.setTitolo("Evento 10");
        e.setDescrizione("Descrizione 10");
        e.setCucina("Cinese");
        e.setPranzo_cena(Event.PRANZO);
        e.setIndirizzo("Via 10");
        c.set(2018,1,1,12,0);
        e.setData(c);
        e.addPartecipante(1);
        e.addPartecipante(3);
        e.addFoto(R.drawable.logo);
        e.setMaxBookings(4);
        listaEventi.add(e);
        //Evento 11
        e = new Event();
        e.setUser(2);
        e.setTitolo("Evento 11");
        e.setDescrizione("Descrizione 11");
        e.setCucina("Giapponese");
        e.setPranzo_cena(Event.CENA);
        e.setIndirizzo("Via 11");
        c.set(2018,1,1,19,0);
        e.setData(c);
        e.addFoto(R.drawable.logo);
        e.setMaxBookings(4);
        listaEventi.add(e);
        //Evento 12
        e = new Event();
        e.setUser(2);
        e.setTitolo("Evento 12");
        e.setDescrizione("Descrizione 12");
        e.setCucina("Tedesca");
        e.setPranzo_cena(Event.PRANZO);
        e.setIndirizzo("Via 12");
        c.set(2018,1,1,12,0);
        e.setData(c);
        e.addPartecipante(0);
        e.addPartecipante(1);
        e.addFoto(R.drawable.logo);
        e.setMaxBookings(4);
        listaEventi.add(e);
        //Evento 13
        e = new Event();
        e.setUser(3);
        e.setTitolo("Evento 13");
        e.setDescrizione("Descrizione 13");
        e.setCucina("Messicana");
        e.setPranzo_cena(Event.CENA);
        e.setIndirizzo("Via 13");
        c.set(2018,1,1,19,0);
        e.setData(c);
        e.addPartecipante(0);
        e.addPartecipante(2);
        e.addFoto(R.drawable.logo);
        e.setMaxBookings(4);
        listaEventi.add(e);
        //Evento 14
        e = new Event();
        e.setUser(3);
        e.setTitolo("Evento 14");
        e.setDescrizione("Descrizione 14");
        e.setCucina("Italiana");
        e.setPranzo_cena(Event.PRANZO);
        e.setIndirizzo("Via 14");
        c.set(2018,1,1,12,0);
        e.setData(c);
        e.addPartecipante(1);
        e.addPartecipante(3);
        e.addFoto(R.drawable.logo);
        e.setMaxBookings(4);
        listaEventi.add(e);
        //Evento 15
        e = new Event();
        e.setUser(3);
        e.setTitolo("Evento 15");
        e.setDescrizione("Descrizione 15");
        e.setCucina("Italiana");
        e.setPranzo_cena(Event.CENA);
        e.setIndirizzo("Via 15");
        c.set(2018,1,1,19,0);
        e.setData(c);
        e.addFoto(R.drawable.logo);
        e.setMaxBookings(4);
        listaEventi.add(e);

        //Generazione ID sequenziali
        int i = 0;
        for(Event ev:listaEventi){
            ev.setId(i++);
        }
    }

}
