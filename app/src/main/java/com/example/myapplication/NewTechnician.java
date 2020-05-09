package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class NewTechnician extends AppCompatActivity implements View.OnClickListener {

    private EditText EUsername1, EPassword1, EProfil2;
    private Button badd1, breturn, bdelete, bupdate, bRead3;
    private TextView TextView6;

    String Users = TablesData.USERES_TABLE;
    String UserName = TablesData.USERNAME;
    String Password = TablesData.PASSWORD;
    String ProfilUser = TablesData.PROFIL;
    MyDataBase db2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_technician);

        db2 = new MyDataBase(this);

        EUsername1 = (EditText) findViewById(R.id.EUsername1);
        EPassword1 = (EditText) findViewById(R.id.EPassword1);
        EProfil2 = (EditText) findViewById(R.id.EProfil2);
        TextView6 = (TextView) findViewById(R.id.TextView6);

        badd1 = (Button) findViewById(R.id.badd1);
        breturn = (Button) findViewById(R.id.breturn);
        bdelete = (Button) findViewById(R.id.bdelete);
        bupdate = (Button) findViewById(R.id.bupdate);
        bRead3 = (Button) findViewById(R.id.bRead3);

        badd1.setOnClickListener(this);
        breturn.setOnClickListener(this);
        bdelete.setOnClickListener(this);
        bupdate.setOnClickListener(this);
        bRead3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())    //gets the ID of the view notifed on Click method
        {
            //save*************
            case R.id.badd1:
                String UserName1 = EUsername1.getText().toString();
                String Password1 = EPassword1.getText().toString();
                String Profil = EProfil2.getText().toString();

                boolean res = db2.AddUser(UserName1, Password1, Profil);
                if (res == true) {
                    Toast t = Toast.makeText(NewTechnician.this, "tech added successfully", Toast.LENGTH_LONG);
                    t.show();
                } else
                    Toast.makeText(NewTechnician.this, "Problem to add a tech", Toast.LENGTH_LONG).show();

            break;

          //Read*************
             case R.id.bRead3:
                SQLiteOpenHelper db = new MyDataBase(this);
                SQLiteDatabase dataB = db.getReadableDatabase();
                String name = EUsername1.getText().toString();
                String pass= EPassword1.getText().toString();
                String profil = EProfil2.getText().toString();
                String query = "SELECT * FROM " + Users +
                        " WHERE " + UserName +"='"+name+"'";
                Cursor cursor;
                cursor=dataB.rawQuery(query,null);
                if(cursor.moveToNext())
                {
                    String p = cursor.getString(cursor.getColumnIndex("Password"));
                    String p2 = cursor.getString(cursor.getColumnIndex("ProfilUser"));
                    TextView6.setText(" password is:" +p+ " profile is:" +p2);

                }else {
                    TextView6.setText("No Record Found");
                }
             break;

           //delete**************
            case R.id.bdelete:
                db2.getWritableDatabase();
                String NameUser = EUsername1.getText().toString();
                boolean res1 = db2.deleteUser(NameUser);
                if (res1 == true) {
                    Toast t = Toast.makeText(NewTechnician.this, "tech deleted successfully", Toast.LENGTH_LONG);
                    t.show();
                } else
                    Toast.makeText(NewTechnician.this, "Problem to delete a tech", Toast.LENGTH_LONG).show();
             break;

           //update*********
            case R.id.bupdate:
                 db2.getWritableDatabase();
                String Username = EUsername1.getText().toString();
                String password = EPassword1.getText().toString();
                db2.Update(Username, password);
                Toast t = Toast.makeText(NewTechnician.this, "Updated successfully", Toast.LENGTH_LONG);
                t.show();
            break;
             //************
            case R.id.breturn:
                Intent intent = new Intent(NewTechnician.this, Menu.class);
                startActivity(intent);
                break;
        }
    }


}


