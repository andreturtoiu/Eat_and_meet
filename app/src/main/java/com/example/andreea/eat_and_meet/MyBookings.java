package com.example.andreea.eat_and_meet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Andreea on 02/02/2018.
 */

public class MyBookings extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ScrollView sv = new ScrollView(getActivity());
        ArrayList<Event> eventi = EventFactory.getInstance().getBookingsByUser(0); //INSERIRE VARIABILE GLOBALE UTENTE
        LinearLayout ll = new LinearLayout(getActivity());
        ll.setOrientation(LinearLayout.VERTICAL);
        for(Event e : eventi){
            //Genero Layout Evento
            LinearLayout lle = newEventView(e);
            lle.setId(View.generateViewId());
            //Aggiungo Evento
            ll.addView(lle);
        }

        LinearLayout empty = new LinearLayout(getActivity());
        empty.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200));
        ll.addView(empty);

        sv.addView(ll);
        return sv;
        //return rootView;
    }

    private LinearLayout newEventView (Event e){
        //Contenitore evento
        LinearLayout eventView = new LinearLayout(getActivity());
        eventView.setOrientation(LinearLayout.VERTICAL);
        //Titolo
        TextView title = new TextView(getActivity());
        title.setText(e.getTitolo());
        title.setId(View.generateViewId());
        //Descrizione
        TextView info = new TextView(getActivity());
        info.setText(e.getDescrizione());
        info.setId(View.generateViewId());
        info.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT));
        //Icona
        ImageView image = new ImageView(getActivity());
        image.setImageResource(R.drawable.logo);
        image.setLayoutParams(new LinearLayout.LayoutParams(200,LinearLayout.LayoutParams.MATCH_PARENT));
        LinearLayout box = new LinearLayout(getActivity());
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
            Intent showEvent = new Intent(getActivity(),ShowEvent.class); //TEMP: SOSTITUIRE CON DESCRIZIONE EVENTO
            showEvent.putExtra("EVENT_EXTRA",e);
            showEvent.putExtra("SOURCE","MY_BOOKINGS");
            startActivity(showEvent);
        }
        public HandleEvent(Event e){
            this.e = e;
        }
    }

}

