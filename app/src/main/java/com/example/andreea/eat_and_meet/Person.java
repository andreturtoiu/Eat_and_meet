package com.example.andreea.eat_and_meet;



import java.io.Serializable;
import java.util.ArrayList;
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
    private ArrayList<RatingLoggedProfile> ratings;
    private ArrayList<Notifications> myNotifications;
    private int foto;


    public Person(){

        this.setName("");
        this.setSurname("");
        this.setPassword("");
        this.setEmail("");
        this.setAddress("");
        this.setCity("");
        this.setPhoneNumber("");
        this.ratings = new ArrayList<RatingLoggedProfile>(0);
        this.setMyNotifications(new ArrayList<Notifications>());


    }

    public Person(String name,String  surname,Calendar  birthdate,String  password,String  email,String  address,String  city,
                  String  phoneNumber, ArrayList<RatingLoggedProfile> ratingsList, ArrayList<Notifications> myNotifications, int foto){

        this.setName(name);
        this.setSurname(surname);
        this.birthdate=birthdate;
        this.setPassword(password);
        this.setEmail(email);
        this.setAddress(address);
        this.setCity(city);
        this.setPhoneNumber(phoneNumber);
        this.setRatings(ratingsList);
        this.setMyNotifications(myNotifications);
        this.setFoto(foto);
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

    public ArrayList<RatingLoggedProfile> getRatings(){ return this.ratings;}

    public void setRatings(ArrayList<RatingLoggedProfile> ratings){
        this.ratings = ratings;
    }

    public void addRatings(RatingLoggedProfile r){
        this.ratings.add(r);
    }

    public void setFoto(int foto) { this.foto=foto; }

    public int getFoto () {return this.foto;}


    public ArrayList<Notifications> getMyNotifications() { return myNotifications; }

    public void setMyNotifications(ArrayList<Notifications> myNotifications) { this.myNotifications = myNotifications; }
}
