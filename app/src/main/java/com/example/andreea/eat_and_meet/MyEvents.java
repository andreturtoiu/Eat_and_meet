package com.example.andreea.eat_and_meet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by Andreea on 02/02/2018.
 */

public class MyEvents extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myBookings = LayoutInflater.from(getActivity()).inflate(R.layout.activity_event_list, null);
        String logged_user = PersonFactory.getInstance().getLoggedUser();
        ArrayList<Event> eventi = EventFactory.getInstance().getEventsByUser(logged_user); //INSERIRE VARIABILE GLOBALE UTENTE
        LinearLayout ll = myBookings.findViewById(R.id.event_container);
        for(Event e : eventi){
            //Genero Layout Evento
            LinearLayout lle = EventFactory.getInstance().newEventView(e,new HandleEvent(e),getActivity());
            //Aggiungo Evento
            ll.addView(lle);
        }

        return myBookings;

    }

    class HandleEvent implements View.OnClickListener{
        Event e;
        @Override
        public void onClick(View v){
            Intent showEvent = new Intent(getActivity(),ShowEvent.class);
            showEvent.putExtra("EVENT_EXTRA",e);
            startActivity(showEvent);
        }
        public HandleEvent(Event e){
            this.e = e;
        }
    }

}

