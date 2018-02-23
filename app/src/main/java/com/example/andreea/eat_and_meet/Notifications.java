package com.example.andreea.eat_and_meet;

import java.io.PushbackInputStream;
import java.io.Serializable;

/**
 * Created by Quontini on 17/02/2018.
 */

public class Notifications implements Serializable {

    public static final int RICHIESTA = 0;
    public static final int R_RIFIUTATA = 1;
    public static final int R_APPROVATA = 2;
    public static final int MODIFICA = 3;
    public static final int CANCELLAZIONE = 4;
    public static final int RINUNCIA = 5;
    public static final int RIMOZIONE = 6;
    public static final int FULL = 7;


    private String mandante;
    private String titoloEvento;
    private int evento;
    private int contensto;

    public Notifications(String mandante, int evento, int contensto){

        this.mandante = mandante;
        this.evento = evento;
        this.contensto = contensto;
        this.titoloEvento = null;

    }
    public Notifications(String mandante, int evento, int contensto, String titoloEvento){

        this.mandante = mandante;
        this.evento = evento;
        this.contensto = contensto;
        this.titoloEvento = titoloEvento;

    }


    public String getMandante() {
        return mandante;
    }

    public void setMandante(String mandante) {
        this.mandante = mandante;
    }

    public int getEvento() {
        return evento;
    }

    public void setEvento(int evento) {
        this.evento = evento;
    }

    public int getContensto() {
        return contensto;
    }

    public void setContensto(int contensto) {
        this.contensto = contensto;
    }

    public String getTitoloEvento() {
        return titoloEvento;
    }

    public void setTitoloEvento(String titoloEvento) {
        this.titoloEvento = titoloEvento;
    }
}
