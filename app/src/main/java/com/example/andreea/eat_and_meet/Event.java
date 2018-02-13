package com.example.andreea.eat_and_meet;

import android.location.Address;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Michele on 03/02/2018.
 */

public class Event implements Serializable {

    public static final int PRANZO = 0;
    public static final int CENA = 1;

    private int id;
    private int user;
    private int maxBookings;
    private int pranzo_cena; //0 = pranzo, 1 = cena
    private String titolo;
    private String descrizione;
    private String cucina;
    private Calendar data;
    private String via;
    private String city;
    private ArrayList<Integer> foto = new ArrayList<>();
    private ArrayList<Integer> partecipanti = new ArrayList<>();

    public Event(){
        this.setTitolo("");
        this.setDescrizione("");
    }

    public void setId(int id){
        this.id = id;
    }
    public int getId(){ return this.id; }

    public void setUser(int user) { this.user = user; }
    public int getUser() { return this.user; }

    public void setTitolo(String titolo){
        this.titolo = titolo;
    }
    public String getTitolo(){
        return this.titolo;
    }

    public void setDescrizione(String descrizione){
        this.descrizione = descrizione;
    }
    public String getDescrizione(){
        return this.descrizione;
    }

    public void addFoto(int id){ this.foto.add(id); }
    public ArrayList<Integer> getFotoList(){ return this.foto; }

    public void addPartecipante(int user){
        partecipanti.add(user);
    }
    public ArrayList<Integer> getPartecipanti(){
        return this.partecipanti;
    }

    public String getCucina() { return this.cucina; }
    public void setCucina(String cucina) { this.cucina = cucina; }

    public String getData() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - hh:mm");
        return sdf.format(this.data.getTime());
    }
    public void setData(Calendar data) { this.data = data; }

    public int getPranzo_cena() {return this.pranzo_cena;}
    public void setPranzo_cena(int pranzo_cena){this.pranzo_cena = pranzo_cena;}

    public String getVia() { return this.via; }
    public void setVia(String via) { this.via = via; }

    public String getCity(){ return this.city;}
    public void setCity(String city){ this.city = city; }

    public String getIndirizzo(){ return (this.via+", "+this.city);}

    public boolean isBooked(int userID){
        for(Integer i:partecipanti)
            if (i == userID) return true;
        return false;
    }

    public void unSubscribe(int idUser) {
        this.partecipanti.remove((Integer) idUser);
    }

    public void setMaxBookings(int max){
        this.maxBookings = max;
    }
    public int getMaxBookings(){
        return this.maxBookings;
    }

    public void Subscribe(int idUser) {
        this.partecipanti.add(idUser);
    }

    public boolean isFull(){
        return !(this.partecipanti.size() < maxBookings);
    }
}

