package com.example.andreea.eat_and_meet;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

/**
 * Created by Quontini on 20/02/2018.
 */

public class NotificationsPage extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Person loggedUser;
    String emailUser;
    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_page);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        String logged_user=PersonFactory.getInstance().getLoggedUser();
        ArrayList<Notifications> notifications = PersonFactory.getInstance().getUserByEmail(logged_user).getMyNotifications();

        Intent intent = getIntent();


        emailUser = PersonFactory.getInstance().getLoggedUser();
        loggedUser = PersonFactory.getInstance().getUserByEmail(emailUser);



        LinearLayout ll= (LinearLayout) findViewById(R.id.container_notif);

        for(Notifications n : notifications){

            LinearLayout lle= newNotificationView(n, NotificationsPage.this);
            ll.addView(lle);
        }




    }

    private LinearLayout newNotificationView(Notifications n, Context c){
        final LinearLayout notificationView;
        TextView info;
        Button delete;
        final Notifications clone = n;

        switch (n.getContensto()){
            case Notifications.RICHIESTA:
                notificationView = (LinearLayout) LayoutInflater.from(c).inflate(R.layout.template_notication, null);
                info = (TextView) notificationView.findViewById(R.id.infoviewNotification);
                ImageView userpic = (ImageView) notificationView.findViewById(R.id.userpicNotification);
                Button confirm = (Button) notificationView.findViewById(R.id.confirmNotification);
                Button deny = (Button) notificationView.findViewById(R.id.denyNotification);
                info.setText(PersonFactory.getInstance().getUserByEmail(n.getMandante()).getName()+" "+PersonFactory.getInstance().getUserByEmail(n.getMandante()).getSurname()+" vuole partecipare all'evento: "+n.getTitoloEvento());
                info.setId(View.generateViewId());
                userpic.setImageResource(PersonFactory.getInstance().getUserByEmail(n.getMandante()).getFoto());
                userpic.setId(View.generateViewId());

                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(EventFactory.getInstance().getEventById(clone.getEvento()).getPartecipanti().size()<EventFactory.getInstance().getEventById(clone.getEvento()).getMaxBookings()) {
                            EventFactory.getInstance().getEventById(clone.getEvento()).addPartecipante(clone.getMandante());
                            EventFactory.getInstance().getEventById(clone.getEvento()).getRichieste().remove(clone.getMandante());
                            if(EventFactory.getInstance().getEventById(clone.getEvento()).getPartecipanti().size()==EventFactory.getInstance().getEventById(clone.getEvento()).getMaxBookings()){
                                emptyBookings(EventFactory.getInstance().getEventById(clone.getEvento()));
                            }

                            AlertDialog.Builder builder = new AlertDialog.Builder(NotificationsPage.this);
                            final View popview = getLayoutInflater().inflate(R.layout.signup_dialog, null);
                            TextView text = (TextView) popview.findViewById(R.id.popuptext);
                            text.setText("Richiesta confermata");
                            Button confirm = (Button) popview.findViewById(R.id.ConfirmButton);
                            confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent login = new Intent(NotificationsPage.this, NotificationsPage.class);
                                    startActivity(login);
                                }
                            });
                            builder.setView(popview);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                        else {
                            EventFactory.getInstance().getEventById(clone.getEvento()).getRichieste().remove(clone.getMandante());
                            AlertDialog.Builder builder = new AlertDialog.Builder(NotificationsPage.this);
                            final View popview = getLayoutInflater().inflate(R.layout.signup_dialog, null);
                            TextView text = (TextView) popview.findViewById(R.id.popuptext);
                            text.setText("Numero massimo di partecipanti raggiunto");
                            Button confirm = (Button) popview.findViewById(R.id.ConfirmButton);
                            confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                   Intent login = new Intent(NotificationsPage.this, NotificationsPage.class);
                                   startActivity(login);
                                }
                            });
                            builder.setView(popview);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                        Notifications nn= new Notifications(loggedUser.getEmail(), clone.getEvento(), Notifications.R_APPROVATA, clone.getTitoloEvento());
                        PersonFactory.getInstance().getUserByEmail(clone.getMandante()).getMyNotifications().add(nn);
                        loggedUser.getMyNotifications().remove(clone);
                        notificationView.setVisibility(View.GONE);
                    }
                });
                confirm.setId(View.generateViewId());

                deny.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        EventFactory.getInstance().getEventById(clone.getEvento()).getRichieste().remove(clone.getMandante());
                        Notifications nn= new Notifications(loggedUser.getEmail(), clone.getEvento(), Notifications.R_RIFIUTATA, clone.getTitoloEvento());
                        PersonFactory.getInstance().getUserByEmail(clone.getMandante()).getMyNotifications().add(nn);
                        loggedUser.getMyNotifications().remove(clone);
                        notificationView.setVisibility(View.GONE);
                    }
                });
                deny.setId(View.generateViewId());
                break;

            case Notifications.R_RIFIUTATA:
                notificationView = (LinearLayout) LayoutInflater.from(c).inflate(R.layout.template_notification_notice, null);
                info = (TextView) notificationView.findViewById(R.id.notifiNoticeInfo);
                delete = (Button) notificationView.findViewById(R.id.notifNoticeOk);
                info.setText("L'utente " + PersonFactory.getInstance().getUserByEmail(n.getMandante()).getName()+" "+PersonFactory.getInstance().getUserByEmail(n.getMandante()).getSurname()+" ha rifiutato la tua richiesta di partecipare all evento: "+n.getTitoloEvento());
                info.setId(View.generateViewId());

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loggedUser.getMyNotifications().remove(clone);
                        notificationView.setVisibility(View.GONE);
                    }
                });
                delete.setId(View.generateViewId());
                break;

            case Notifications.R_APPROVATA:
                notificationView = (LinearLayout) LayoutInflater.from(c).inflate(R.layout.template_notification_notice, null);
                info = (TextView) notificationView.findViewById(R.id.notifiNoticeInfo);
                delete = (Button) notificationView.findViewById(R.id.notifNoticeOk);
                info.setText("L'utente " + PersonFactory.getInstance().getUserByEmail(n.getMandante()).getName()+" "+PersonFactory.getInstance().getUserByEmail(n.getMandante()).getSurname()+" ha accettato la tua richiesta di partecipare all evento: "+n.getTitoloEvento());
                info.setId(View.generateViewId());

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loggedUser.getMyNotifications().remove(clone);
                        notificationView.setVisibility(View.GONE);
                    }
                });
                delete.setId(View.generateViewId());
                break;

            case Notifications.MODIFICA:
                notificationView = (LinearLayout) LayoutInflater.from(c).inflate(R.layout.template_notification_notice, null);
                info = (TextView) notificationView.findViewById(R.id.notifiNoticeInfo);
                delete = (Button) notificationView.findViewById(R.id.notifNoticeOk);
                info.setText("L'utente " + PersonFactory.getInstance().getUserByEmail(n.getMandante()).getName()+" "+PersonFactory.getInstance().getUserByEmail(n.getMandante()).getSurname()+" ha apportato modifiche all'evento: "+n.getTitoloEvento());
                info.setId(View.generateViewId());

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        loggedUser.getMyNotifications().remove(clone);
                        notificationView.setVisibility(View.GONE);
                    }
                });
                delete.setId(View.generateViewId());
                break;

            case Notifications.RINUNCIA:
                notificationView = (LinearLayout) LayoutInflater.from(c).inflate(R.layout.template_notification_notice, null);
                info = (TextView) notificationView.findViewById(R.id.notifiNoticeInfo);
                delete = (Button) notificationView.findViewById(R.id.notifNoticeOk);
                info.setText("L'utente " + PersonFactory.getInstance().getUserByEmail(n.getMandante()).getName()+" "+PersonFactory.getInstance().getUserByEmail(n.getMandante()).getSurname()+" si è cancellato dall'evento: "+n.getTitoloEvento());
                info.setId(View.generateViewId());

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        loggedUser.getMyNotifications().remove(clone);
                        notificationView.setVisibility(View.GONE);
                    }
                });
                delete.setId(View.generateViewId());
                break;

            case Notifications.FULL:
                notificationView = (LinearLayout) LayoutInflater.from(c).inflate(R.layout.template_notification_notice, null);
                info = (TextView) notificationView.findViewById(R.id.notifiNoticeInfo);
                delete = (Button) notificationView.findViewById(R.id.notifNoticeOk);
                info.setText("L'evento "+n.getTitoloEvento() + " ha ragiunto capienza massima");
                info.setId(View.generateViewId());

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        loggedUser.getMyNotifications().remove(clone);
                        notificationView.setVisibility(View.GONE);
                    }
                });
                delete.setId(View.generateViewId());
                break;

            case Notifications.RIMOZIONE:
                notificationView = (LinearLayout) LayoutInflater.from(c).inflate(R.layout.template_notification_notice, null);
                info = (TextView) notificationView.findViewById(R.id.notifiNoticeInfo);
                delete = (Button) notificationView.findViewById(R.id.notifNoticeOk);
                info.setText("L'utente " + PersonFactory.getInstance().getUserByEmail(n.getMandante()).getName()+" "+PersonFactory.getInstance().getUserByEmail(n.getMandante()).getSurname()+" ti ha rimosso dall'evento: "+n.getTitoloEvento());
                info.setId(View.generateViewId());

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        loggedUser.getMyNotifications().remove(clone);
                        notificationView.setVisibility(View.GONE);
                    }
                });
                delete.setId(View.generateViewId());
                break;



            default:
                notificationView = (LinearLayout) LayoutInflater.from(c).inflate(R.layout.template_notification_notice, null);
                info = (TextView) notificationView.findViewById(R.id.notifiNoticeInfo);
                delete = (Button) notificationView.findViewById(R.id.notifNoticeOk);
                info.setText("L'utente " + PersonFactory.getInstance().getUserByEmail(n.getMandante()).getName()+" "+PersonFactory.getInstance().getUserByEmail(n.getMandante()).getSurname()+" ha annullato l'evento: "+n.getTitoloEvento());
                info.setId(View.generateViewId());

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loggedUser.getMyNotifications().remove(clone);
                        notificationView.setVisibility(View.GONE);
                    }
                });
                delete.setId(View.generateViewId());
        }

        return notificationView;

    }

    private void emptyBookings(Event eventById) {

        ArrayList<String> richieste = eventById.getRichieste();
        for (String r:richieste){

            PersonFactory.getInstance().getUserByEmail(r).getMyNotifications().add(new Notifications(eventById.getUser(), eventById.getId(), Notifications.FULL, eventById.getTitolo()));
            PersonFactory.getInstance().removenotif(eventById.getId(), Notifications.RICHIESTA, eventById.getUser(), r);

        }

    }


}
