package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDataBase  extends SQLiteOpenHelper {

    //Data
    public  final static String DB_NAME = "Techworkspace.db";
    public final static int DBVERSION = 1;
    public  MyDataBase(@Nullable Context context) {
        super(context, DB_NAME, null, DBVERSION);
    }
    //user
    String Users = TablesData.USERES_TABLE;
    String UserName = TablesData.USERNAME;
    String Password = TablesData.PASSWORD;
    String IdUser = TablesData.USERID;
    String ProfilUser = TablesData.PROFIL;
    //Intervention
    String Intervention= TablesData.INTERVENTION_TABLE;
    String IdIntev = TablesData.IdInterv;
    String NomClient= TablesData.NomClient;
    String MobileClient = TablesData.MobileClient;
    String TitreInterv= TablesData.TitreInterv;
    String DateInterv = TablesData.DateInterv;
    String Description = TablesData.Description;
    String ExecInterv = TablesData.ExecInterv;
    //Categories
    String categories= TablesData.CATEGORIES_TABLE;
    String IdCateg = TablesData.IdCateg;
    String NameCateg = TablesData.NameCateg;
    //interventionCategoriesTechnician
    String InterCategTech = TablesData.INTERCATEGTECH_TABLE;
    String InterCategTechIdTech = TablesData.INTERVTECHCATEG_TechId;
    String InterCategTechIdInterv = TablesData.INTERVTECHCATEG_INTERVID;
    String InterCategTechIdCateg = TablesData.INTERVTECHCATEG_CategId;


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //***************create User Table*****************
        String  req1 =  "CREATE TABLE IF NOT EXISTS "+Users+"( " +
                IdUser+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                UserName+" TEXT NOT NULL,"+
                Password + " TEXT NOT NULL," +
                ProfilUser+ " TEXT NOT NULL" +
                " );";

        // Execute the SQL statement
        db.execSQL(req1);
        Log.i("USERS", "Table is created successfully ");

        //*********create intervention table**********
        String  req2 =  "CREATE TABLE IF NOT EXISTS "+Intervention+"( " +
                IdIntev+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                NomClient+" TEXT NOT NULL,"+
                MobileClient+" INTEGER NOT NULL,"+
                TitreInterv+" TEXT NOT NULL,"+
                DateInterv +" TEXT NOT NULL,"+
                Description+" TEXT NOT NULL,"+
                ExecInterv+" INTEGER NOT NULL"+
                " );";

        // Execute the SQL statement
        db.execSQL(req2);
        Log.i("INTERVENTION", "Table is created successfully ");
        //*************create categories table*********

        String  req3=  "CREATE TABLE IF NOT EXISTS "+categories+"( " +
                IdCateg +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                NameCateg +" TEXT NOT NULL"+
                " );";

        // Execute the SQL statement
        db.execSQL(req3);
        Log.i("CATEGORIES", "Table is created successfully ");
        //******create intervention tech categories table*********
        String  req4 =  "CREATE TABLE IF NOT EXISTS "+InterCategTech +"( " +
                InterCategTechIdTech+" INTEGER ,"+
                InterCategTechIdInterv+" INTEGER ,"+
                InterCategTechIdCateg + " INTEGER"+
                " );";

        // Execute the SQL statement
        db.execSQL(req4);
        Log.i("InterCategTech", "Table is created successfully ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //add a User*****************
    public boolean AddUser(String UserName, String Password, String Profil){
        boolean res = false;
        String req = "INSERT INTO Users (UserName, Password, ProfilUser) " +
                "values ('"+UserName+"', '"+Password+"' , '"+Profil+"') ";
        try{
            this.getWritableDatabase().execSQL(req);
            res = true;
            Log.i("stepAdd", "User successfully added");
        }
        catch(Exception e){
            Log.i("stepAdd", "Problem insertion new intervention " + e.getMessage());
        }

        return res;
    }

    // add an intervention************
    public  boolean AddIntervention (String NomClient , int mobileClient , String TitreInterv , String DateInterv, String Description, int ExecInterv){
        boolean exeInt;

        String req = "INSERT INTO  Intervention (NomClient, mobileClient, TitreInterv ,DateInterv , Description, ExecInterv) " +
                "values ('" + NomClient + "' , '" + mobileClient +"' , '" +TitreInterv+ "' , '" +DateInterv+ "' , '" +Description+ "', '" +ExecInterv+ "')";
        try{
            this.getWritableDatabase().execSQL(req);
            exeInt=true;
            Log.i("INTERVETION", NomClient + " intervention successfully added");
        }catch(Exception e){
            exeInt = false;

            Log.i("INTERVETION ", "Problem insertion new intervention"+ e.getMessage());
        }
        return exeInt;
    }

    //add a category**********
    public  boolean AddCategory (int IdCateg, String NameCateg ){
        boolean exeInt ;

        String req = "insert into categories (IdCateg, NameCateg) " +
                "values ('"+IdCateg+"', '"+NameCateg+"')";
        try{
            this.getWritableDatabase().execSQL(req);
            exeInt=true;
            Log.i("CATEGORIES", NameCateg + " category successfully added ");
        }catch(Exception e){
            exeInt = false;

            Log.i("CATEGORIES ", "Problem insertion new user"+ e.getMessage());
        }
        return exeInt;
    }

    //view categories****************
    public ArrayList<Category> getAllCategories(){
        ArrayList<Category> list = new ArrayList<>();
        String req = "SELECT * FROM categories";
        Cursor c = this.getReadableDatabase().rawQuery(req,null);

        c.moveToFirst();
        while(c.isAfterLast() != true){
            Category c1;
            c1 = new Category(c.getInt(0), c.getString(1));
            list.add(c1);
            c.moveToNext();
        }
        c.close();
        return list;
    }

    //view intervention******************
    public ArrayList<Intervention> getAllInterventions(){
        ArrayList<Intervention> list1 = new ArrayList<>();
        String req = "SELECT * FROM intervention";
        Cursor c = this.getReadableDatabase().rawQuery(req,null);

        c.moveToFirst();
        while(c.isAfterLast() != true){
            Intervention i1 = new Intervention(c.getInt(0), c.getString(1), c.getString(2),c.getString(3), c.getString(4), c.getString(5));
            list1.add(i1);
            c.moveToNext();
        }
        c.close();
        return list1;
    }

    //get Users******************
    public ArrayList<User> getAllUser() {

       ArrayList<User> list = new ArrayList<User>();
       String req = "SELECT * FROM Users";

       Cursor c = this.getReadableDatabase().rawQuery(req, null);
       c.moveToFirst();
       while (c.moveToNext()) {
           User u = new User(c.getInt(0), c.getString(1));
           list.add(u);
           c.moveToNext();
           c.close();
       }
       return list;
   }

    //Delete User*************
    public boolean deleteUser (String UserName) {
       boolean res = false;
       String req = "DELETE FROM Users where UserName= '"  + UserName+ "'";
       try{
           this.getWritableDatabase().execSQL(req);
           res = true;
           Log.i("stepDELETE", "deleted successfully ");
       }
       catch(Exception e){
           Log.i("stepDELETE", "problem to delete a user " + e.getMessage());
       }

       return res;
   }


    //get One user*****************
    public User getOneUser(String UserName)
    {
        User us1 = null;
        String req = "SELECT * FROM USERS where UserName= '"  + UserName+ "'";
        return us1;
    }

    //Update**********************
    public void Update(String Username, String Password)
    {
        String req = "UPDATE Users SET Password = "+"'Password' "+ "WHERE UserName = "+ Username;
        this.getWritableDatabase().execSQL(req);

    }

}


