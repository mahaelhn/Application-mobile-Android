package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MenuTech extends AppCompatActivity {


    private Button bDone, bNotDone, blogOut2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_tech);

        bDone= findViewById(R.id.bDone);
        bNotDone= findViewById(R.id. bNotDone);
        blogOut2= findViewById(R.id.blogOut2);

        bDone.setOnClickListener(bDoneListener);
        bNotDone.setOnClickListener(bNotDoneListener);
        blogOut2.setOnClickListener(blogOut2Listener);


    }

    private View.OnClickListener bDoneListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          //  Intent intent1 = new Intent(MenuTech.this,);
          //  startActivity(intent1);

        }
    };
    private View.OnClickListener bNotDoneListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //  Intent intent1 = new Intent(MenuTech.this,);
            //  startActivity(intent1);

        }
    };
    private View.OnClickListener blogOut2Listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i3= new Intent(MenuTech.this, MainActivity.class);
            startActivity(i3);

        }
    };
}
