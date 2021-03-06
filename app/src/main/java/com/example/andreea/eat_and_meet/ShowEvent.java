package com.example.andreea.eat_and_meet;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Michele on 05/02/2018.
 */

public class ShowEvent extends AppCompatActivity{

    private Event evento;
    String logged_user;
    Intent intentDelete;
    Intent intentEdit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event);
        this.intentDelete = new Intent(this,HomePage.class);
        this.intentEdit = new Intent(this,EditEvent.class);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Serializable obj = intent.getSerializableExtra("EVENT_EXTRA");
        if (!(obj instanceof Event)) return;

        evento = (Event) obj;

        boolean confirmRequest = intent.getBooleanExtra("CONFIRM_REQUEST",false);
        boolean confirmUnsubscribe = intent.getBooleanExtra("CONFIRM_UNSUBSCRIBE",false);
        if(confirmRequest) dialogConfirm("Inviata richiesta d'iscrizione all'evento "+evento.getTitolo());
        if(confirmUnsubscribe) dialogConfirm("Hai annullato la tua iscrizione all'evento "+evento.getTitolo());

        final String user = evento.getUser();
        Person person = PersonFactory.getInstance().getUserByEmail(user);
        TextView userName = (TextView)findViewById(R.id.userOrg);
        ImageView userPic = (ImageView)(findViewById(R.id.userPhoto));
        userName.setText(person.getName() + " " + person.getSurname());
        userPic.setImageResource(person.getFoto());
        TextView titolo = (TextView) findViewById(R.id.TitoloId);
        titolo.setText(evento.getTitolo());
        TextView info =  (TextView) findViewById(R.id.InfoId);
        info.setText(evento.getDescrizione());
        TextView cucina = (TextView) findViewById(R.id.CucinaId);
        cucina.setText(evento.getCucina());
        TextView address = (TextView) findViewById(R.id.AddressId);
        address.setText(evento.getIndirizzo());
        TextView partecipanti = (TextView) findViewById(R.id.IscrittiId);
        partecipanti.setText(evento.getPartecipanti().size()+" su "+evento.getMaxBookings());
        TextView data = (TextView) findViewById(R.id.DataId);
        data.setText(evento.getData());


        ///
        ImageView map = (ImageView) findViewById(R.id.map);

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowEvent.this,MapsActivity.class);
                ArrayList<Double> pos = evento.getLocation();
                if(pos != null) {
                    intent.putExtra("POSITION_EXTRA", pos);
                    startActivity(intent);
                }else {

                }
            }
        });

        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user.equals(PersonFactory.getInstance().getLoggedUser())){

                }else {
                    Intent intent = new Intent(ShowEvent.this, UserProfile.class);
                    intent.putExtra("EMAIL_EXTRA", user);
                    startActivity(intent);
                }
            }
        });

        userPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user.equals(PersonFactory.getInstance().getLoggedUser())){

                }else {
                    Intent intent = new Intent(ShowEvent.this, UserProfile.class);
                    intent.putExtra("EMAIL_EXTRA", user);
                    startActivity(intent);
                }

            }
        });

        //
        ArrayList<Integer> fotoList = evento.getFotoList();
        List<BitmapDataObject> fotoUriList = evento.getFotoUriList();
        LinearLayout ss = (LinearLayout) findViewById(R.id.SlideshowId);
        int dim = (findViewById(R.id.scroll_slideshow)).getLayoutParams().height;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(dim,LinearLayout.LayoutParams.MATCH_PARENT);

        for(Integer i:fotoList){ //i corrisponde a R.drawable.immagine
            ImageView foto = new ImageView(this);
            foto.setImageResource(i);
            //Imposto dimensione
            foto.setLayoutParams(lp);
            foto.setScaleType(ImageView.ScaleType.FIT_XY);
            ss.addView(foto);
        }

        for(BitmapDataObject bm:fotoUriList){
            ImageView foto = new ImageView(this);
            //Uri path = Uri.parse(uri);
            //foto.setImageURI(path);
            foto.setImageBitmap(bm.getBitmap());


            //Imposto dimensione
            foto.setLayoutParams(lp);
            foto.setScaleType(ImageView.ScaleType.FIT_XY);
            ss.addView(foto);
        }

        String source = (String) intent.getSerializableExtra("SOURCE");
        Button btn = (Button) new Button(this);
        logged_user = PersonFactory.getInstance().getLoggedUser();
        if(evento.getUser().equals(logged_user)) { // Sono il proprietario
            btn.setText("Mostra Partecipanti");
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ShowEvent.this,ShowBookedUsersList.class);
                    intent.putExtra("EVENT_EXTRA",evento);
                    startActivity(intent);
                }
            });
        }
        else {
            if (evento.isBooked(logged_user)){                //Sono iscritto. Voglio annullare
                btn.setText("Annulla Iscrizione");
                btn.setOnClickListener(new UnSubscribeListener());
            }
            else if (evento.hasRequest(logged_user)){
                btn.setText("Annulla richiesta iscrizione");
                btn.setOnClickListener(new UnSubscribeListener());
            }
            else{
                btn.setText("Iscriviti"); //Non sono iscritto. Voglio iscrivermi
                btn.setOnClickListener(new SubscribeListener());
            }

        }
        LinearLayout ll = (LinearLayout) findViewById(R.id.showEventBody);
        ll.addView(btn);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        String logged_user = PersonFactory.getInstance().getLoggedUser();
        if(evento.getUser().equals(logged_user))
            getMenuInflater().inflate(R.menu.menu_show_events, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.alterEvent:
                // User chose the "Settings" item, show the app settings UI...
                //intentEdit.putExtra("EVENT",evento);
                intentEdit.putExtra("EVENT_EXTRA",evento);
                startActivity(intentEdit);
                return true;

            case R.id.deleteEvent:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setMessage("Sei sicuro di voler eliminare?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Sì",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                EventFactory.getInstance().deleteEventById(evento.getId());
                                startActivity(intentDelete);

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

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
    class UnSubscribeListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(ShowEvent.this);
            builder1.setMessage("Sei sicuro?");
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "Sì",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(ShowEvent.this,ShowEvent.class);
                            EventFactory.getInstance().unSubscribeFromEvent(evento.getId(),logged_user); //GLOBALE
                            intent.putExtra("EVENT_EXTRA",EventFactory.getInstance().getEventById(evento.getId()));
                            intent.putExtra("CONFIRM_UNSUBSCRIBE",true);
                            finish();
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
    }

    class SubscribeListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            final Event e = EventFactory.getInstance().isDateReserved(evento,logged_user);
            if(e == null) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ShowEvent.this);
                builder1.setMessage("Posti disponibili: " + (evento.getMaxBookings() - evento.getPartecipanti().size()) + "\nSei sicuro?");
                builder1.setCancelable(false);
                builder1.setPositiveButton(
                        "Sì",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(ShowEvent.this, ShowEvent.class);
                                EventFactory.getInstance().RequestSubscribeToEvent(evento.getId(), logged_user); //GLOBALE
                                intent.putExtra("EVENT_EXTRA",EventFactory.getInstance().getEventById(evento.getId()));
                                intent.putExtra("CONFIRM_REQUEST",true);
                                finish();
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
            else{
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ShowEvent.this);
                builder1.setMessage("Hai già un impegno per l'evento: "+e.getTitolo());
                builder1.setCancelable(false);
                builder1.setPositiveButton(
                        "Mostra Evento",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(ShowEvent.this,ShowEvent.class);
                                intent.putExtra("EVENT_EXTRA",e);
                                finish();
                                startActivity(intent);

                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        }
    }

    private void dialogConfirm(String message){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(ShowEvent.this);
        builder1.setMessage(message);
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
    }


}
