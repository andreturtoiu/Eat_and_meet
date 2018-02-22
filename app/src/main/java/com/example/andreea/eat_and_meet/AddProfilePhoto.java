package com.example.andreea.eat_and_meet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import java.util.List;

/**
 * Created by Andreea on 22/02/2018.
 */

public class AddProfilePhoto extends AppCompatActivity implements View.OnClickListener{

    private static final int REQUEST_PICK_IMAGE = 1;
    private BitmapDataObject foto;
    ImageView img;
    private String email;
    private int clicked = 0;
    LinearLayout ll;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile_photo);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        email = intent.getStringExtra("EXTRA_PHOTO");
        ll = (LinearLayout)findViewById(R.id.imageContainer);
        findViewById(R.id.pickImageBtn).setOnClickListener(this);
        findViewById(R.id.add_photo).setOnClickListener(this);

    }



    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.pickImageBtn) {
            clicked +=1;
            if(clicked == 1){
                changePhoto();
            }else if(clicked>1){
                ll.removeAllViews();
                changePhoto();

            }

        }

        if (view.getId() == R.id.add_photo) {
           savePhoto();
        }
    }

    protected void changePhoto(){

        //Creo un intent che lancia un'activity per selezionare un'immagine dalla galleria
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        //Setto il tipo di file da cercare
        intent.setType("image/*");

        //Lancio l'activity dandogli un identificativo che mi servirà a prendermi il risultato
        startActivityForResult(intent, REQUEST_PICK_IMAGE);

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

            foto = new BitmapDataObject(bitmap);

            ImageView imageView = new ImageView(this);
            imageView.setImageBitmap(bitmap);
            int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
            params.setMargins(20, 0, 20, 0);
            imageView.setLayoutParams(params);

            //Aggiungo l'immagine al linear
            LinearLayout container = (LinearLayout) findViewById(R.id.imageContainer);
            container.addView(imageView);

        }
    }

    private void savePhoto() {

        Person p = PersonFactory.getInstance().getUserByEmail(email);
        p.setFoto2(foto);
        Intent intent = new Intent(this, LoggedProfile.class);
        //Questo flag serve a togliere le activity di creazione evento dalla cronologia delle activity
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


}
