package com.example.andreea.eat_and_meet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
            if (pranzo.isChecked() && cena.isChecked()){
                pranzo_cena = -1;
            }
            else {
                if (pranzo.isChecked()) pranzo_cena = Event.PRANZO;
                else pranzo_cena = Event.CENA;
            }

            String citta = text_citta.getText().toString();
            ArrayList<Event> list = EventFactory.getInstance().searchEventsByFilter(cucina,citta,pranzo_cena);

            intent.putExtra("EVENT_LIST", list);

            startActivity(intent);
        }
    }
}
