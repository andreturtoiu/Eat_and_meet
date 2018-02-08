package com.example.andreea.eat_and_meet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Michele on 08/02/2018.
 */

public class EditEvent extends AppCompatActivity {
    Event evento;
    EditText info;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        Intent intent = getIntent();
        Object obj = intent.getSerializableExtra("EVENT_EXTRA");
        if (!(obj instanceof Event))
            return;
        evento = (Event) obj;

        TextView titolo = (TextView) findViewById(R.id.TitoloId);
        titolo.setText(evento.getTitolo());
        info = (EditText) findViewById(R.id.InfoId);
        info.setText(evento.getDescrizione());
        TextView cucina = (TextView) findViewById(R.id.CucinaId);
        cucina.setText(evento.getCucina());
        TextView address = (TextView) findViewById(R.id.AddressId);
        address.setText(evento.getIndirizzo());
        TextView partecipanti = (TextView) findViewById(R.id.IscrittiId);
        partecipanti.setText(evento.getPartecipanti().size()+"");
        TextView data = (TextView) findViewById(R.id.DataId);
        data.setText(evento.getData());

        findViewById(R.id.ConfirmEditButton).setOnClickListener(new ConfirmBtn());
        findViewById(R.id.AbortEditButton).setOnClickListener(new AbortBtn());

        ArrayList<Integer> fotoList = evento.getFotoList();
        LinearLayout ss = (LinearLayout) findViewById(R.id.SlideshowId);
        for(Integer i:fotoList){ //i corrisponde a R.drawable.immagine
            ImageView foto = new ImageView(this);
            foto.setImageResource(i);
            //Imposto dimensione
            foto.setLayoutParams(new LinearLayout.LayoutParams(200,200));
            ss.addView(foto);
        }


    }
    class ConfirmBtn implements View.OnClickListener {
        public void onClick(View v) {
            Intent intent = new Intent(EditEvent.this, ShowEvent.class);
            evento.setDescrizione(info.getText().toString());
            EventFactory.getInstance().editEvent(evento);
            intent.putExtra("EVENT_EXTRA",evento);
            startActivity(intent);

        }
    }
    class AbortBtn implements View.OnClickListener {
        public void onClick(View v) {
            Intent intent = new Intent(EditEvent.this, ShowEvent.class);
            intent.putExtra("EVENT_EXTRA",evento);
            startActivity(intent);

        }
    }


}
