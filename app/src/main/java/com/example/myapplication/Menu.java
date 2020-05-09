package com.example.myapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity {

 private Button bCreate;  //create tech
 private Button bCreateInv;
 private Button bCreateCateg;//create intervention
 private Button bListV;    //Read intervention list
 private Button bLogOut, bDeleteAll;

    String Users = TablesData.USERES_TABLE;
    String Intervention= TablesData.INTERVENTION_TABLE;
    String categories= TablesData.CATEGORIES_TABLE;
    String InterCategTech = TablesData.INTERCATEGTECH_TABLE;

    MyDataBase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        db = new MyDataBase(this);

        bCreate= findViewById(R.id.bCreate);
        bCreateInv= findViewById(R.id.bCreateInv);
        bCreateCateg= findViewById(R.id.bCreateCateg);
        bListV= findViewById(R.id.bListV);
        bDeleteAll= findViewById(R.id.bDeleteAll);
        bLogOut= findViewById(R.id.blogOut);




        bCreate.setOnClickListener(bCreateListener);
        bCreateInv.setOnClickListener(bCreateInvListener);
        bCreateCateg.setOnClickListener(bCreateCategListener);
        bListV.setOnClickListener(bListVListener);
        bDeleteAll.setOnClickListener(bDeleteAllListener);
        bLogOut.setOnClickListener(blogOutListener);


    }
    private View.OnClickListener bCreateListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent1 = new Intent(Menu.this, NewTechnician.class);
            startActivity(intent1);

        }
    };

    private View.OnClickListener bCreateInvListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent2 = new Intent(Menu.this, NewIntervention.class);
            startActivity(intent2);
        }
    };

    private View.OnClickListener bCreateCategListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           Intent intent3 = new Intent(Menu.this, NewCategory.class);
           startActivity(intent3);
        }
    };

    private View.OnClickListener bListVListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent4 = new Intent(Menu.this, ViewIntervention.class);
            startActivity(intent4);
        }
    };


    private View.OnClickListener bDeleteAllListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
              deleteAll();
        }
    };

private View.OnClickListener blogOutListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent6 = new Intent(Menu.this, MainActivity.class);
        startActivity(intent6);
    }
};

    public void deleteAll()
    {
        SQLiteOpenHelper db = new MyDataBase(this);
        SQLiteDatabase dataB = db.getWritableDatabase();
        dataB.execSQL("delete from "+ Users);
        dataB.execSQL("delete from "+ Intervention);
        dataB.execSQL("delete from "+ categories);
        dataB.execSQL("delete from "+ InterCategTech);
    }
}
