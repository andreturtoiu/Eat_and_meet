package com.example.andreea.eat_and_meet;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.DatePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class CreateEvent extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private DatePickerDialog datePickerDialog;
    private TextView dateTextView;
    private TextView timeTextView;
    private int day, month, year, hour, minutes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        Person loggedUser;
        String emailUser;

        Button prosegui = (Button) findViewById(R.id.prosegui);
        //Iscrivo la mainActivity all'evento di click del bottone
        prosegui.setOnClickListener(this);

        findViewById(R.id.timeTextView).setOnClickListener(this);
        findViewById(R.id.dateTextView).setOnClickListener(this);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout1);

        emailUser = PersonFactory.getInstance().getLoggedUser();
        loggedUser = PersonFactory.getInstance().getUserByEmail(emailUser);

        View headerview = navigationView.getHeaderView(0);
        TextView profilename = (TextView) headerview.findViewById(R.id.name);
        profilename.setText(loggedUser.getName() + " " + loggedUser.getSurname());


        ImageView img = (ImageView) headerview.findViewById(R.id.imageView);
        img.setImageResource(loggedUser.getFoto());


        headerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = PersonFactory.getInstance().getLoggedUser();
                Intent t1 = new Intent(CreateEvent.this, LoggedProfile.class);
                startActivity(t1);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();

                if (item.getItemId() == R.id.events) {
                    Intent t = new Intent(CreateEvent.this, HomePage.class);
                    startActivity(t);
                } else if (item.getItemId() == R.id.create_event) {
                    Intent t = new Intent(CreateEvent.this, CreateEvent.class);
                    startActivity(t);

                } else if (item.getItemId() == R.id.search_events) {
                    Intent t = new Intent(CreateEvent.this, SearchEvents.class);
                    startActivity(t);

                } else if (item.getItemId() == R.id.notifies) {

                } else if (item.getItemId() == R.id.logout) {
                    Intent t = new Intent(CreateEvent.this, Login_activity.class);
                    startActivity(t);
                } else if (item.getItemId() == R.id.imageView) {
                    Intent t = new Intent(CreateEvent.this, LoggedProfile.class);
                    startActivity(t);

                }
                return false;
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Crea Evento");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //saveButton = (Button) this.findViewById(R.id.prosegui);

        //saveButton.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //   public void onClick(View v) {
        //        if(checkInput()){
        //
        //        }
        //    }
        //});


    }

    @Override
    protected void onResume() {
        super.onResume();

        //Prendo l'evento salvato come parziale
        Event event = EventFactory.getInstance().getPartialEvent();

        //Se l'evento è nullo vuol dire che stiamo visitando questa pagina per la
        //prima volta
        if (event != null) {
            //inserisco le informazioni salvate dentro le varie view
            EditText titleView = (EditText) findViewById(R.id.editEvent);
            titleView.setText(event.getTitolo());

            EditText addressView = (EditText) findViewById(R.id.editIndirizzo);
            addressView.setText(event.getIndirizzo());

            EditText cityView = (EditText) findViewById(R.id.editCitta);
            cityView.setText(event.getCity());
        }
    }

    public void onRadioButtonClicked(View view) {
    }

    @Override
    public void onClick(View view) {
        //Controllo se la view che è stata cliccata è il bottone per proseguire
        if (view.getId() == R.id.prosegui) {
            saveEventData();

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
            datePickerDialog = new DatePickerDialog(CreateEvent.this, this, year, month, day);
            datePickerDialog.show();
        }
    }

    private void saveEventData() {
        //Prendo le informazioni dell'evento dalle view e le salvo dentro la factory

        String title = ((EditText) findViewById(R.id.editEvent)).getText().toString();
        String address = ((EditText) findViewById(R.id.editIndirizzo)).getText().toString();
        String city = ((EditText) findViewById(R.id.editCitta)).getText().toString();

        Spinner typeSpinner = (Spinner) findViewById(R.id.spinner);
        String type = typeSpinner.getSelectedItem().toString();

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        int pranzo_cena = checkedRadioButtonId == R.id.check_pranzo ? Event.PRANZO : Event.CENA;

        String userEmail = PersonFactory.getInstance().getLoggedUser();

        Event event = new Event();
        event.setTitolo(title);
        event.setVia(address);
        event.setCity(city);
        event.setCucina(type);
        event.setPranzo_cena(pranzo_cena);
        event.setData(new GregorianCalendar(year, month, day, hour, minutes));
        event.setUser(userEmail);
        EventFactory.getInstance().setPartialEvent(event);
    }

    @Override
    public void onTimeSet(TimePicker timepicker, int hour, int minutes) {
        this.hour = hour;
        this.minutes = minutes;

        //setto il testo della textview con l'ora e minuti presi dalla dialog
        timeTextView = (TextView) findViewById(R.id.timeTextView);
        timeTextView.setText(String.format("%d:%d", hour, minutes));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = month;
        this.day = dayOfMonth;

        dateTextView = (TextView) findViewById(R.id.dateTextView);
        // set day of month , month and year value in the edit text
        dateTextView.setText(dayOfMonth + "/" + (month + 1) + "/" + year);

    }


//    private boolean checkInput() {
//
//        int errors = 0;
//
//        if (titleEditView.getText() == null || titleEditView.getText().length() == 0) {
//
//            titleEditView.setError("Inserire il nome dell'evento");
//            errors++;
//
//        } else {
//            titleEditView.setError(null);
//        }
//
//        if (addressEditView.getText() == null || addressEditView.getText().length() == 0) {
//
//            addressEditView.setError("Inserire indirizzo e città");
//            errors++;
//
//        } else {
//            addressEditView.setError(null);
//        }
//
//
//        return errors == 0;
//    }

}