package com.example.smaani.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class QuestionQuiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_quiz);
    }

    public void toWelcome(View view) {
        Intent i1=new Intent(this, MainActivity.class);
        startActivity(i1);
    }

    public void ToScore(View view) {
        Intent i1=new Intent(this, ScoreActivity.class);
        startActivity(i1);
    }


    public void toGame(View view) {
        Intent i1=new Intent(this, UserName.class);
        startActivity(i1);
    }


}
