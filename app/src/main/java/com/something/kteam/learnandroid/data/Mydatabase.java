package com.something.kteam.learnandroid.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.something.kteam.learnandroid.BuildConfig;
import com.something.kteam.learnandroid.Video;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by Nguyen Hung Son on 11/12/2017.
 */

public class Mydatabase extends SQLiteOpenHelper{
    public static final String DB_NAME = "ptit.sqlite";
    public static final String DBLOCATION = "/data/data/com.something.kteam.learnandroid/databases/";
    public static final int DB_VERSION = 1;
    private SQLiteDatabase myDatabase;
    private Context mContext;
//    private static String DB_PATH ="/data/data/"+ BuildConfig.APPLICATION_ID+"/databases/";
    public static final String ACCOUNT_TABLE = "Video";
//    public static final int ID = 0;
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_LINK = "link";
    public Mydatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }






    public synchronized void opendatabase() throws SQLException {
        //Open the database
       String dbPath = mContext.getDatabasePath(DB_NAME).getPath();
       if(myDatabase != null){
           return;
       }
       myDatabase = SQLiteDatabase.openDatabase(dbPath,null,SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void closeDB(){
        if(myDatabase != null){
            myDatabase.close();
        }
    }

    public ArrayList<Video> search(String title){
        ArrayList<Video> list = new ArrayList<>();
        opendatabase();
        Cursor c = myDatabase.rawQuery("SELECT * FROM "+ ACCOUNT_TABLE + " WHERE " + COLUMN_TITLE + " LIKE ?",new String[]{"%"+title+"%"});
        if (c.moveToFirst()) {
            do {
                Video note = new Video();
                note.setTitle(c.getString(1));
                note.setLink(c.getString(2));
                note.setTime(c.getString(3));
                list.add(note);
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }
    public ArrayList<Video> getAll(){
        ArrayList<Video> list = new ArrayList<>();
        opendatabase();
        String sql = "SELECT * FROM " + ACCOUNT_TABLE;
        Cursor c = myDatabase.rawQuery(sql, null);
        if (c.moveToFirst()) {
            do {
                Video note = new Video();
                note.setTitle(c.getString(1));
                note.setLink(c.getString(2));
                note.setTime(c.getString(3));
                list.add(note);
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }
}
