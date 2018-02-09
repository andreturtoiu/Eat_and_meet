package com.example.andreea.eat_and_meet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Michele on 09/02/2018.
 */

public class ShowEventList extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_events);


        //prova
        ScrollView sv = new ScrollView(this);
        Intent intent = getIntent();
        ArrayList<Event> eventi = (ArrayList<Event>) intent.getSerializableExtra("EVENT_LIST");
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        for(Event e : eventi){
            //Genero Layout Evento
            LinearLayout lle = newEventView(e);
            lle.setId(View.generateViewId());
            //Aggiungo Evento
            ll.addView(lle);
        }

        LinearLayout empty = new LinearLayout(this);
        empty.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200));
        ll.addView(empty);

        sv.addView(ll);

        setContentView(sv);

    }

    private LinearLayout newEventView (Event e){
        //Contenitore evento
        LinearLayout eventView = new LinearLayout(this);
        eventView.setOrientation(LinearLayout.VERTICAL);
        //Titolo
        TextView title = new TextView(this);
        title.setText(e.getTitolo());
        title.setId(View.generateViewId());
        //Descrizione
        TextView info = new TextView(this);
        info.setText(e.getDescrizione());
        info.setId(View.generateViewId());
        info.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT));
        //Icona
        ImageView image = new ImageView(this);
        image.setImageResource(R.drawable.logo);
        image.setLayoutParams(new LinearLayout.LayoutParams(200,LinearLayout.LayoutParams.MATCH_PARENT));
        LinearLayout box = new LinearLayout(this);
        box.addView(image);
        box.addView(info);
        box.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,200));
        //Aggiungo al contenitore
        eventView.addView(title);
        eventView.addView(box);
        eventView.setOnClickListener(new HandleEvent(e));
        //Aggiungo listener

        return eventView;
    }

    class HandleEvent implements View.OnClickListener{
        Event e;
        @Override
        public void onClick(View v){
            Intent showEvent = new Intent(ShowEventList.this,ShowEvent.class);
            showEvent.putExtra("EVENT_EXTRA",e);
            startActivity(showEvent);
        }
        public HandleEvent(Event e){
            this.e = e;
        }
    }

}
