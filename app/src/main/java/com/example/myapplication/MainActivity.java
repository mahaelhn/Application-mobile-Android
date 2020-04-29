package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //******
   private Button bLogin;
   private EditText etUsername, etPassword;
   private TextView tvRegisterLink;

    //*********
    //user
    String Users = TablesData.USERES_TABLE;
    String UserName = TablesData.USERNAME;
    String Password = TablesData.USERPASSWORD;
    String IdUser = TablesData.USERID;
    String ProfilUser = TablesData.USERPROFIL;
    private final String TECHNICIEN = TablesData.USERTECH;
    private final String ADMIN = TablesData.USERADMIN;



    //**********************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvRegisterLink = (TextView) findViewById(R.id.tvRegisterLink);
        bLogin = (Button) findViewById(R.id.bLogin);


        bLogin.setOnClickListener(this);
        tvRegisterLink.setOnClickListener(this);
    }
    public void onClick (View v){
            switch (v.getId())    //gets the ID of the view notifed on Click method
            {
                case R.id.bLogin:
                    Log.i("error", "Logger1");
                    String name = etUsername.getText().toString().trim();  //trim() reduire le string (espace)
                    String password = etPassword.getText().toString().trim();
                    Log.i("error", "Logger");
                    authentication(name, password);

                    //startActivity(new Intent(this, Menu.class));
                    break;
            }
            switch (v.getId())    //gets the ID of the view notifed on Click method
            {
                case R.id.tvRegisterLink:
                    startActivity(new Intent(MainActivity.this, Register.class));
                    break;
            }
        }



public void authentication(String name, String password)
        {
          /*  SQLiteOpenHelper database = new Database_class(this);
            SQLiteDatabase db = new database.getReadableDatabase();*/
            SQLiteOpenHelper db = new MyDataBase(this);
            SQLiteDatabase dataB = db.getReadableDatabase();

        String query = "SELECT * FROM " + Users +
        " WHERE " + Password +"='"+password+
        "' AND "+UserName + "= '"+ name+"'";

        Log.i("error","err1");
        Cursor cursor = dataB.rawQuery(query, null);
        Log.i("error","err2");

        if(cursor.getCount()>=1)
        {
        cursor.moveToNext();
        int idIndex = cursor.getColumnIndex(IdUser);
        int nameIndex = cursor.getColumnIndex(UserName);
        int passwordIndex = cursor.getColumnIndex(Password);
        int profilIndex = cursor.getColumnIndex(ProfilUser);

        String currentName = cursor.getString(nameIndex);
        String currentPassword = cursor.getString(passwordIndex);
        String currentProfil = cursor.getString(profilIndex);

        int currentID = cursor.getInt(idIndex);

        if(currentProfil.equals(TECHNICIEN))
        {
        Toast.makeText(this, "Technicien: "+currentName, Toast.LENGTH_SHORT).show();
        cursor.close();
        Intent intent = new Intent(this, MenuTech.class);

        intent.putExtra(UserName, currentName);
        intent.putExtra(ProfilUser, currentProfil);
        intent.putExtra(IdUser, currentID);

            startActivity(intent);
        }
        else
        {
       Toast.makeText(this, "Admin: "+currentName, Toast.LENGTH_SHORT).show();
        cursor.close();
        Intent intent = new Intent(this, Menu.class);

        intent.putExtra(UserName, currentName);
      //  intent.putExtra(Password, currentPassword);
        intent.putExtra(ProfilUser, currentProfil);
        startActivity(intent);
        }
        }
        else
        Toast.makeText(this, "Account doesn't exist!", Toast.LENGTH_SHORT).show();
        cursor.close();

        }
}