package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class NewTechnician extends AppCompatActivity {


    private EditText EUsername1, EPassword1, EProfil2;
    private Button badd1, breturn;

    MyDataBase db2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_technician);

        db2 = new MyDataBase(this);

        EUsername1 = (EditText) findViewById(R.id.EUsername1);
        EPassword1= (EditText) findViewById(R.id.EPassword1);
        EProfil2= (EditText) findViewById(R.id.EProfil2);

        badd1 = (Button) findViewById(R.id.badd1);
        breturn = (Button) findViewById(R.id.breturn);


        badd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UserName = EUsername1.getText().toString();
                String Password = EPassword1.getText().toString();
                String Profil =   EProfil2.getText().toString();

                boolean res = db2.AddUser(UserName, Password, Profil);

                if(res==true) {
                    Toast t = Toast.makeText(NewTechnician.this, "Client added successfully", Toast.LENGTH_LONG);

                } else
                    Toast.makeText(NewTechnician.this, "Problem to add a client", Toast.LENGTH_LONG);
            }
        });

        breturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewTechnician.this, Menu.class);
                startActivity(intent);
            }
        });
    }
}

