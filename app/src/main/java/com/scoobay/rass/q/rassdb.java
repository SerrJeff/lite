package com.scoobay.rass.q;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;

public class rassdb extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "rass";


    public rassdb(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table users (id integer primary key, email text,password text)"
        );

        db.execSQL(
                "create table quiz (id integer primary key, question text,answer text)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS quiz");
        onCreate(db);
    }

    public boolean insertUsers ( String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        db.insert("users", null, contentValues);
        return true;
    }


    public Cursor valUser(String email,String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query="SELECT * FROM users WHERE email = ? AND password = ?";
        String[] selectionArgs = {email,password};
        Cursor res =  db.rawQuery("SELECT * FROM users WHERE email = ? AND password = ?", selectionArgs);
        return res;
    }

    public Cursor getQuizItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = {};
        Cursor res =  db.rawQuery("SELECT * FROM quiz",selectionArgs );
        return res;
    }

    public boolean insertQuiz ( String question, String answer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("question", question);
        contentValues.put("answer", answer);
        db.insert("quiz", null, contentValues);
        return true;
    }

    public boolean updateQuiz ( String question, String answer, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("question", question);
        contentValues.put("answer", answer);
        db.update("quiz", contentValues, "id = ?", new String[]{id});
        return true;
    }


    public Cursor valQuiz(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = {email};
        Cursor res =  db.rawQuery("SELECT * FROM quiz WHERE question = ?", selectionArgs);
        return res;
    }

    public boolean delQuiz(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = {email};
        db.delete("quiz","id = ?",selectionArgs);
        return true ;
    }





}


