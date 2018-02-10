package com.example.andreea.eat_and_meet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;

public class CreateEvent2 extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_PICK_IMAGE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_events2);

        findViewById(R.id.pickImageBtn).setOnClickListener(this);
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
            ImageView imageView = new ImageView(this);
            imageView.setImageBitmap(bitmap);

            //Convertiamo il 100pixel in 100dp
            int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
            //Imposto le dimensioni e i margini dell'immagine
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
            params.setMargins(20, 0, 20,0);
            imageView.setLayoutParams(params);

            //Aggiungo l'immagine al linear layout dentro la scrollview
            ViewGroup container = (ViewGroup) findViewById(R.id.imageContainer);
            container.addView(imageView);
        }
    }
}