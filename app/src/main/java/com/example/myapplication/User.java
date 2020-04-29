package com.example.myapplication;

public class User {
    private int idUser;
    private String Username;
    private String Password;

    public User(int idUser, String Username, String Password) {
        this.idUser = idUser;
        this.Username = Username;
        this.Password = Password;
    }
    public User(int idUser, String nameUser) {
        this.idUser = idUser;
        this.Username = Username;
    }


    public User() {
        this.idUser = 0;
        this.Username = null;
        this.Password = null;
    }

    public int getIdUser() {
        return idUser;
    }
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
    public String getNameUser() {
        return Username;
    }
    public void setNameUser(String Username) {
        this.Username = Username;
    }
    public String  getPassword() {
        return Password;
    }
    public void setPassword(String Password) {
        this.Password = Password;
    }
    @Override
    public String toString() {
        return  idUser + "-" + Username + "-" +Password ;
    }
}
