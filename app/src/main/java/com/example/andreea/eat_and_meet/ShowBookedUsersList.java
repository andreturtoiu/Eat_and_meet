package com.example.andreea.eat_and_meet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Michele on 10/02/2018.
 */

public class ShowBookedUsersList extends AppCompatActivity {
    @SuppressLint("WrongViewCast")

    Event evento;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_users_list);

        Intent intent = getIntent();
        evento = (Event) intent.getSerializableExtra("EVENT_EXTRA");
        ArrayList<String> partecipanti = evento.getPartecipanti();
        LinearLayout ll = (LinearLayout) findViewById(R.id.booked_user_container);


        for(String u : partecipanti){
            //Genero Layout Evento
            // Creare vista e generare utenti
            // Sostituire con classe utente
            Person p = PersonFactory.getInstance().getUserByEmail(u);
            LinearLayout llu = newBookedUserView(p,this);
            //Aggiungo Evento
            ll.addView(llu);
        }

    }

    public LinearLayout newBookedUserView (Person p, Context c){
        LinearLayout eventView = (LinearLayout) LayoutInflater.from(c).inflate(R.layout.template_booked_user, null);
        TextView name = (TextView) eventView.findViewById(R.id.template_booked_user_name);
        TextView delete = (TextView) eventView.findViewById(R.id.template_delete_booked_user);
        ImageView photo = (ImageView) eventView.findViewById(R.id.template_booked_user_avatar);
        name.setText(p.getName()+" "+p.getSurname());
        name.setId(View.generateViewId());
        delete.setId(View.generateViewId());
        photo.setImageResource(R.drawable.ic_menu_camera);
        photo.setId(View.generateViewId());
        eventView.setId(View.generateViewId());
        //Aggiungo listener
        delete.setOnClickListener(new RemoveUser(p.getEmail()));
        return eventView;
    }
    //Linkare a Profilo Utente
    //Necessario XML per visualizzare profilo utente
    class ShowProfile implements View.OnClickListener{
        String u;
        @Override
        public void onClick(View v){
            /*Intent showEvent = new Intent(ShowBookedUsersList.this,ShowEvent.class);
            showEvent.putExtra("EVENT_EXTRA",u);
            startActivity(showEvent);*/
        }
        public ShowProfile(String u){
            this.u = u;
        }
    }
    class RemoveUser implements View.OnClickListener{
        String u;
        @Override
        public void onClick(View v){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(ShowBookedUsersList.this);
            builder1.setMessage("Sei sicuro?");
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "Sì",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(ShowBookedUsersList.this,HomePage.class);
                            EventFactory.getInstance().unSubscribeFromEvent(evento.getId(),u); //GLOBALE
                            startActivity(intent);

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
        }
        public RemoveUser(String u){
            this.u = u;
        }
    }
}

