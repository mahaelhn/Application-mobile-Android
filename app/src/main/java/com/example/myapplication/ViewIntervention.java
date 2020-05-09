package com.example.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewIntervention extends AppCompatActivity implements View.OnClickListener {


    //*******
    private ListView lvTechnicians, lvInterventions, lvCategories;
    private TextView TVTechName, TVInterventionName, TVCategoryName;
    private TextView tvTech, tvInter, tvCateg;
    private Button buttonLink, buttonReturn;

    private ArrayList<String> TechList;
    private ArrayAdapter<String> adapterTech;

    private ArrayList<String> IntervList;
    private ArrayAdapter<String> adapterInterv;

    private ArrayList<String> CategList;
    private ArrayAdapter<String> adapterCateg;

    private MyDataBase database;

    //*************user***********************
    private final String TECHNICIEN = TablesData.USERTECH;
    String Users = TablesData.USERES_TABLE;
    String UserName = TablesData.USERNAME;
    String Password = TablesData.PASSWORD;
    String IdUser = TablesData.USERID;
    String ProfilUser = TablesData.PROFIL;
    //****************Intervention********************
    String Intervention = TablesData.INTERVENTION_TABLE;
    String IdIntev = TablesData.IdInterv;
    String NomClient = TablesData.NomClient;
    String MobileClient = TablesData.MobileClient;
    String TitreInterv = TablesData.TitreInterv;
    String DateInterv = TablesData.DateInterv;
    String Description = TablesData.Description;
    String ExecInterv = TablesData.ExecInterv;
    //**************Categories*****************
    String categories = TablesData.CATEGORIES_TABLE;
    String IdCateg = TablesData.IdCateg;
    String NameCateg = TablesData.NameCateg;
    //***************interventionCategoriesTechnician******************
    String InterCategTech = TablesData.INTERCATEGTECH_TABLE;
    String InterCategTechIdTech = TablesData.INTERVTECHCATEG_TechId;
    String InterCategTechIdInterv = TablesData.INTERVTECHCATEG_INTERVID;
    String InterCategTechIdCateg = TablesData.INTERVTECHCATEG_CategId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_intervention);

        database = new MyDataBase(this);

        for (int i = 0; i < TablesData.categoryListe.size(); i++)
            Log.i("list", "USERS size: " + TablesData.usersList.size() + "\n");

        lvTechnicians = findViewById(R.id.lvTechnicians);
        lvInterventions = findViewById(R.id.lvInterventions);
        lvCategories = findViewById(R.id.lvCategories);

        TVTechName = (TextView) findViewById(R.id.TVTechName);
        TVInterventionName = (TextView) findViewById(R.id.TVInterventionName);
        TVCategoryName = (TextView) findViewById(R.id.TVCategoryName);

        TechList = new ArrayList<>();
        adapterTech = new ArrayAdapter<String>(this, R.layout.technician_item, R.id.TVInterventionName, TechList);
        lvTechnicians.setAdapter(adapterTech);

        IntervList = new ArrayList<>();
        adapterInterv = new ArrayAdapter<String>(this, R.layout.intervention_item, R.id.TVInterventionName, IntervList);
        lvInterventions.setAdapter(adapterInterv);

        CategList = new ArrayList<>();
        adapterCateg = new ArrayAdapter<String>(this, R.layout.category_item, R.id.TVCategoryName, CategList);
        lvCategories.setAdapter(adapterCateg);

        TechName();
        InterventionName();
        CategoryName();

        tvTech = (TextView) findViewById(R.id.tvTech);
        tvInter = (TextView) findViewById(R.id.tvInter);
        tvCateg = (TextView) findViewById(R.id.tvCateg);

        buttonLink = (Button) findViewById(R.id.buttonLink);
        buttonReturn = (Button) findViewById(R.id.buttonReturn);

        buttonLink.setOnClickListener(this);
        buttonReturn.setOnClickListener(this);

        lvTechnicians.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                String selectedItem = (String) parent.getItemAtPosition(position);
                // Display the selected item text on TextView
                tvTech.setText("" + selectedItem);
            }
        });

        lvInterventions.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                tvInter.setText("" + selectedItem);
            }
        });

        lvCategories.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                tvCateg.setText("" + selectedItem);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())    //gets the ID of the view notifed on Click method
        {
            //*****************
            case R.id.buttonLink:
                if(!tvTech.getText().toString().equals("none") && !tvInter.getText().toString().equals("none") && !tvCateg.getText().toString().equals("none"))
                {
                    Toast t =  Toast.makeText(ViewIntervention.this, "ADDED Successfully", Toast.LENGTH_SHORT);
                    t.show();
                    addTableLinkItem();
                }
                else
                {
                    Toast t =  Toast.makeText(ViewIntervention.this, "A table not yet selected!", Toast.LENGTH_SHORT);
                    t.show();
                }
                break;

            case R.id.buttonReturn:
                Intent i = new Intent(ViewIntervention.this, Menu.class);
                startActivity(i);
                break;
        }
    }
    private void TechName() {
        SQLiteOpenHelper db1 = new MyDataBase(this);
        SQLiteDatabase dataB2 = db1.getReadableDatabase();
        String query = "SELECT * FROM " + Users +
                " WHERE " + ProfilUser + "='" + TECHNICIEN + "'";

        Cursor cursor = dataB2.rawQuery(query, null);
        int idColumnIndex = cursor.getColumnIndex(IdUser);
        int nameColumnIndex = cursor.getColumnIndex(UserName);

        while (cursor.moveToNext()) {
            String currentName = cursor.getString(nameColumnIndex);
            int currentID = cursor.getInt(idColumnIndex);
            addTechnicianItem(currentName);
        }
        cursor.close();
    }

    private void addTechnicianItem(String name) {
        TechList.add(name);
        adapterTech.notifyDataSetChanged();
    }

    private void InterventionName() {
        SQLiteOpenHelper db = new MyDataBase(this);
        SQLiteDatabase dataB1 = db.getReadableDatabase();

        String[] table = {IdIntev, TitreInterv};

        Cursor cursor = dataB1.query(Intervention, table, null, null, null, null, null);

        int idColumnIndex = cursor.getColumnIndex(IdIntev);
        int nameColumnIndex = cursor.getColumnIndex(TitreInterv);

        while (cursor.moveToNext()) {
            String currentIntervTitle = cursor.getString(nameColumnIndex);
            addInterventionItem(currentIntervTitle);
        }
        cursor.close();

    }

    private void addInterventionItem(String name) {
        IntervList.add(name);
        adapterInterv.notifyDataSetChanged();
    }

    private void CategoryName() {
        SQLiteOpenHelper db3 = new MyDataBase(this);
        SQLiteDatabase dataB3 = db3.getReadableDatabase();

        String[] table = {IdCateg, NameCateg};
        Cursor cursor = dataB3.query(categories, table, null, null, null, null, null);

        int idColumnIndex = cursor.getColumnIndex(IdCateg);
        int nameColumnIndex = cursor.getColumnIndex(NameCateg);
        while (cursor.moveToNext()) {
            String currentCategoryName = cursor.getString(nameColumnIndex);
            addCategoryItem(currentCategoryName);
        }
        cursor.close();
    }

    private void addCategoryItem(String name) {
        CategList.add(name);
        adapterCateg.notifyDataSetChanged();
    }

    private void addTableLinkItem()
    {
        int currentTechnicienId=-1;
        int currentInterventionId=-1;
        int currentCategoryId=-1;

        SQLiteOpenHelper db4 = new MyDataBase(this);
        SQLiteDatabase dataB4 = db4.getReadableDatabase();
        //**********************
        String query = "SELECT * FROM " +Users +
                " WHERE " + ProfilUser+ "='" + TECHNICIEN+"' "+
                " AND "+UserName+ "= '"+tvTech.getText().toString()+"'" ;

        Cursor cursor = dataB4.rawQuery(query, null);
        int idColumnIndex = cursor.getColumnIndex(IdUser);

        while (cursor.moveToNext())
        { currentTechnicienId = cursor.getInt(idColumnIndex); }
        cursor.close();

        //**********************
        query = "SELECT * FROM " +Intervention +
                " WHERE " + TitreInterv+ "='" + tvInter.getText().toString()+"' ";

        cursor = dataB4.rawQuery(query, null);
        idColumnIndex = cursor.getColumnIndex(IdIntev);

        while (cursor.moveToNext())
        { currentInterventionId = cursor.getInt(idColumnIndex); }
        cursor.close();

        //*************************
        query = "SELECT * FROM " +categories+
                " WHERE " + NameCateg+ "='" + tvCateg.getText().toString()+"' ";

        cursor = dataB4.rawQuery(query, null);
        idColumnIndex = cursor.getColumnIndex(IdCateg);

        while (cursor.moveToNext())
        { currentCategoryId = cursor.getInt(idColumnIndex); }
        cursor.close();

        // ********************************
        SQLiteOpenHelper db5 = new MyDataBase(this);
        SQLiteDatabase dataB5= db5.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(InterCategTechIdTech, currentTechnicienId);
        contentvalues.put(InterCategTechIdInterv , currentInterventionId);
        contentvalues.put(InterCategTechIdCateg,currentCategoryId );

        Log.i("New","INSERTED: Tech Id: " + currentTechnicienId + " IntervId: "+currentInterventionId+" Categ: "+currentCategoryId);
        dataB5.insert(InterCategTech , null, contentvalues);
    }
}