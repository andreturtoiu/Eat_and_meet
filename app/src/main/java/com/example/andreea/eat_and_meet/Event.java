package com.example.andreea.eat_and_meet;

import android.media.Image;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Michele on 03/02/2018.
 */

public class Event implements Serializable {
    private int id;
    private String titolo;
    private String descrizione;
    private ArrayList<String> foto = new ArrayList<String>();
    private ArrayList<Integer> partecipanti = new ArrayList<Integer>();

    public Event(){
        this.setTitolo("");
        this.setDescrizione("");
    }

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
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
    public void addPartecipante(int user){
        partecipanti.add(user);
    }
    public ArrayList<Integer> getPartecipanti(){
        return this.partecipanti;
    }
}

