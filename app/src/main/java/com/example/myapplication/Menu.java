package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity {

 private Button bCreate;  //create tech
 private Button bCreateInv;  //create intervention
 private Button bListeA;    //Read Accounts
 private Button bListV;    //Read intervention list
 private Button bDelete;   //delete an account

private TextView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        bCreate= findViewById(R.id.bCreate);
        bCreateInv= findViewById(R.id.bCreateInv);
        bListeA= findViewById(R.id.bListeA);
        bListV= findViewById(R.id.bListV);
        bDelete= findViewById(R.id.bDelete);


        menu = findViewById(R.id.menu);

        bCreate.setOnClickListener(bCreateListener);
        bCreateInv.setOnClickListener(bCreateInvListener);
        bListeA.setOnClickListener(bListeAListener);
        bListV.setOnClickListener(bListVListener);
        bDelete.setOnClickListener(bDeleteListener);


    }
    private View.OnClickListener bCreateListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private View.OnClickListener bCreateInvListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private View.OnClickListener bListeAListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };
    private View.OnClickListener bListVListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };
    private View.OnClickListener bDeleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };


}
