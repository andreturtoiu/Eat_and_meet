package com.example.andreea.eat_and_meet;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateEvent2 extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_PICK_IMAGE = 1;
    private List<BitmapDataObject> fotoUriList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_events2);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.pickImageBtn).setOnClickListener(this);
        findViewById(R.id.create_event_btn).setOnClickListener(this);

        fotoUriList = new ArrayList<BitmapDataObject>();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.pickImageBtn) {
            //Creo un intent che lancia un'activity per selezionare un'immagine dalla galleria
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

            //Setto il tipo di file da cercare
            intent.setType("image/*");

            //Lancio l'activity dandogli un identificativo che mi servirà a prendermi il risultato
            startActivityForResult(intent, REQUEST_PICK_IMAGE);
        }

        if (view.getId() == R.id.create_event_btn) {
            android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(CreateEvent2.this);
            builder1.setMessage("Vuoi creare l'evento?");
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "Si",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent rate = new Intent(CreateEvent2.this, HomePage.class);
                            saveEvent();
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
            android.support.v7.app.AlertDialog alert11 = builder1.create();
            alert11.show();
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

            fotoUriList.add(new BitmapDataObject(bitmap));

            //Creo una ImageView con l'immagine appena presa
            ImageView imageView = new ImageView(this);
            imageView.setImageBitmap(bitmap);

            //Convertiamo il 100pixel in 100dp
            int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
            //Imposto le dimensioni e i margini dell'immagine
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
            params.setMargins(20, 0, 20, 0);
            imageView.setLayoutParams(params);

            //Aggiungo l'immagine al linear layout dentro la scrollview
            ViewGroup container = (ViewGroup) findViewById(R.id.imageContainer);
            container.addView(imageView);
        }
    }

    private void saveEvent() {
        //Prendo l'evento parziale salvato nella factory e lo aggiorno con le nuove
        //informazioni inserite

        EditText editTextDesc = (EditText) findViewById(R.id.eventDescription);
        EditText editTextMaxBookings = (EditText) findViewById(R.id.maxPerson);
        String description = editTextDesc.getText().toString();
        int maxBookings = 5;
        int errors = 0;
        editTextDesc.setError(null);
        editTextMaxBookings.setError(null);

        try{
            maxBookings = Integer.parseInt(editTextMaxBookings.getText().toString());
            if (maxBookings < 2){
                editTextMaxBookings.setError("Il numero minimo è 2");
                errors++;
            }
        }
        catch (NumberFormatException e){
            editTextMaxBookings.setError("Inserire un numero intero");
            errors++;
        }

        int min_size = 10;
        if(description.length() < min_size){
            editTextDesc.setError("Inserire una descrizione di almeno "+min_size);
            errors++;
        }

        if(errors > 0) return;

        editTextDesc.setError(null);
        editTextMaxBookings.setError(null);

        EventFactory factory = EventFactory.getInstance();

        Event event = factory.getPartialEvent();
        event.setDescrizione(description);
        event.setFotoUriList(fotoUriList);
        event.setMaxBookings(maxBookings);

        factory.addEvent(event);

        //Setto l'evento parziale a null perché lo sto salvando nella lista eventi
        factory.setPartialEvent(null);

        Intent intent = new Intent(this, HomePage.class);
        //Questo flag serve a togliere le activity di creazione evento dalla cronologia delle activity
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}