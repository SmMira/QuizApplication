package com.example.smaani.quizapp;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class VerifyData extends SQLiteOpenHelper {

    private static String DB_PATH = "/data/data/com.example.smaani.quizapp/databases/";
    private static String DB_NAME = "CountriesInf.db";
    private static  String table_Name = "pays_du_monde";

    private SQLiteDatabase myDataBase;
    private final Context myContext;
    public VerifyData(Context context) {

        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    public void openDataBase() throws SQLException {
        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    public Cursor viewdata(int i){
        SQLiteDatabase db= this.getReadableDatabase();
        String query = "select * from "+table_Name +" where _id= ?";
        Cursor cursor = db.rawQuery(query, new String[] { i + " " });
        return cursor;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
