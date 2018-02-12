package com.example.andreea.eat_and_meet;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.DatePicker;
import java.util.Calendar;

public class CreateEvent extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_events);

        Button prosegui = (Button) findViewById(R.id.prosegui);
        //Iscrivo la mainActivity all'evento di click del bottone
        prosegui.setOnClickListener(this);

        findViewById(R.id.timeTextView).setOnClickListener(this);
        findViewById(R.id.dateTextView).setOnClickListener(this);
    }

    public void onRadioButtonClicked(View view) {
    }

    @Override
    public void onClick(View view) {
        //Controllo se la view che è stata cliccata è il bottone per proseguire
        if (view.getId() == R.id.prosegui) {
            //Creo un intent per andare alla prossima activity
            Intent intent = new Intent(this, CreateEvent2.class);
            startActivity(intent);
        }

        if (view.getId() == R.id.timeTextView) {
            //Mi prendo l'ora attuale
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            //Creo una dialog per prendere l'ora impostandogli l'ora attuale e iscrivendo
            //la main activity all'evento di pick dell'ora
            TimePickerDialog dialog = new TimePickerDialog(this, this, hour, minute, true);
            dialog.show();
        }

        if (view.getId() == R.id.dateTextView) {
            //Mi prendo l'ora attuale
            final Calendar c = Calendar.getInstance();
            int day = c.get(Calendar.DAY_OF_MONTH);
            int month = c.get(Calendar.MONTH);
            int year = c.get(Calendar.YEAR);

            //Creo una dialog per prendere l'ora impostandogli l'ora attuale e iscrivendo
            //la main activity all'evento di pick dell'ora
            datePickerDialog = new DatePickerDialog(CreateEvent.this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            TextView date = (TextView) findViewById(R.id.dateTextView);
                            // set day of month , month and year value in the edit text
                            date.setText(dayOfMonth + "/"
                                    + (monthOfYear + 1) + "/" + year);

                        }
                    }, year, month, day);
            datePickerDialog.show();
        }
    }
    @Override
    public void onTimeSet(TimePicker timepicker, int hour, int minutes) {
        //setto il testo della textview con l'ora e minuti presi dalla dialog
        TextView textView = (TextView) findViewById(R.id.timeTextView);
        textView.setText(String.format("%d:%d", hour, minutes));
    }
}