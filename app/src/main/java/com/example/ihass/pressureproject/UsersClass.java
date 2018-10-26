package com.example.ihass.pressureproject;

class PersonalData {
    private String Name, Email, Password;
    private int Age, PhoneNumber;

    public PersonalData() {
    }

    public PersonalData(String name, String email, String password, int age, int phoneNumber) {
        Name = name;
        Email = email;
        Password = password;
        Age = age;
        PhoneNumber = phoneNumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public int getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}

class Measurement {
    private int UpperMeasure, LowerMeasure;
    private String Day;
    private int Time;

    public Measurement() {
    }

    public Measurement(int upperMeasure, int lowerMeasure, String day, int time) {
        UpperMeasure = upperMeasure;
        LowerMeasure = lowerMeasure;
        Day = day;
        Time = time;
    }

    public int getUpperMeasure() {
        return UpperMeasure;
    }

    public void setUpperMeasure(int upperMeasure) {
        UpperMeasure = upperMeasure;
    }

    public int getLowerMeasure() {
        return LowerMeasure;
    }

    public void setLowerMeasure(int lowerMeasure) {
        LowerMeasure = lowerMeasure;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public int getTime() {
        return Time;
    }

    public void setTime(int time) {
        Time = time;
    }
}

public class UsersClass {
    private PersonalData person;
    private Measurement measure;

    public UsersClass() {
    }

    public UsersClass(PersonalData person, Measurement measure) {
        this.person = person;
        this.measure = measure;
    }

    public PersonalData getPerson() {
        return person;
    }

    public void setPerson(PersonalData person) {
        this.person = person;
    }

    public Measurement getMeasure() {
        return measure;
    }

    public void setMeasure(Measurement measure) {
        this.measure = measure;
    }
}
