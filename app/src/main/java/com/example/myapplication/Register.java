package com.example.myapplication;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity implements View.OnClickListener {

    //user
    String Users = TablesData.USERES_TABLE;
    String NameUser= TablesData.USERNAME;
    String Password = TablesData.USERPASSWORD;
    String IdUser = TablesData.USERID;
    String ProfilUser = TablesData.USERPROFIL;
    private final String TECHNICIEN= TablesData.USERTECH;
    private final String ADMIN= TablesData.USERADMIN;

    Button bRegister;
    EditText EtUsername, EtPassword, EtProfil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EtProfil = (EditText) findViewById(R.id.EtProfil1);

        EtUsername = (EditText) findViewById(R.id.EtUsername);
        EtPassword = (EditText) findViewById(R.id.EtPassword);

        bRegister = (Button) findViewById(R.id.bRegister);

        bRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId())    //gets the ID of the view notifed on Click method
        { case R.id.bRegister:
            Log.i("error", "err1");
            AddUsers();
            Log.i("error", "err2");
            break;
        }
    }

    private void AddUsers()
    {
        SQLiteOpenHelper db = new MyDataBase(this);
        SQLiteDatabase dataBase = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NameUser, "maha");
        values.put(Password, "maha");
        values.put(ProfilUser,TECHNICIEN );
        Log.i("error", "err3");
        long currentId = dataBase.insert(Users, null, values);
        TablesData.usersList.add(currentId);

        values.put(NameUser, "admin");
        values.put(Password, "admin");
        values.put(ProfilUser, ADMIN );
        Log.i("error", "err4");
        currentId = dataBase.insert(Users, null, values);
        Log.i("error", "err5");
        TablesData.usersList.add(currentId);

    }
}
