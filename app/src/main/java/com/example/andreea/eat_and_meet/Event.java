package com.example.andreea.eat_and_meet;

import android.location.Address;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Michele on 03/02/2018.
 */

public class Event implements Serializable {
    private int id;
    private int user;
    private String titolo;
    private String descrizione;
    private String cucina;
    private Calendar data;
    private String indirizzo;
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

    public Calendar getData() { return this.data; }
    public void setData(Calendar data) { this.data = data; }

    public String getIndirizzo() { return this.indirizzo; }
    public void setIndirizzo(String indirizzo) { this.indirizzo = indirizzo; }
}

