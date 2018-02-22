package com.example.andreea.eat_and_meet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Andreea on 21/02/2018.
 */

public class ShowSearchedEvent extends AppCompatActivity {

    @SuppressLint("WrongViewCast")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_events);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        ArrayList<Event> eventi = (ArrayList<Event>) intent.getSerializableExtra("EVENT_SEARCHED");
        LinearLayout ll = (LinearLayout) findViewById(R.id.event_container);
        TextView t = (TextView) findViewById(R.id.result);
        if(eventi.isEmpty()){
            t.setText("Nessun evento trovato, prova a cambiare le tue preferenze :)");
        }else{
            t.setText("Elenco degli eventi trovati");
        }

        for(Event e : eventi){
            //Genero Layout Evento
            LinearLayout lle = EventFactory.getInstance().newEventView(e,new HandleEvent(e),this);
            //Aggiungo Evento
            ll.addView(lle);
        }
    }

    class HandleEvent implements View.OnClickListener{
        Event e;
        @Override
        public void onClick(View v){
            Intent showEvent = new Intent(ShowSearchedEvent.this,SearchedEventDetails.class);
            showEvent.putExtra("EVENT_EXTRA",e);
            startActivity(showEvent);
        }
        public HandleEvent(Event e){
            this.e = e;
        }
    }


}