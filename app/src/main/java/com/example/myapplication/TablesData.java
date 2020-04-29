package com.example.myapplication;

import java.util.ArrayList;

public class TablesData {

    public static ArrayList<Long> intervListe = new ArrayList<Long>();
    public static ArrayList<Long> categoryListe = new ArrayList<Long>();
    public static ArrayList<Long> usersList = new ArrayList<Long>();


    public static void addIntervention(long id) {
        intervListe.add(id);
    }

    public static void addCategory(long id) {
        categoryListe.add(id);
    }

    //Users Table
    public static final String USERES_TABLE = "users";
    public static final String USERID = "IdUser";
    public static final String USERNAME = "NameUser";
    public static final String USERPASSWORD = "Password";
    public static final String USERPROFIL ="ProfilUser";
    public static final String USERTECH="Technicien";
    public static final String USERADMIN="Admin";

    //Intervention Table
    public static final String INTERVENTION_TABLE = "interventions";
    public static final String IdInterv = "IdInterv";
    public static final String NomClient = "NomClient";
    public static final String MobileClient = "MobileClient";
    public static final String TitreInterv= "TitreInterv";
    public static final String DateInterv = "DateInterv";
    public static final String Description= "Description";
    public static final String ExecInterv = "ExecInterv";

    //Category Table
    public static final String CATEGORIES_TABLE = "categories";
    public static final String IdCateg = "IdCateg";
    public static final String NameCateg= "NameCateg";

    // Table
    public static final String INTERCATEGTECH_TABLE = "intervTechCateg";
    public static final String INTERVTECHCATEG_INTERVID = "INTERVTECHCATEG_INTERVID ";
    public static final String INTERVTECHCATEG_TechId = "INTERVTECHCATEG_TechId";
    public static final String INTERVTECHCATEG_CategId= "INTERVTECHCATEG_CategId";
}
