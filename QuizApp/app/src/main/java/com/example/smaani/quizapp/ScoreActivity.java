package com.example.smaani.quizapp;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ScoreActivity extends AppCompatActivity {

    ArrayList<String> listItems;
    ArrayAdapter adapter;
    ListView userlist;
    int score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        listItems = new ArrayList<>();
        userlist = findViewById(R.id.LvScore);
        SharedPreferences sharedPreferences=getSharedPreferences("myScore", MODE_PRIVATE);
        String user= sharedPreferences.getString("user name","N/A");
        score = sharedPreferences.getInt("score", 0);
        listItems.add(user.toString()+"                         "+score);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);
        userlist.setAdapter(adapter);
    }
}
