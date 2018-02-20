package com.example.andreea.eat_and_meet;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Quontini on 18/02/2018.
 */

public class MyNotifications extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        View myNotifications = LayoutInflater.from(getActivity()).inflate(R.layout.activity_notifications_list, null);
        String logged_user=PersonFactory.getInstance().getLoggedUser();
        ArrayList<Notifications> notifications = PersonFactory.getInstance().getUserByEmail(logged_user).getMyNotifications();
        LinearLayout ll= myNotifications.findViewById(R.id.notificationsContainer);
        for(Notifications n : notifications){

         LinearLayout lle= newNotificationView(n, getActivity());
         ll.addView(lle);
        }

        return myNotifications;

    }


    private LinearLayout newNotificationView(Notifications n, Context c){
        final LinearLayout notificationView;
        TextView info;
        Button delete;

        switch (n.getContensto()){
            case Notifications.RICHIESTA:
                notificationView = (LinearLayout) LayoutInflater.from(c).inflate(R.layout.template_notication, null);
                info = (TextView) notificationView.findViewById(R.id.infoviewNotification);
                ImageView userpic = (ImageView) notificationView.findViewById(R.id.userpicNotification);
                Button confirm = (Button) notificationView.findViewById(R.id.confirmNotification);
                Button deny = (Button) notificationView.findViewById(R.id.denyNotification);
                info.setText(PersonFactory.getInstance().getUserByEmail(n.getMandante()).getName()+PersonFactory.getInstance().getUserByEmail(n.getMandante()).getSurname()+" vuole partecipare all'eveto: "+EventFactory.getInstance().getEventById(n.getEvento()).getTitolo());
                info.setId(View.generateViewId());
                userpic.setImageResource(PersonFactory.getInstance().getUserByEmail(n.getMandante()).getFoto());
                userpic.setId(View.generateViewId());

                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO implementa logica
                    }
                });
                confirm.setId(View.generateViewId());

                deny.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO implementa logica
                    }
                });
                deny.setId(View.generateViewId());
                break;

            case Notifications.R_RIFIUTATA:
                notificationView = (LinearLayout) LayoutInflater.from(c).inflate(R.layout.template_notification_notice, null);
                info = (TextView) notificationView.findViewById(R.id.notifiNoticeInfo);
                delete = (Button) notificationView.findViewById(R.id.notifNoticeOk);
                info.setText("L'utente " + PersonFactory.getInstance().getUserByEmail(n.getMandante()).getName()+PersonFactory.getInstance().getUserByEmail(n.getMandante()).getSurname()+" ha rifiutato la tua richiesta di partecipare all evento: "+EventFactory.getInstance().getEventById(n.getEvento()).getTitolo());
                info.setId(View.generateViewId());

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO implementa logica
                    }
                });
                delete.setId(View.generateViewId());
                break;

            case Notifications.R_APPROVATA:
                notificationView = (LinearLayout) LayoutInflater.from(c).inflate(R.layout.template_notification_notice, null);
                info = (TextView) notificationView.findViewById(R.id.notifiNoticeInfo);
                delete = (Button) notificationView.findViewById(R.id.notifNoticeOk);
                info.setText("L'utente " + PersonFactory.getInstance().getUserByEmail(n.getMandante()).getName()+PersonFactory.getInstance().getUserByEmail(n.getMandante()).getSurname()+" ha accettato la tua richiesta di partecipare all evento: "+EventFactory.getInstance().getEventById(n.getEvento()).getTitolo());
                info.setId(View.generateViewId());

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO implementa logica
                    }
                });
                delete.setId(View.generateViewId());
                break;

            case Notifications.MODIFICA:
                notificationView = (LinearLayout) LayoutInflater.from(c).inflate(R.layout.template_notification_notice, null);
                info = (TextView) notificationView.findViewById(R.id.notifiNoticeInfo);
                delete = (Button) notificationView.findViewById(R.id.notifNoticeOk);
                info.setText("L'utente " + PersonFactory.getInstance().getUserByEmail(n.getMandante()).getName()+PersonFactory.getInstance().getUserByEmail(n.getMandante()).getSurname()+" ha apportato modifiche all'evento: "+EventFactory.getInstance().getEventById(n.getEvento()).getTitolo());
                info.setId(View.generateViewId());

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO implementa logica
                    }
                });
                delete.setId(View.generateViewId());
                break;

            default:
                notificationView = (LinearLayout) LayoutInflater.from(c).inflate(R.layout.template_notification_notice, null);
                info = (TextView) notificationView.findViewById(R.id.notifiNoticeInfo);
                delete = (Button) notificationView.findViewById(R.id.notifNoticeOk);
                info.setText("L'utente " + PersonFactory.getInstance().getUserByEmail(n.getMandante()).getName()+PersonFactory.getInstance().getUserByEmail(n.getMandante()).getSurname()+" ha annullato l'evento: "+EventFactory.getInstance().getEventById(n.getEvento()).getTitolo());
                info.setId(View.generateViewId());

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO implementa logica
                    }
                });
                delete.setId(View.generateViewId());
                break;
        }

        return notificationView;

    }



}
