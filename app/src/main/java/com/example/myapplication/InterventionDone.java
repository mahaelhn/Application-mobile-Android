package com.example.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class InterventionDone extends AppCompatActivity {

    int nbInterventions=0;
    ListView lvDoneInterventions;

    private ArrayList<String> DoneInterventionsList;
    private ArrayAdapter<String> adapterDoneInterventions;
    //user******************
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
    int Id2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intervention_done);
        initializer();

        lvDoneInterventions.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                // Get the selected item text from ListView
                String selectedItem = (String) parent.getItemAtPosition(position);

                // Display the selected item text on TextView
                markInterventionUndone(selectedItem);

                Toast toast = Toast.makeText(InterventionDone.this, "Intervention Undone", Toast.LENGTH_SHORT);
                toast.show();
                initializer();


            }
        });

    }

    private void initializer()
    {
        nbInterventions=0;
        DoneInterventionsList = new ArrayList<String>();

       MyDataBase db = new MyDataBase(this);

       lvDoneInterventions = findViewById(R.id.lvTechInterventionsDone);


        DoneInterventionsList = new ArrayList<>();
        adapterDoneInterventions = new ArrayAdapter<String>(this, R.layout.intervention_item,R.id.TVInterventionName, DoneInterventionsList);
        lvDoneInterventions.setAdapter(adapterDoneInterventions);


        Bundle extras = getIntent().getExtras();
        if (extras != null)
            Id2 = extras.getInt(IdUser);

        showWork();
    }

    private void showWork()
    {
        SQLiteOpenHelper dba = new MyDataBase(this);
        SQLiteDatabase dataB1 = dba.getReadableDatabase();


        String query = "SELECT * FROM " +InterCategTech+ " WHERE "+InterCategTechIdTech+ "= "+Id2;

        Cursor cursor = dataB1.rawQuery(query, null);

        int idInterventColumnIndex = cursor.getColumnIndex(InterCategTechIdInterv);
        int idCategColumnIndex = cursor.getColumnIndex(InterCategTechIdCateg);


        while (cursor.moveToNext())
        {
            int currentInterventionId = cursor.getInt( idInterventColumnIndex );
            showDoneInterventions(currentInterventionId);
            Log.i("new","ID TECH: " + Id2 + " |INTERVID: "+currentInterventionId + "\n");

            int currentCategoryId = cursor.getInt( idCategColumnIndex );
            showCategory(currentCategoryId );
        }
        cursor.close();
    }

    private void showDoneInterventions(int intervId)
    {
        SQLiteOpenHelper dba = new MyDataBase(this);
        SQLiteDatabase dataB1 = dba.getReadableDatabase();


        String query = "SELECT * FROM " + Intervention +
                " WHERE "+IdIntev+ "= "+intervId +
                " AND " + ExecInterv+"=1";


        Cursor cursor =dataB1.rawQuery(query, null);

        int  interventionTitleColumnIndex = cursor.getColumnIndex(TitreInterv);

        if(  cursor.moveToNext())
        {

            String currentInterventionTitle = cursor.getString(interventionTitleColumnIndex );
            //  tvMyWork.append(currentInterventionTitle + "\n");
            DoneInterventionsList.add(currentInterventionTitle);
            nbInterventions++;
            Log.i("AEW","TEST_DONE" + currentInterventionTitle);
        }
        cursor.close();
        adapterDoneInterventions.notifyDataSetChanged();

    }

    private void showCategory(int categId)
    {
        SQLiteOpenHelper db2 = new MyDataBase(this);
        SQLiteDatabase dataB2 = db2.getReadableDatabase();


        String query = "SELECT * FROM " +categories +
                " WHERE "+IdCateg+ "= "+categId;


        Cursor cursor =dataB2.rawQuery(query, null);

        int categoryNameColumnIndex = cursor.getColumnIndex(NameCateg);

        while (cursor.moveToNext())
        {

            String currentCategoryName = cursor.getString( categoryNameColumnIndex );
            //  tvWork.append(currentCategoryName + "\n");
        }
        cursor.close();
    }

    private void markInterventionUndone(String intervTitle)
    {
        SQLiteOpenHelper db3 = new MyDataBase(this);
        SQLiteDatabase dataB3 = db3.getReadableDatabase();
        String query = "SELECT * FROM " +Intervention +
                " WHERE " + TitreInterv+ " ='" + intervTitle+"'";

        Cursor cursor = dataB3.rawQuery(query, null);

        int idColumnIndex = cursor.getColumnIndex(IdIntev);
        int currentInterventionId=-1;

        while (cursor.moveToNext())
        {
            currentInterventionId = cursor.getInt(idColumnIndex);
        }
        cursor.close();

        SQLiteOpenHelper db4 = new MyDataBase(this);
        SQLiteDatabase dataB4 = db4.getReadableDatabase();
        String  SQL_UPDATE_Intervention_TABLE =  "UPDATE "+Intervention + " SET "+ ExecInterv +"= 0 "+ "WHERE " + IdIntev +"="+currentInterventionId;
        //Execute
        dataB4.execSQL(SQL_UPDATE_Intervention_TABLE );

    }
}
