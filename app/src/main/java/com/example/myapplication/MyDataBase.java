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
    public  final static String DBNAME = "TechWorkspace";
    public final static int DBVERSION = 1;
    public  MyDataBase(@Nullable Context context) {
        super(context, DBNAME, null, DBVERSION);
    }
    //user
    String Users = TablesData.USERES_TABLE;
    String UserName = TablesData.USERNAME;
    String Password = TablesData.USERPASSWORD;
    String IdUser = TablesData.USERID;
    String ProfilUser = TablesData.USERPROFIL;
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
        String  req1 =  "CREATE TABLE "+Users+"( " +
                IdUser+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                UserName+" TEXT NOT NULL,"+
                Password + " TEXT NOT NULL," +
                ProfilUser+ " TEXT NOT NULL" +
                " );";

        // Execute the SQL statement
        db.execSQL(req1);
        Log.i("USERS", "data base is created successfully ");

        //*********create intervention table**********
        String  req2 =  "CREATE TABLE "+Intervention+"( " +
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
        Log.i("INTERVENTION", "data base is created successfully ");
        //*************create categories table*********

        String  req3=  "CREATE TABLE "+categories+"( " +
                IdCateg +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                NameCateg +" TEXT NOT NULL"+
                " );";

        // Execute the SQL statement
        db.execSQL(req3);
        Log.i("CATEGORIES", "data base is created successfully ");
        //******create intervention tech categories table*********
        String  req4 =  "CREATE TABLE "+InterCategTech +"( " +
                InterCategTechIdTech+" INTEGER ,"+
                InterCategTechIdInterv+" INTEGER ,"+
                InterCategTechIdCateg + " INTEGER"+
                " );";

        // Execute the SQL statement
        db.execSQL(req4);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //add a User
    public boolean AddUser(String nameUser, String Password, String Profil){
        boolean res = false;
        String req = "INSERT INTO Users (nomUser, ageUser) " +
                "values ('"+nameUser+"', '"+Password+"' , '"+Profil+"') ";
        try{
            this.getWritableDatabase().execSQL(req);
            res = true;
            Log.i("stepAjout", "User successfully added");
        }
        catch(Exception e){
            Log.i("stepAjout", "Problem insertion new intervention " + e.getMessage());
        }

        return res;
    }

    // add an intervention
    public  boolean AddIntervention (String NomClient , String mobileClient , String TitreInterv , String DateInterv, String Description ){
        boolean exeInt;

        String req = "insert into INTERVENTION (NomClient, mobileClient, TitreInterv ,DateInterv , Description) " +
                "values ('" + NomClient + "' , '" + mobileClient +"' , '" +TitreInterv+ "' , '" +DateInterv+ "' , '" +Description+ "')";
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
    //add a category
    public  boolean AddCategory (String NameCateg ){
        boolean exeInt ;

        String req = "insert into INTERVENTION (NameCateg) " +
                "values ('" + NameCateg + "')";
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
    //view categories
    public ArrayList<Category> getAllCategories(){
        ArrayList<Category> l = new ArrayList<>();
        String req = "SELECT idInt FROM CATEGORIES";
        Cursor c = this.getReadableDatabase().rawQuery(req,null);

        c.moveToFirst();
        while(c.isAfterLast() != true){
            Category c1;
            c1 = new Category(c.getInt(0), c.getString(1));
            l.add(c1);
            c.moveToNext();
        }
        c.close();
        return l;
    }
    //view intervention
    public ArrayList<Intervention> getAllInterventions(){
        ArrayList<Intervention> list = new ArrayList<>();
        String req = "SELECT idInt FROM INTERVENTION";
        Cursor c = this.getReadableDatabase().rawQuery(req,null);

        c.moveToFirst();
        while(c.isAfterLast() != true){
            Intervention i1 = new Intervention(c.getInt(0), c.getString(1), c.getString(2),c.getString(3), c.getString(4), c.getString(5));
            list.add(i1);
            c.moveToNext();
        }
        c.close();
        return list;
    }
   //get Users
   public ArrayList<User> getAllUser() {

       ArrayList<User> list = new ArrayList<User>();
       String req = "SELECT * FROM USERS";

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


   //Delete User
   public boolean deleteUser (int IdUser) {
       boolean res = false;
       String req = "DELETE FROM Users where IdUser= '"  + IdUser+ "'";
       try{
           this.getWritableDatabase().execSQL(req);
           res = true;
           Log.i("stepAjout", "successfully added");
       }
       catch(Exception e){
           Log.i("stepAjout", "problem insertion new user " + e.getMessage());
       }

       return res;
   }
   //get One user
    public User getOneUser(int IdUser)
    {
        User us1 = null;
        String req = "SELECT * FROM USERS where IdUser= '"  + IdUser+ "'";
        return us1;
    }
   //Update
    public void UpdateUserName(int Id, String Username)
    {
        String req = "UPDATE USERS SET UserName = "+"'UserName' "+ "WHERE IdUser = "+ IdUser;
        this.getWritableDatabase().execSQL(req);
    }

}


