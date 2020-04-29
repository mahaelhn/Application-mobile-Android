package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NewIntervention extends AppCompatActivity {

   private EditText  NomClient, MobileClient, EtTitre, EtDate, EtDescription;
   private Button bAdd, bReturn3;

   MyDataBase db1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_intervention);

        db1 = new MyDataBase(this);


        NomClient = (EditText) findViewById(R.id.NomClient);
        MobileClient= (EditText) findViewById(R.id.MobileClient);
        EtTitre = (EditText) findViewById(R.id.EtTitre );
        EtDate = (EditText) findViewById(R.id.EtDate);
        EtDescription = (EditText) findViewById(R.id.EtDescription );

        bAdd = (Button) findViewById(R.id.bAdd);
        bReturn3 = (Button) findViewById(R.id.bReturn3);


        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String NameClient = NomClient.getText().toString();
                String mobile = MobileClient.getText().toString();
                String Title = EtTitre.getText().toString();
                String Date = NomClient.getText().toString();
                String Description = EtDescription.getText().toString();

                boolean res = db1.AddIntervention(NameClient, mobile, Title, Date, Description);

                if(res==true) {
                    Toast t = Toast.makeText(NewIntervention.this, "Client added successfully", Toast.LENGTH_LONG);

                } else
                    Toast.makeText(NewIntervention.this, "Problem to add a client", Toast.LENGTH_LONG);
            }
        });

        bReturn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewIntervention.this, Menu.class);
                startActivity(i);
            }
        });
    }
}
