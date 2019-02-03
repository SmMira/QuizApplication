package com.example.smaani.quizapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {
    ArrayList<String> listItems;
    ArrayAdapter adapter;
    ListView userlist;
    DatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        db = new DatabaseHandler(this);
        listItems = new ArrayList<>();
        userlist = findViewById(R.id.userlist);

        viewData();
    }

    private void viewData() {
        Cursor cursor = db.viewdata();
        if (cursor.getCount() == 0){
            Toast.makeText(this, "no data to show", Toast.LENGTH_SHORT ).show();
        } else {
            while ( cursor.moveToNext()){
                listItems.add(cursor.getString(1));

            }
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);
            userlist.setAdapter(adapter);

        }
    }

}
