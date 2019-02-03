package com.example.smaani.quizapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {
    int num_question = 0,score=0, e=0;
    ArrayList<String> list;
    boolean bool=false;
    String pays ="", reponse, reponse2, ville="";
    private static final String TAG ="hhhhhhhhhhh" ;
    VerifyData db;
    Random rand = new Random();
    Random randLigne = new Random();
    String question[] = new String[]{"Où se situe : ", "Quelle est la capitale de : ", "Quelle est la superficie de : ", "Quelle est la monnaie de : "};
    int min=0, max=question.length-1, minLigne = 1, maxLigne= 39;
    int diff = max - min;
    int diffLigne = maxLigne - minLigne;
    int i, iLigne;
    TextView tvUser, TvQuestion, ETResponse, Tvscore, Tvnumquestion;
    CharSequence rep, user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        list = new ArrayList<>();
        db = new VerifyData(this);
        try {
            db.openDataBase();
        }catch(SQLException sqle){
            throw sqle;
        }
        tvUser = findViewById(R.id.tvUserName);
        Intent inte=getIntent();
        user = inte.getStringExtra("username");
        tvUser.setText(user);
        Tvscore = findViewById(R.id.TvScore);
        ETResponse = findViewById(R.id.ETResponse);
        TvQuestion  = findViewById(R.id.TVQuestion);
        Tvnumquestion = findViewById(R.id.TvQuestionNumber);
        Tvnumquestion.setText(num_question+"");
        // en premier lieu prendre une question aléatoirement du tableau des question, pour ce faire on genere un nombre entre
        // zero et la taille du tableau -1, le nombre généré correspond a l'index de la question dans le tableau
        i= rand.nextInt(diff + 1);
        i += min;
        Log.i(TAG, "onCreate: i "+i);
        // iLigne pour generer une ligne aléatoire
        iLigne = randLigne.nextInt(diffLigne + 1);
        iLigne+=minLigne;
        Log.i(TAG, "onCreate: iLigne "+iLigne);
        // il faut appeler une fonction avec comme arguments la valeur de iLigne qui va servir pour nous retourner la donnée dans la base qui est a l'index en question.
        getData(i, iLigne);
    }

    /*Fonction qui permet de récuprer la réponse saisie par l'utilisateur et de la comparer avec la bonne réponse*/
    public void validerReponse(View view) {
        rep = ETResponse.getText().toString().toLowerCase();
        Log.i(TAG, "onCreate:"+rep);
        if (rep.length()!=0) {
            if ((rep.equals(reponse.toLowerCase())) || (rep.equals(reponse2.toLowerCase()))) {
                score++;
                Tvscore.setText(score + "");
                Toast.makeText(this, "Good Job", Toast.LENGTH_SHORT).show();
                questionSuivante(view);
            } else if ((!rep.equals(reponse.toLowerCase())) || (!rep.equals(reponse2.toLowerCase()))) {
                Toast.makeText(this, "So Bad !!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getData(int l, int k) { // colonne , ligne
        reponse=""; reponse2="";
        Log.i(TAG, "getData: "+k);
        Cursor cursor = db.viewdata(k);
        Log.i(TAG, "getData: "+db.viewdata(k));
        if (cursor.getCount() == 0){
            Toast.makeText(this, "no data to show", Toast.LENGTH_SHORT ).show();
        } else {
            while (cursor.moveToNext()){
                if(l==0){
                    ville =cursor.getString(2);
                    reponse = cursor.getString(1);
                    TvQuestion.setText(question[l]+""+ville+" "+reponse);
                }else if(l==1){
                    pays =cursor.getString(1);
                    reponse = cursor.getString(2);
                    TvQuestion.setText(question[l]+""+pays+" "+reponse);
                } else if(l==2){
                    pays =cursor.getString(1);
                    reponse = cursor.getString(3);
                    TvQuestion.setText(question[l]+""+pays+" "+reponse);
                } else if(l==3){
                    pays =cursor.getString(1);
                    if(pays=="Bahamas"){
                        reponse = cursor.getString(4); reponse2="Dollar bahaméen";
                    } else if (pays=="Bahreïn"){
                        reponse = cursor.getString(4); reponse2="Dinar bahreïni";
                    } else if (pays=="Belize") {
                        reponse = cursor.getString(4); reponse2="Dollar bélizien";
                    } else {
                        reponse = cursor.getString(4);
                    }
                    TvQuestion.setText(question[l]+""+pays+" "+reponse);
                }
            }
        }
    }

    public void questionSuivante(View view) {
        bool=false;
        ETResponse.setText("");
        i= rand.nextInt(diff + 1);
        i += min;
        iLigne = randLigne.nextInt(diffLigne + 1);
        iLigne+=minLigne;
        getData(i, iLigne);
        num_question++;
        Tvnumquestion.setText(num_question+"");
    }

    public void SaveScore(View view){
        SharedPreferences sharedPrefs = getSharedPreferences("myScore", MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPrefs.edit();
        //Toast.makeText(this, sharedPrefs.getInt("score", 0),Toast.LENGTH_LONG);
        e=sharedPrefs.getInt("score", 0);
        if (score > e) {
            editor.putString("user name", tvUser.getText().toString());
            editor.putInt("score", score);
        }
        editor.commit();
        Toast.makeText(this, "data stored", Toast.LENGTH_LONG);
        Intent i1=new Intent(this, QuestionQuiz.class);
        startActivity(i1);
        finish();
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: onStop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: onRestart");
    }
}

