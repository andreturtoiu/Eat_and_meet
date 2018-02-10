package com.example.andreea.eat_and_meet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by Michele on 10/02/2018.
 */

public class ShowBookedUsersList extends AppCompatActivity {
    @SuppressLint("WrongViewCast")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_users_list);

        Intent intent = getIntent();
        ArrayList<Integer> partecipanti = (ArrayList<Integer>) intent.getSerializableExtra("BOOKED_USERS");
        LinearLayout ll = (LinearLayout) findViewById(R.id.booked_user_container);


        for(int u : partecipanti){
            //Genero Layout Evento
            // Creare vista e generare utenti
            // Sostituire con classe utente
            LinearLayout llu = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.template_booked_user, null);;
            //Aggiungo Evento
            ll.addView(llu);
        }

    }
    //Linkare a Profilo Utente
    //Necessario XML per visualizzare profilo utente
    /*
    class HandleEvent implements View.OnClickListener{
        Event e;
        @Override
        public void onClick(View v){
            Intent showEvent = new Intent(ShowBookedUsersList.this,ShowEvent.class);
            showEvent.putExtra("EVENT_EXTRA",e);
            startActivity(showEvent);
        }
        public HandleEvent(Event e){
            this.e = e;
        }
    }*/
}

