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
    private int pranzo_cena; //0 = pranzo, 1 = cena
    private String titolo;
    private String descrizione;
    private String cucina;
    private String data;
    private String time;
    private String indirizzo;
    private ArrayList<Integer> foto = new ArrayList<>();
    private ArrayList<Integer> partecipanti = new ArrayList<>();

    public Event(){
        this.setTitolo("");
        this.setDescrizione("");
    }

    public Event(int id,int user,String titolo,String descrizione,String cucina,String data,String time,int pranzo_cena,String indirizzo){
        this.id = id; this.user = user; this.titolo = titolo; this.descrizione = descrizione; this.cucina = cucina; this.data = data; this.time = time; this.indirizzo = indirizzo;
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

    public String getData() { return this.data; }
    public void setData(int year,int month, int date) { this.data = date+"/"+month+"/"+year; }

    public int getPranzo_cena() {return this.pranzo_cena;}
    public void setPranzo_cena(int pranzo_cena){this.pranzo_cena = pranzo_cena;}

    public String getTime() { return this.time; }
    public void setTime(int hour, int minute) { this.time = hour+":"+minute; }

    public String getIndirizzo() { return this.indirizzo; }
    public void setIndirizzo(String indirizzo) { this.indirizzo = indirizzo; }

    public boolean isBooked(int userID){
        for(Integer i:partecipanti)
            if (i == userID) return true;
        return false;
    }
}

