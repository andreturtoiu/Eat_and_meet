package com.example.andreea.eat_and_meet;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.UTFDataFormatException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Andreea on 31/01/2018.
 */

public class SignUp extends AppCompatActivity {
    private static final int REQUEST_PICK_IMAGE = 1;
    PersonFactory utenti = PersonFactory.getInstance();
    Person person;
    EditText nameText, surnameText, birthText, emailText, passwordText, addressText, cityText, phoneText;
    ImageView fotoImage;
    BitmapDataObject user_foto;
    Button saveButton;
 //   TextView nameError, surnameError, emailError, passwordError, addressError, cityError, phoneError;
    boolean isResumed;
    DatePickerFragment datePickerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        person = new Person();
        isResumed = false;
        datePickerFragment = new DatePickerFragment();
        user_foto = null;

        fotoImage = (ImageView) findViewById(R.id.user_pic);
        nameText = (EditText) findViewById(R.id.nomeSignUp);
        surnameText = (EditText) findViewById(R.id.cognomeSignUp);
        birthText = (EditText) findViewById(R.id.date);
        emailText = (EditText) findViewById(R.id.email);
        passwordText = (EditText) findViewById(R.id.passSigUp);
        addressText = (EditText) findViewById(R.id.address);
        cityText = (EditText) findViewById(R.id.city);
        phoneText = (EditText) findViewById(R.id.phone);

/*
        nameError = (TextView) this.findViewById(R.id.nameErrorSignUp);
        nameError.setVisibility(View.GONE);
        surnameError = (TextView) this.findViewById(R.id.surnameErrorSignUp);
        surnameError.setVisibility(View.GONE);
        emailError = (TextView) this.findViewById(R.id.emailErrorSignUp);
        emailError.setVisibility(View.GONE);
        passwordError = (TextView) this.findViewById(R.id.passwordErrorSignUp);
        passwordError.setVisibility(View.GONE);
        addressError = (TextView) this.findViewById(R.id.addressErrorSignUp);
        addressError.setVisibility(View.GONE);
        cityError = (TextView) this.findViewById(R.id.cityErrorSignUp);
        cityError.setVisibility(View.GONE);
        phoneError = (TextView) this.findViewById(R.id.phoneErrorSignUp);
        phoneError.setVisibility(View.GONE);
*/

        fotoImage.setOnClickListener(new AddFotoListener());

        birthText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerFragment.show(getFragmentManager(), "datePicker");
            }
        });

        birthText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    datePickerFragment.show(getFragmentManager(), "datePicker");

                }
            }
        });
      datePickerFragment.setOnDatePickerFragmentChanged(new DatePickerFragment.DatePickerFragmentListener() {
            @Override
            public void onDatePickerFragmentOkButton(DialogFragment dialog, Calendar date) {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                birthText.setText(format.format(date.getTime()));
            }

            @Override
            public void onDatePickerFragmentCancelButton(DialogFragment dialog) {}
        });

        saveButton = (Button) this.findViewById(R.id.signUpConfirm);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInput()){

                    UpdatePerson();
                    utenti.addUser(person);
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
                    View popview = getLayoutInflater().inflate(R.layout.signup_dialog, null);
                    Button confirm = (Button) popview.findViewById(R.id.ConfirmButton);
                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent login = new Intent(SignUp.this, Login_activity.class);
                            startActivity(login);
                        }
                    });

                    builder.setView(popview);
                    AlertDialog dialog = builder.create();
                    dialog.show();



                }
            }
        });


    }





    @Override
    public void onResume(){
        super.onResume();
        isResumed = true;

    }

    private boolean checkInput(){

        int errors=0;

        if(nameText.getText() == null || nameText.getText().length()==0){

            nameText.setError("Inserire il nome");
            errors++;


        }
        else {
            nameText.setError(null);
        }

        if(surnameText.getText() == null || surnameText.getText().length()==0){

            surnameText.setError("Inserire il cognome");
            errors++;


        }
        else{
            surnameText.setError(null);
        }

        if(birthText.getText() == null || birthText.getText().length() == 0){

            birthText.setError("Inserire la data di nascita");
            errors++;


        }
        else{
            birthText.setError(null);
        }

        if(emailText.getText() == null || emailText.getText().length() == 0){

            emailText.setError("Inserire l'email");

            errors++;


        }
        else{

            if(!isEmailValid(emailText.getText())) {
                emailText.setError("Inserisci un formato valido");
                errors++;
            }
            else {
                if(utenti.isUserRegistered(emailText.toString())) {
                    emailText.setError("Utente già registrato");
                    errors++;
                }
                else emailText.setError(null);
            }

        }

        if(passwordText.getText() == null || passwordText.getText().length() == 0){

            passwordText.setError("Inserire la password");
            errors++;


        }
        else{
            passwordText.setError(null);
        }

        if(addressText.getText() == null || addressText.getText().length() == 0){

            addressText.setError("Inserire un indirizzo");
            errors++;


        }
        else{
            addressText.setError(null);
        }

        if(cityText.getText() == null || cityText.getText().length() == 0){

            cityText.setError("Inserire la città");
            errors++;


        }
        else{
            cityText.setError(null);
        }

        if(phoneText.getText() == null || phoneText.getText().length() == 0){

            phoneText.setError("Inserire il numero di telefono");
            errors++;


        }
        else{
            phoneText.setError(null);
        }

        return errors == 0;
    }

    private void UpdatePerson(){

        this.person.setName(this.nameText.getText().toString());
        this.person.setSurname(this.surnameText.getText().toString());
        this.person.setBirthdate(this.datePickerFragment.getDate());
        this.person.setEmail(this.emailText.getText().toString());
        this.person.setPassword(this.passwordText.getText().toString());
        this.person.setAddress(this.addressText.getText().toString());
        this.person.setCity(this.cityText.getText().toString());
        this.person.setPhoneNumber(this.phoneText.getText().toString());
        //this.person.setFoto(-1);
        this.person.setFoto2(user_foto);

    }

    private boolean isEmailValid(CharSequence email) {

        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
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

            user_foto = new BitmapDataObject(bitmap);

            //Creo una ImageView con l'immagine appena presa
            fotoImage.setImageBitmap(bitmap);

        }
    }


}


