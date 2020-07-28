package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class InterventionNotDone extends AppCompatActivity {
    //private TextView tvWork;

    int nbInterventions=0;
    ListView lvUndoneInterventions;

    private ArrayList<String> UndoneInterventionsList;
    private ArrayAdapter<String> adapterUndoneInterventions;
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

    private MyDataBase db;
    int Id1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intervention_not_done);
     Show();

     lvUndoneInterventions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

             String selectedItem = (String) parent.getItemAtPosition(position);
             markInterventionDone(selectedItem);

             String number= GetClientNumber(selectedItem); //Get the number of the client
             Log.i("new","number: "+number);
             CallPhoneNumber(number); //call client

             Toast toast = Toast.makeText(InterventionNotDone.this, "Intervention Done", Toast.LENGTH_SHORT);
             toast.show();
             Show();
         }
     });
    }




    private void Show()
    {
        UndoneInterventionsList = new ArrayList<String>();
        nbInterventions=0;
        MyDataBase db = new MyDataBase(this);
        lvUndoneInterventions = findViewById(R.id.lvTechInterventionsNotDone);


        UndoneInterventionsList = new ArrayList<>();
        adapterUndoneInterventions = new ArrayAdapter<String>(this, R.layout.intervention_item,R.id.TVInterventionName, UndoneInterventionsList);
        lvUndoneInterventions.setAdapter(adapterUndoneInterventions);


        Bundle extras = getIntent().getExtras();
        if (extras != null)
            Id1 = extras.getInt(IdUser);

        showWork();
    }

    private void showWork()
    {
        SQLiteOpenHelper dba = new MyDataBase(this);
        SQLiteDatabase dataB1 = dba.getReadableDatabase();
        String query = "SELECT * FROM " +InterCategTech + " WHERE "+InterCategTechIdTech + "= "+Id1;

        Cursor cursor = dataB1.rawQuery(query, null);
        int idInterventColumnIndex = cursor.getColumnIndex(InterCategTechIdInterv );
        int idCategColumnIndex = cursor.getColumnIndex(InterCategTechIdCateg);
        while (cursor.moveToNext())
        {
            int currentInterventionId = cursor.getInt( idInterventColumnIndex );

            showUndoneIntervention(currentInterventionId);
            Log.i("AEW","INSIDE: |ID TECH: " + Id1 + " |INTERVID: "+currentInterventionId + "\n");

            int currentCategoryId = cursor.getInt( idCategColumnIndex );
            showCategory(currentCategoryId );
        }
        cursor.close();
    }
    private void showUndoneIntervention(int intervId)
    {   SQLiteOpenHelper db = new MyDataBase(this);
        SQLiteDatabase dataB = db.getReadableDatabase();


        String query = "SELECT * FROM " +Intervention +
                " WHERE "+IdIntev+ "= "+intervId +
                " AND " + ExecInterv+"=0";
        Cursor cursor = dataB.rawQuery(query, null);

        int  interventionTitleColumnIndex = cursor.getColumnIndex(TitreInterv);

        if(  cursor.moveToNext())
        {
            String currentInterventionTitle = cursor.getString(interventionTitleColumnIndex );
            UndoneInterventionsList.add(currentInterventionTitle);
            nbInterventions++;
            Log.i("new","TEST_UNDONE" + currentInterventionTitle);
        }
        cursor.close();
        adapterUndoneInterventions.notifyDataSetChanged();
    }
    private void showCategory(int categId)
    {
        SQLiteOpenHelper db2 = new MyDataBase(this);
        SQLiteDatabase dataB2 = db2.getReadableDatabase();
        String query = "SELECT * FROM " +categories + " WHERE "+IdCateg+ "= "+categId;


        Cursor cursor =dataB2.rawQuery(query, null);
        int categoryNameColumnIndex = cursor.getColumnIndex(NameCateg);
        while (cursor.moveToNext())
        { String currentCategoryName = cursor.getString( categoryNameColumnIndex );
            //  tvWork.append(currentCategoryName + "\n");
        }
        cursor.close();
    }

    private void markInterventionDone(String intervTitle)
    {
        SQLiteOpenHelper db3 = new MyDataBase(this);
        SQLiteDatabase dataB3 = db3.getReadableDatabase();
        String query = "SELECT * FROM " +Intervention + " WHERE " + TitreInterv+ " ='" + intervTitle+"'";

        Cursor cursor = dataB3.rawQuery(query, null);int idColumnIndex = cursor.getColumnIndex(IdIntev);
        int currentInterventionId=-1;

        while (cursor.moveToNext())
        {
            currentInterventionId = cursor.getInt(idColumnIndex);
        }
        cursor.close();
//***************
        SQLiteOpenHelper db4 = new MyDataBase(this);
        SQLiteDatabase dataB4 = db3.getWritableDatabase();
        String  SQL_UPDATE_Intervention_TABLE =  "UPDATE "+Intervention + " SET "+ ExecInterv +"= 1 "+ "WHERE " + IdIntev +"="+currentInterventionId;
        Log.i("JERICHO","Done id:"+currentInterventionId);
        // Execute the SQL statement
        dataB4.execSQL(SQL_UPDATE_Intervention_TABLE );
        Log.i("newtech","SAVED");
        Log.i("new", " TechIntervId: "+currentInterventionId);

    }

    public String GetClientNumber(String selectedItem)
    {
        SQLiteOpenHelper db5 = new MyDataBase(this);
        SQLiteDatabase dataB5 = db5.getReadableDatabase();


        String query = "SELECT * FROM " +Intervention + " WHERE " + IdIntev+"="+ Id1+";";

        Log.i("new","id: "+Id1);
        Cursor cursor = dataB5.rawQuery(query, null);

        if(cursor.getCount()>=1)
        {
            cursor.moveToNext();
            int numberColumnIndex = cursor.getColumnIndex(MobileClient);
            Log.i("new"," id: "+Id1);

            String num=cursor.getString(numberColumnIndex);
            Log.i("new"," num: "+num);

            return  num;


        }
        return "";
    }

    public void CallPhoneNumber(String phoneNumber)
    {
        Log.i("new","num1: "+phoneNumber);
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(intent);
        }
    }
}
