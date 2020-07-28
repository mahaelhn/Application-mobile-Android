package com.example.myapplication;

public class Category {
    public int IdCateg ;
    public  String NameCateg;

    public Category( int IdCateg, String NameCateg){
        this.IdCateg= IdCateg;
        this.NameCateg = NameCateg;
    }
    public String toString(){

        return IdCateg + "\n" + NameCateg;
    }
}
