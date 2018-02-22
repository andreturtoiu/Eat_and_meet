package com.example.andreea.eat_and_meet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Andreea on 02/02/2018.
 */

public class MyBookings extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myBookings = LayoutInflater.from(getActivity()).inflate(R.layout.activity_event_list, null);
        String logged_user = PersonFactory.getInstance().getLoggedUser();
        ArrayList<Event> eventi = EventFactory.getInstance().getBookingsByUser(logged_user); //INSERIRE VARIABILE GLOBALE UTENTE
        LinearLayout ll = myBookings.findViewById(R.id.event_container);
        //Verifico eventuali attese di conferma
        ArrayList<Event> richieste = EventFactory.getInstance().getPendingRequestsByUser(logged_user);
        if(richieste.size()>0){
            TextView requestWarning = new TextView(getActivity());
            String message = "Hai " + richieste.size() +" eventi in attesa di conferma";
            if(richieste.size() == 1) message = message.replace("eventi","evento");
            requestWarning.setText(message);
            requestWarning.setOnClickListener(new HandleRequest(richieste));
            requestWarning.setGravity(Gravity.CENTER_HORIZONTAL);
            ll.addView(requestWarning);
        }
        //
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

    class HandleRequest implements View.OnClickListener{
        ArrayList<Event> l;
        @Override
        public void onClick(View v){
            Intent showEventList = new Intent(getActivity(),RequestEvents.class);
            showEventList.putExtra("EVENT_REQUESTS",l);
            startActivity(showEventList);
        }
        public HandleRequest(ArrayList<Event> l){
            this.l = l;
        }
    }
}

