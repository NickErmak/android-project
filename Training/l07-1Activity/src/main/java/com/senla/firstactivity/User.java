package com.senla.firstactivity;

import android.os.Parcel;

import java.io.Serializable;

public class User implements Serializable {

    private String login;
    private String password;
    private String name;
    private String surname;
    private String gender;
    private String addInfo;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        name = "none";
        surname = "none";
        addInfo = "none";
    }

    public User(String login, String password, String name, String surname, String gender, String addInfo) {
        this(login, password);
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.addInfo = addInfo;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public String getLogin() {

        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}


