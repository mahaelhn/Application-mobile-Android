package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity {

 private Button bCreate;  //create tech
 private Button bCreateInv;  //create intervention
 private Button bListV;    //Read intervention list
 private Button bLogOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        bCreate= findViewById(R.id.bCreate);
        bCreateInv= findViewById(R.id.bCreateInv);
        bListV= findViewById(R.id.bListV);
        bLogOut= findViewById(R.id.blogOut);




        bCreate.setOnClickListener(bCreateListener);
        bCreateInv.setOnClickListener(bCreateInvListener);
        bListV.setOnClickListener(bListVListener);
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


    private View.OnClickListener bListVListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent4 = new Intent(Menu.this, ViewIntervention.class);
            startActivity(intent4);
        }
    };

private View.OnClickListener blogOutListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent5 = new Intent(Menu.this, MainActivity.class);
        startActivity(intent5);
    }
};

}
