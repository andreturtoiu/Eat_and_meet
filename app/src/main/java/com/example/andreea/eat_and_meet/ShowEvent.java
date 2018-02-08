package com.example.andreea.eat_and_meet;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Michele on 05/02/2018.
 */

public class ShowEvent extends AppCompatActivity{

    private Event evento;

    Intent intentDelete;
    Intent intentEdit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event);
        this.intentDelete = new Intent(this,HomePage.class);
        this.intentEdit = new Intent(this,EditEvent.class);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        Serializable obj = intent.getSerializableExtra("EVENT_EXTRA");
        if (!(obj instanceof Event)) return;


        evento = (Event) obj;

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
        data.setText(evento.getData());

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(evento.getUser() == 0)
            getMenuInflater().inflate(R.menu.menu_show_events, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.alterEvent:
                // User chose the "Settings" item, show the app settings UI...
                //intentEdit.putExtra("EVENT",evento);
                intentEdit.putExtra("EVENT_EXTRA",evento);
                startActivity(intentEdit);
                return true;

            case R.id.deleteEvent:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setMessage("Sei sicuro di voler eliminare?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Sì",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                EventFactory.getInstance().deleteEventById(evento.getId());
                                startActivity(intentDelete);

                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}