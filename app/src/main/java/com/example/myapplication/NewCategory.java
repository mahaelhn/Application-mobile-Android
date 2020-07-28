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

public class NewCategory extends AppCompatActivity implements View.OnClickListener {

    private EditText IdCategory, NameCategory;
    private Button bAdd8, bReturn8, bDelete8, bSave8, bUpdate8, bRead8;
    private TextView TextView8;
    //Categories
    String categories = TablesData.CATEGORIES_TABLE;
    String IdCateg = TablesData.IdCateg;
    String NameCateg = TablesData.NameCateg;

    MyDataBase db2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);

        db2 = new MyDataBase(this);

        IdCategory = (EditText) findViewById(R.id.IdCategory);
        NameCategory = (EditText) findViewById(R.id.NameCategory);
        TextView8 = (TextView) findViewById(R.id.TextView8);

        bAdd8 = (Button) findViewById(R.id.bAdd8);
        bSave8 = (Button) findViewById(R.id.bSave8);
        bUpdate8 = (Button) findViewById(R.id.bUpdate8);
        bRead8 = (Button) findViewById(R.id.bRead8);
        bReturn8 = (Button) findViewById(R.id.bReturn8);
        bDelete8 = (Button) findViewById(R.id.bDelete8);

        bAdd8.setOnClickListener(this);
        bSave8.setOnClickListener(this);
        bUpdate8.setOnClickListener(this);
        bRead8.setOnClickListener(this);
        bReturn8.setOnClickListener(this);
        bDelete8.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())    //gets the ID of the view notifed on Click method
        {
            //save*****************
            case R.id.bAdd8:

                int IdCate = Integer.parseInt(IdCategory.getText().toString());
                String nameCateg = NameCategory .getText().toString();

                boolean res = db2.AddCategory(IdCate,nameCateg);

                if(res==true) {
                    Toast t = Toast.makeText(NewCategory.this, "Category added successfully", Toast.LENGTH_LONG);
                    t.show();
                } else
                    Toast.makeText(NewCategory.this, "Problem to add a category", Toast.LENGTH_LONG).show();
                break;

            //save default categories*****************
            case R.id.bSave8:
                insertDefaultCategories();
                Toast.makeText(NewCategory.this, "INSERTED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                break;

            //Show info*****************
            case R.id.bRead8:
                SQLiteOpenHelper dba = new MyDataBase(this);
                SQLiteDatabase dataB1 = dba.getReadableDatabase();
                int idCategori1 = Integer.parseInt(IdCategory.getText().toString());

                String query = "SELECT * FROM " + categories +
                        " WHERE " + IdCateg +"='"+idCategori1+"'";
                Cursor cursor;
                cursor=dataB1.rawQuery(query,null);
                if(cursor.moveToNext())
                {
                    String p = cursor.getString(cursor.getColumnIndex("IdCateg"));
                    String p1 = cursor.getString(cursor.getColumnIndex("NameCateg "));


                    TextView8.setText(" id category is:" +p+ " name of the category is:" +p1  );

                }else {
                    TextView8.setText("No Record Found");
                }
                break;
         //Update*****************
            case R.id.bUpdate8:
                SQLiteOpenHelper db1 = new MyDataBase(this);
                SQLiteDatabase dataB = db1.getWritableDatabase();

                int idCategori2 = Integer.parseInt(IdCategory.getText().toString());
                String nameCateg2 = NameCategory.getText().toString();
                String req1 = "UPDATE Intervention SET  NameCateg ='" +nameCateg2+ "' WHERE  IdCateg = '"+ idCategori2+"'";

                dataB.execSQL(req1);
                TextView8.setText("Record UPDATED");
                IdCategory.setText("");
                NameCategory.setText("");
                break;

                //Delete*****************
            case R.id.bDelete8:
                SQLiteOpenHelper db3 = new MyDataBase(this);
                SQLiteDatabase dataB3 = db3.getWritableDatabase();

                int idCategori3 = Integer.parseInt(IdCategory.getText().toString());
                String nameCateg3 = NameCategory.getText().toString();
                String req2 = "DELETE FROM categories where idCategori3 = '"  + idCategori3 + "'";
                dataB3.execSQL(req2);
                TextView8.setText("Record DELETED");
                IdCategory.setText("");
                NameCategory.setText("");
                break;

            //Return*****************
            case R.id.bReturn8:
                Intent i5 = new Intent(NewCategory.this, Menu.class);
                startActivity(i5);
                break;
        }
    }


    private void insertDefaultCategories()
    {
        long currentId;
        // Gets the database in write mode
        // Gets the database in write mode
        SQLiteOpenHelper db8 = new MyDataBase(this);
        SQLiteDatabase dataB8 = db8.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NameCateg, "installation");
        currentId= dataB8.insert(categories, null, values);
        //add current category to our list
        TablesData.addCategory(currentId);

        values.put(NameCateg, "Maintenance corrective");
        currentId = dataB8.insert(categories, null, values);
        //add current category to our list
        TablesData.addCategory(currentId);
    }

}