package com.example.andreea.eat_and_meet;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by Michele on 08/02/2018.
 */

public class SearchEvents extends AppCompatActivity {

    Spinner spinner;
    CheckBox pranzo;
    CheckBox cena;
    EditText text_citta;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_events);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        spinner = (Spinner) findViewById(R.id.spinner_cucina);
        pranzo = (CheckBox) findViewById(R.id.check_pranzo);
        cena = (CheckBox) findViewById(R.id.check_cena);
        text_citta = (EditText) findViewById(R.id.citta);

        findViewById(R.id.search_button).setOnClickListener(new sendInput());
    }

    class sendInput implements View.OnClickListener {
        public void onClick(View v) {
            Intent intent = new Intent(SearchEvents.this, ShowEventList.class);

            String cucina = spinner.getSelectedItem().toString();
            int pranzo_cena;
            if (pranzo.isChecked() && cena.isChecked())pranzo_cena = -1;
            else if (pranzo.isChecked()) pranzo_cena = Event.PRANZO;
            else if (cena.isChecked()) pranzo_cena = Event.CENA;
            else{
                //Nessuna selezione
                AlertDialog.Builder builder1 = new AlertDialog.Builder(SearchEvents.this);
                builder1.setMessage("Seleziona una preferenza Pranzo/Cena");
                builder1.setCancelable(true);
                builder1.setNegativeButton(
                        "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
                return;
            }



            String citta = text_citta.getText().toString();
            ArrayList<Event> list = EventFactory.getInstance().searchEventsByFilter(cucina,citta,pranzo_cena);

            intent.putExtra("EVENT_LIST", list);

            startActivity(intent);
        }
    }
}
