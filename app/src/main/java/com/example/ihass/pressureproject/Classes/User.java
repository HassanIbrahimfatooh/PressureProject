package com.example.ihass.pressureproject.Classes;

public class User {
    private String Name, Email, Password;
    private int Age, PhoneNumber;

    public User() {
    }

    public User(String name, String email, String password, int age, int phoneNumber) {
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
