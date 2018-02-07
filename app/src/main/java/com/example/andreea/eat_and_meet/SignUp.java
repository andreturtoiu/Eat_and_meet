package com.example.andreea.eat_and_meet;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Andreea on 31/01/2018.
 */


public class SignUp extends AppCompatActivity {

    Person person;
    EditText nameText, surnameText, birthText, emailText, passwordText, addressText, cityText, phoneText;
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
        birthText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerFragment.show(getFragmentManager(), "datePicker");
            }
        });

        birthText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
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

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInput()){

                    UpdatePerson();


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

            emailText.setError("Inserire la data di nascita");
            errors++;


        }
        else{
            emailText.setError(null);
        }

        if(passwordText.getText() == null || passwordText.getText().length() == 0){

            passwordText.setError("Inserire la data di nascita");
            errors++;


        }
        else{
            passwordText.setError(null);
        }

        if(addressText.getText() == null || addressText.getText().length() == 0){

            addressText.setError("Inserire la data di nascita");
            errors++;


        }
        else{
            addressText.setError(null);
        }

        if(cityText.getText() == null || cityText.getText().length() == 0){

            cityText.setError("Inserire la data di nascita");
            errors++;


        }
        else{
            cityText.setError(null);
        }

        if(phoneText.getText() == null || phoneText.getText().length() == 0){

            phoneText.setError("Inserire la data di nascita");
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

    }


}


