package com.example.smaani.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UserName extends AppCompatActivity {

    EditText ETusername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_name);
        ETusername = findViewById(R.id.ETUserName);
    }

    public void jouer(View view) {
        Intent i1=new Intent(this, QuizActivity.class);
        if (ETusername.getText().toString().length()!=0) {
            i1.putExtra("username", ETusername.getText().toString());
            startActivity(i1);
        } else {
            Toast.makeText(this, "Pleas, enter a user name", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
