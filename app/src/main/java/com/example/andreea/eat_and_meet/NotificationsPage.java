package com.example.andreea.eat_and_meet;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
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
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        String logged_user=PersonFactory.getInstance().getLoggedUser();
        ArrayList<Notifications> notifications = PersonFactory.getInstance().getUserByEmail(logged_user).getMyNotifications();
        setSupportActionBar(toolbar);
        Intent intent = getIntent();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_notif);

        View headerview = navigationView.getHeaderView(0);

        emailUser = PersonFactory.getInstance().getLoggedUser();
        loggedUser = PersonFactory.getInstance().getUserByEmail(emailUser);


        TextView profilename = (TextView) headerview.findViewById(R.id.name);
        profilename.setText(loggedUser.getName()+" "+ loggedUser.getSurname());
        ImageView img = (ImageView) headerview.findViewById(R.id.imageView);
        img.setImageResource(loggedUser.getFoto());


        headerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = PersonFactory.getInstance().getLoggedUser();
                Intent t1 = new Intent(NotificationsPage.this, LoggedProfile.class);
                t1.putExtra("EMAIL_EXTRA",email);
                startActivity(t1);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();

                if (item.getItemId()== R.id.events) {
                    Intent t = new Intent(NotificationsPage.this, HomePage.class);
                    startActivity(t);
                }else if (item.getItemId() == R.id.create_event) {
                    Intent t = new Intent(NotificationsPage.this, CreateEvent.class);
                    startActivity(t);

                } else if (item.getItemId() == R.id.search_events) {
                    Intent t = new Intent(NotificationsPage.this,SearchEvents.class);
                    startActivity(t);

                } else if (item.getItemId() == R.id.notifies) {

                } else if (item.getItemId() == R.id.logout) {
                    Intent t = new Intent(NotificationsPage.this, Login_activity.class);
                    startActivity(t);
                }

                return false;
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Notifiche");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

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
                info.setText(PersonFactory.getInstance().getUserByEmail(n.getMandante()).getName()+" "+PersonFactory.getInstance().getUserByEmail(n.getMandante()).getSurname()+" vuole partecipare all'evento: "+EventFactory.getInstance().getEventById(n.getEvento()).getTitolo());
                info.setId(View.generateViewId());
                userpic.setImageResource(PersonFactory.getInstance().getUserByEmail(n.getMandante()).getFoto());
                userpic.setId(View.generateViewId());

                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO implementa logica
                        loggedUser.getMyNotifications().remove(clone);
                        notificationView.setVisibility(View.GONE);
                    }
                });
                confirm.setId(View.generateViewId());

                deny.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO implementa logica
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
                info.setText("L'utente " + PersonFactory.getInstance().getUserByEmail(n.getMandante()).getName()+" "+PersonFactory.getInstance().getUserByEmail(n.getMandante()).getSurname()+" ha rifiutato la tua richiesta di partecipare all evento: "+EventFactory.getInstance().getEventById(n.getEvento()).getTitolo());
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
                info.setText("L'utente " + PersonFactory.getInstance().getUserByEmail(n.getMandante()).getName()+" "+PersonFactory.getInstance().getUserByEmail(n.getMandante()).getSurname()+" ha accettato la tua richiesta di partecipare all evento: "+EventFactory.getInstance().getEventById(n.getEvento()).getTitolo());
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
                info.setText("L'utente " + PersonFactory.getInstance().getUserByEmail(n.getMandante()).getName()+" "+PersonFactory.getInstance().getUserByEmail(n.getMandante()).getSurname()+" ha apportato modifiche all'evento: "+EventFactory.getInstance().getEventById(n.getEvento()).getTitolo());
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
                info.setText("L'utente " + PersonFactory.getInstance().getUserByEmail(n.getMandante()).getName()+" "+PersonFactory.getInstance().getUserByEmail(n.getMandante()).getSurname()+" ha annullato l'evento: "+EventFactory.getInstance().getEventById(n.getEvento()).getTitolo());
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
        }

        return notificationView;

    }



}
