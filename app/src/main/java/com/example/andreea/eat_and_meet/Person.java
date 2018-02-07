package com.example.andreea.eat_and_meet;



import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by Quontini on 05/02/2018.
 */

public class Person implements Serializable {

    private String name;
    private String surname;
    private Calendar birthdate;
    private String password;
    private String email;
    private String address;
    private String city;
    private String phoneNumber;


    public Person(){

        this.setName("");
        this.setSurname("");
        this.setPassword("");
        this.setEmail("");
        this.setAddress("");
        this.setCity("");
        this.setPhoneNumber("");

    }

    public Person(String name,String  surname,String  birthdate,String  password,String  email,String  address,String  city,String  phoneNumber){

        this.setName(name);
        this.setSurname(surname);
        this.setPassword(password);
        this.setEmail(email);
        this.setAddress(address);
        this.setCity(city);
        this.setPhoneNumber(phoneNumber);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Calendar getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Calendar birthdate) { this.birthdate = birthdate; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }




}
