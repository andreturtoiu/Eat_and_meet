package com.example.andreea.eat_and_meet;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Michele on 08/02/2018.
 */

public class EditEvent extends AppCompatActivity {
    Event evento;
    EditText info;
    private static final int REQUEST_PICK_IMAGE = 1;
    DatePickerDialog datePickerDialog;
    LinearLayout.LayoutParams fotoParams;

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
        data.setText(evento.showDateOnly());
        data.setOnClickListener(new EditDate());
        TextView time = (TextView) findViewById(R.id.TimeId);
        time.setText(evento.showTimeOnly());
        time.setOnClickListener(new EditTime());
        findViewById(R.id.ConfirmEditButton).setOnClickListener(new ConfirmBtn());
        findViewById(R.id.AbortEditButton).setOnClickListener(new AbortBtn());

        ArrayList<Integer> fotoList = evento.getFotoList();
        LinearLayout ss = (LinearLayout) findViewById(R.id.SlideshowId);
        int dim = (findViewById(R.id.scroll_slideshow)).getLayoutParams().height;
        fotoParams = new LinearLayout.LayoutParams(dim,LinearLayout.LayoutParams.MATCH_PARENT);

        ImageButton addFoto = (ImageButton) findViewById(R.id.pickImageBtn);
        addFoto.setLayoutParams(fotoParams);
        addFoto.setOnClickListener(new AddFotoListener());

        for(Integer i:fotoList){ //i corrisponde a R.drawable.immagine
            ImageView foto = new ImageView(this);
            foto.setImageResource(i);
            //Imposto dimensione
            foto.setLayoutParams(fotoParams);
            foto.setScaleType(ImageView.ScaleType.FIT_XY);
            ss.addView(foto);
        }


    }
    class ConfirmBtn implements View.OnClickListener {
        public void onClick(View v) {
            final String loggedUser = PersonFactory.getInstance().getLoggedUser();
            if(!evento.isTimeValid()){
                //errore non valido
                AlertDialog.Builder builder1 = new AlertDialog.Builder(EditEvent.this);
                builder1.setMessage("Gli eventi si possono svolgere solo nei seguenti orari:\nPranzo: 11:00 - 15:00\nCena: 16:00 - 22:00");
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
                return;
            }
            final Event e = EventFactory.getInstance().isDateReserved(evento,loggedUser);
            if(e != null){
                AlertDialog.Builder builder1 = new AlertDialog.Builder(EditEvent.this);
                builder1.setMessage("Hai già un impegno per l'evento: "+e.getTitolo());
                builder1.setCancelable(false);
                builder1.setPositiveButton(
                        "Mostra Evento",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(EditEvent.this,ShowEvent.class);
                                String loggedUser = PersonFactory.getInstance().getLoggedUser();
                                intent.putExtra("EVENT_EXTRA",e);
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
                return;
            }

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

    class EditDate implements  View.OnClickListener{
        public void onClick(View v){
            //Mi prendo l'ora attuale
            final Calendar c = evento.getDataCalendar();
            int day = c.get(Calendar.DAY_OF_MONTH);
            int month = c.get(Calendar.MONTH);
            int year = c.get(Calendar.YEAR);
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            //Creo una dialog per prendere l'ora impostandogli l'ora attuale e iscrivendo
            //la main activity all'evento di pick dell'ora
            datePickerDialog = new DatePickerDialog(EditEvent.this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            TextView date = (TextView) findViewById(R.id.DataId);
                            // set day of month , month and year value in the edit text
                            date.setText(dayOfMonth + "/"
                                    + (monthOfYear + 1) + "/" + year);
                            int hour = c.get(Calendar.HOUR_OF_DAY);
                            int minute = c.get(Calendar.MINUTE);
                            evento.setData(year,monthOfYear,dayOfMonth,hour,minute);
                        }
                    }, year, month, day);
            datePickerDialog.show();
        }
    }

    class EditTime implements TimePickerDialog.OnTimeSetListener, View.OnClickListener{
        public void onClick(View v){
            final Calendar c = evento.getDataCalendar();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            //Creo una dialog per prendere l'ora impostandogli l'ora attuale e iscrivendo
            //la main activity all'evento di pick dell'ora
            TimePickerDialog dialog = new TimePickerDialog(EditEvent.this, this, hour, minute, true);
            dialog.show();
        }
        public void onTimeSet(TimePicker timepicker, int hour, int minutes){
            int day = evento.getDataCalendar().get(Calendar.DAY_OF_MONTH);
            int month = evento.getDataCalendar().get(Calendar.MONTH);
            int year = evento.getDataCalendar().get(Calendar.YEAR);
            TextView textView = (TextView) findViewById(R.id.TimeId);
            textView.setText(String.format("%d:%d", hour, minutes));
            evento.setData(year,month,day,hour,minutes);

        }
    }

    class AddFotoListener implements View.OnClickListener{
        public void onClick(View view) {

                //Creo un intent che lancia un'activity per selezionare un'immagine dalla galleria
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

                //Setto il tipo di file da cercare
                intent.setType("image/*");

                //Lancio l'activity dandogli un identificativo che mi servirà a prendermi il risultato
                startActivityForResult(intent, REQUEST_PICK_IMAGE);

        }


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Controllo se ha preso correttamente l'immagine
        if (requestCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK) {
            //L'Uri è un indirizzo che identifica l'immagine all'interno del device
            Uri selectedimg = data.getData();
            Bitmap bitmap = null;
            try {
                //Bitmap sono immagini non compresse
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedimg);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Se non è riuscito a prendere l'immagine annullo
            if (bitmap == null) {
                return;
            }

            //Creo una ImageView con l'immagine appena presa
            ImageView imageView = new ImageView(EditEvent.this);
            imageView.setImageBitmap(bitmap);


            imageView.setLayoutParams(fotoParams);

            //Aggiungo l'immagine al linear layout dentro la scrollview
            LinearLayout container = (LinearLayout) findViewById(R.id.SlideshowId);
            container.addView(imageView);
        }
    }




}
