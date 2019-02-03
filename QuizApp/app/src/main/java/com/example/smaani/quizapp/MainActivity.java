package com.example.smaani.quizapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHandler myDbHelper = new DatabaseHandler(this);
        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            myDbHelper.openDataBase();
        }catch(SQLException sqle){
            throw sqle;
        }
    }



    public void toFirstGame(View view) {
        Intent i1=new Intent(this, QuestionQuiz.class);
        //startActivityForResult(i1, 12);
        startActivity(i1);
    }
    public void toSecondGame(View view) {
        Intent i2=new Intent(this, MapsActivity.class);
        startActivity(i2);
    }
    public void ExitApplication(View view) {
        finish();
    }

    public void DisplayData(View view) {
        Intent i2=new Intent(this, DisplayActivity.class);
        startActivity(i2);
    }
}





// Gets the data repository in write mode

//        String mCSVfile = "countries_of_the_world.csv";
//        AssetManager manager = getApplicationContext().getAssets();
//        InputStream inStream = null;
//        try {
//            inStream = manager.open(mCSVfile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));
//        String line = "";
//        db.beginTransaction();
//        try {
//            while ((line = buffer.readLine()) != null) {
//                String[] colums = line.split(",");
//                if (colums.length != 4) {
//                    Log.d("CSVParser", "Skipping Bad CSV Row");
//                    continue;
//                }
//                ContentValues cv = new ContentValues(3);
//                cv.put(DbCountryContract.CountryEntry.COLUMN_NAME_COUNTRY, colums[0].trim());
//                cv.put(DbCountryContract.CountryEntry.COLUMN_NAME_REGION, colums[1].trim());
//                cv.put(DbCountryContract.CountryEntry.COLUMN_NAME_POPULATION, colums[2].trim());
//                cv.put(DbCountryContract.CountryEntry.COLUMN_NAME_AREA, colums[3].trim());
//                db.insert(DbCountryContract.CountryEntry.TABLE_NAME, null, cv);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        db.setTransactionSuccessful();
//        db.endTransaction();
