package com.example.myapplication;

import android.content.ContentValues;
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

public class NewIntervention extends AppCompatActivity implements View.OnClickListener{

   private EditText  NomClient1, MobileClient1, EtTitre, EtDate, EtDescription,EtExecInterv;
   private Button bAdd, bReturn3, bDelete1, bSave,bUpdate1, bRead4;
   private TextView TextView7;

   //****************
//Intervention
    String Intervention= TablesData.INTERVENTION_TABLE;
    String IdIntev = TablesData.IdInterv;
    String NomClient= TablesData.NomClient;
    String MobileClient = TablesData.MobileClient;
    String TitreInterv= TablesData.TitreInterv;
    String DateInterv = TablesData.DateInterv;
    String Description = TablesData.Description;
    String ExecInterv = TablesData.ExecInterv;

    MyDataBase db1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_intervention);

     db1 = new MyDataBase(this);
      //  SQLiteOpenHelper db1 = new MyDataBase(this);

        NomClient1 = (EditText) findViewById(R.id.NomClient);
        MobileClient1 = (EditText) findViewById(R.id.MobileClient);
        EtTitre = (EditText) findViewById(R.id.EtTitre);
        EtDate = (EditText) findViewById(R.id.EtDate);
        EtDescription = (EditText) findViewById(R.id.EtDescription);
        EtExecInterv= (EditText) findViewById(R.id.EtExecInterv);
        TextView7 = (TextView) findViewById(R.id.TextView7);

        bAdd = (Button) findViewById(R.id.bAdd);
        bSave = (Button) findViewById(R.id.bSave);
        bUpdate1= (Button) findViewById(R.id.bUpdate1);
        bRead4 = (Button) findViewById(R.id.bRead4);
        bReturn3 = (Button) findViewById(R.id.bReturn3);
        bDelete1= (Button) findViewById(R.id.bDelete1);

        bAdd.setOnClickListener(this);
        bSave.setOnClickListener(this);
        bUpdate1.setOnClickListener(this);
        bRead4.setOnClickListener(this);
        bReturn3.setOnClickListener(this);
        bDelete1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())    //gets the ID of the view notifed on Click method
        {
            //save*****************
            case R.id.bAdd:
                String NameClient = NomClient1.getText().toString();
                int mobile = Integer.parseInt(MobileClient1.getText().toString());
                String Title = EtTitre.getText().toString();
                String Date = EtDate.getText().toString();
                String Description = EtDescription.getText().toString();
                int ExecInterv1 = Integer.parseInt(EtExecInterv.getText().toString());
                boolean res = db1.AddIntervention(NameClient, mobile, Title, Date, Description, ExecInterv1 );

                if(res==true) {
                    Toast t = Toast.makeText(NewIntervention.this, "Client added successfully", Toast.LENGTH_LONG);
                         t.show();
                } else
                    Toast.makeText(NewIntervention.this, "Problem to add a client", Toast.LENGTH_LONG).show();
            break;

         //save default info **********
            case R.id.bSave:
                insertDefaultIntervention();
                Toast.makeText(NewIntervention.this, "INSERTED SUCCESSFULLY", Toast.LENGTH_LONG).show();
            break;

            //Show info************
            case R.id.bRead4:
                SQLiteOpenHelper dba = new MyDataBase(this);
                SQLiteDatabase dataB1 = dba.getReadableDatabase();
                String NameClient1 = NomClient1.getText().toString();
                int mobile1 = Integer.parseInt(MobileClient1.getText().toString());
                String Title1 = EtTitre.getText().toString();
                String Date1 = EtDate.getText().toString();
                String Description1 = EtDescription.getText().toString();
                int ExecInterv2 = Integer.parseInt(EtExecInterv.getText().toString());
                String query = "SELECT * FROM " + Intervention +
                        " WHERE " + MobileClient +"='"+NameClient1+"'";
                Cursor cursor;
                cursor=dataB1.rawQuery(query,null);
                if(cursor.moveToNext())
                {
                    String p = cursor.getString(cursor.getColumnIndex("MobileClient"));
                    String p1 = cursor.getString(cursor.getColumnIndex("TitreInterv"));
                    String p2 = cursor.getString(cursor.getColumnIndex("DateInterv"));
                    String p3 = cursor.getString(cursor.getColumnIndex("Description"));
                    String p4 = cursor.getString(cursor.getColumnIndex("ExecInterv"));

                TextView7.setText(" mobileClient is:" +p+ " TitreInterv is:" +p1 + " DateInterv is:" +p2 + " Description is:" +p3
                            + " ExecInterv is:" +p4 );

                }else {
                    TextView7.setText("No Record Found");
                }
                break;

         //update***********
            case R.id.bUpdate1:
                SQLiteOpenHelper db1 = new MyDataBase(this);
                SQLiteDatabase dataB = db1.getWritableDatabase();

                String nameClient = NomClient1.getText().toString();
                String mobilClient =MobileClient1.getText().toString();
                String req1 = "UPDATE Intervention SET  MobileClient ='" +mobilClient+ "' WHERE nameClient = '"+ nameClient+"'";

                dataB.execSQL(req1);
                TextView7.setText("Record UPDATED");
                NomClient1.setText("");
                MobileClient1.setText("");
                EtTitre.setText("");
                EtDate.setText("");
                EtDescription.setText("");
                EtExecInterv.setText("");
             break;



         // delete intervention************
            case R.id.bDelete1:
                SQLiteOpenHelper db3 = new MyDataBase(this);
                SQLiteDatabase dataB3 = db3.getWritableDatabase();

                String nameClient3 = NomClient1.getText().toString();
                String mobilClient3 =MobileClient1.getText().toString();
                String req2 = "DELETE FROM Intervention where NomClient= '"  + nameClient3+ "'";
                dataB3.execSQL(req2);
                TextView7.setText("Record DELETED");
                NomClient1.setText("");
                MobileClient1.setText("");


             break;

            case R.id.bReturn3:
                Intent i = new Intent(NewIntervention.this, Menu.class);
                startActivity(i);
             break;
        }

    }
    //*********insert Default Intervention ****************//
     private void insertDefaultIntervention()
    {
        long currentId;
        // Gets the database in write mode
        SQLiteOpenHelper db1 = new MyDataBase(this);
        SQLiteDatabase dataB = db1.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NomClient, "Nouha");
        values.put(MobileClient, 0725634543);
        values.put(TitreInterv,"Inetervention1" );
        values.put(DateInterv ,"10/5/2020" );
        values.put(Description,"Intervention 1 description here" );
        values.put(ExecInterv,0 );

        currentId=dataB.insert(Intervention, null, values);


        //add this intervention to list
        TablesData.addIntervention(currentId);

        values.put(NomClient, "Nassim");
        values.put(MobileClient, 0725634543);
        values.put(TitreInterv,"Intervention2" );
        values.put(DateInterv,"10/5/2020" );
        values.put(Description,"Intervention 2 description here" );
        values.put(ExecInterv, 0 );

        currentId= dataB.insert(Intervention, null, values);

        //add this intervention to our list
        TablesData.addIntervention(currentId);

    }

  /*  private void update(String name, int mobile, String titre, String date, String description, int ExecInter)
  /*  {
       /* long currentId1;
        SQLiteOpenHelper db1 = new MyDataBase(this);
        SQLiteDatabase dataB = db1.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues .put(NomClient, name);
        contentValues .put(MobileClient, mobile);
        contentValues .put(TitreInterv,titre);
        contentValues .put(DateInterv ,date );
        contentValues .put(Description,description );
        contentValues .put(ExecInterv, ExecInter);


        currentId1= dataB.update(Intervention, contentValues, new String [] {id} );
        TablesData.addIntervention(currentId1);*/

}
