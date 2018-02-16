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
    private String user;
    private int maxBookings;
    private int pranzo_cena; //0 = pranzo, 1 = cena
    private String titolo;
    private String descrizione;
    private String cucina;
    private Calendar data;
    private String via;
    private String city;
    private ArrayList<Integer> foto = new ArrayList<>();
    private ArrayList<String> partecipanti = new ArrayList<>();
    private ArrayList<String> richieste = new ArrayList<>();

    public Event(){
        this.setTitolo("");
        this.setDescrizione("");
    }

    public void setId(int id){ this.id = id; }
    public int getId(){ return this.id; }

    public void setUser(String user) { this.user = user; }
    public String getUser() { return this.user; }

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

    public void addPartecipante(String user){
        partecipanti.add(user);
    }
    public ArrayList<String> getPartecipanti(){
        return this.partecipanti;
    }

    public String getCucina() { return this.cucina; }
    public void setCucina(String cucina) { this.cucina = cucina; }

    public String getData() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        return sdf.format(this.data.getTime());
    }

    public String showDateOnly() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(this.data.getTime());
    }

    public String showTimeOnly(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(this.data.getTime());
    }

    public Calendar getDataCalendar(){ return this.data; }

    public void setData(Calendar data) { this.data = data; }
    public void setData(int year,int month, int day, int hour, int min){
        this.data = Calendar.getInstance();
        this.data.set(year,month,day,hour,min);
    }

    public int getPranzo_cena() {return this.pranzo_cena;}
    public void setPranzo_cena(int pranzo_cena){this.pranzo_cena = pranzo_cena;}

    public void addRequest(String email){
        this.richieste.add(email);
    }
    public ArrayList<String> getRichieste(){ return this.richieste; }

    public String getVia() { return this.via; }
    public void setVia(String via) { this.via = via; }

    public String getCity(){ return this.city;}
    public void setCity(String city){ this.city = city; }

    public String getIndirizzo(){ return (this.via+", "+this.city);}

    public boolean isBooked(String userMail){
        for(String i:partecipanti)
            if (i.equals(userMail)) return true;
        return false;
    }

    public boolean hasRequest(String userMail){
        for(String i:richieste)
            if (i.equals(userMail)) return true;
        return false;
    }

    public void unSubscribe(String idUser) {
        this.partecipanti.remove(idUser);
        this.richieste.remove(idUser);
    }

    public void setMaxBookings(int max){
        this.maxBookings = max;
    }
    public int getMaxBookings(){
        return this.maxBookings;
    }

    public void Subscribe(String userMail) {
        this.partecipanti.add(userMail);
    }

    public boolean isFull(){
        return !(this.partecipanti.size() < maxBookings);
    }

    public boolean isTimeValid(){
        int hour = this.data.get(Calendar.HOUR_OF_DAY);
        int minute = this.data.get(Calendar.MINUTE);
        int orario = (hour*100) + minute;
        int minPranzo = 1100; int maxPranzo = 1600;
        int minCena = 1700; int maxCena = 2200;

        switch(this.pranzo_cena){
            case PRANZO:
                return (minPranzo <= orario) && (orario <= maxPranzo);
            case CENA:
                return (minCena <= orario) && (orario <= maxCena);
        }
        return false;
    }


}

