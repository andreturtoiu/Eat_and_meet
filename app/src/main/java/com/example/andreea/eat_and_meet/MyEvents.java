package com.example.andreea.eat_and_meet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Andreea on 02/02/2018.
 */

public class MyEvents extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View rootView = inflater.inflate(R.layout.activity_events, container, false);
        ScrollView sv = new ScrollView(getActivity());
        ArrayList<Event> eventi = EventFactory.getInstance().getEventList();
        LinearLayout ll = new LinearLayout(getActivity());
        ll.setOrientation(LinearLayout.VERTICAL);
        for(Event e : eventi){
            //Genero Layout Evento
            LinearLayout lle = newEventView(e);
            lle.setId(View.generateViewId());
            //Aggiungo Evento
            ll.addView(lle);
        }
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
        //Icona
        ImageView image = new ImageView(getActivity());
        image.setImageResource(R.drawable.ic_menu_camera);
        LinearLayout box = new LinearLayout(getActivity());
        box.addView(image);
        box.addView(info);
        //Aggiungo al contenitore
        eventView.addView(title);
        eventView.addView(box);
        eventView.setOnClickListener(new HandleEvent(e));
        //Aggiungo listener

        return eventView;
    }

    public class HandleEvent implements View.OnClickListener{
        Event e;
        @Override
        public void onClick(View v){
            Intent showEvent = new Intent(getActivity(),Login_activity.class); //TEMP: SOSTITUIRE CON DESCRIZIONE EVENTO
            showEvent.putExtra("EVENT_EXTRA",e);
            startActivity(showEvent);
        }
        public HandleEvent(Event e){
            this.e = e;
        }
    }

}


