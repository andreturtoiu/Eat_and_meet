package com.example.andreea.eat_and_meet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Michele on 05/02/2018.
 */

public class ShowEvent extends AppCompatActivity{

    private Event evento;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event);

        Intent intent = getIntent();
        Serializable obj = intent.getSerializableExtra("EVENT_EXTRA");
        if (obj instanceof Event)
            evento = (Event) obj;
        else
            evento = new Event();

        TextView titolo = (TextView) findViewById(R.id.TitoloId);
        titolo.setText(evento.getTitolo());
        TextView info =  (TextView) findViewById(R.id.InfoId);
        info.setText(evento.getDescrizione());
        TextView cucina = (TextView) findViewById(R.id.CucinaId);
        cucina.setText(evento.getCucina());
        TextView address = (TextView) findViewById(R.id.AddressId);
        address.setText(evento.getIndirizzo());
        TextView partecipanti = (TextView) findViewById(R.id.IscrittiId);
        partecipanti.setText(evento.getPartecipanti().size()+"");
        TextView data = (TextView) findViewById(R.id.DataId);
        data.setText(evento.getData()+" - "+evento.getTime());
        partecipanti.setEnabled(false);
        titolo.setEnabled(false);
        info.setEnabled(false);
        cucina.setEnabled(false);
        address.setEnabled(false);
        data.setEnabled(false);
        ArrayList<Integer> fotoList = evento.getFotoList();
        LinearLayout ss = (LinearLayout) findViewById(R.id.SlideshowId);
        for(Integer i:fotoList){ //i corrisponde a R.drawable.immagine
            ImageView foto = new ImageView(this);
            foto.setImageResource(i);
            //Imposto dimensione
            foto.setLayoutParams(new LinearLayout.LayoutParams(200,200));
            ss.addView(foto);
        }
        String source = (String) intent.getSerializableExtra("SOURCE");
        Button btn = (Button) new Button(this);

        if(evento.getUser() == 0) // Sono il proprietario. Cambiare "0" con "logged_User"
            btn.setText("Mostra Partecipanti");
        else {
            if (evento.isBooked(0)) //Sono iscritto. Voglio annullare
                btn.setText("Annulla Iscrizione");
            else
                btn.setText("Iscriviti"); //Non sono iscritto. Voglio iscrivermi
        }
        LinearLayout ll = (LinearLayout) findViewById(R.id.showEventBody);
        ll.addView(btn);
    }
}
