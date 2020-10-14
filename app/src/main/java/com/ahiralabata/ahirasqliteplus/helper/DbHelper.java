package com.ahiralabata.ahirasqliteplus.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DbHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "ahira.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_SQLite = "sqlite";
    private static final String KEY_ID = "id";
    private static final String KEY_NAMA = "nama";
    private static final String KEY_ALAMAT = "alamat";

    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE " + TABLE_SQLite + "(" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_NAMA + " TEXT NOT NULL, " +
                KEY_ALAMAT + " TEXT NOT NULL)";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SQLite);
        onCreate(db);
    }

    public ArrayList<HashMap<String, String>> getAll(){
        ArrayList<HashMap<String, String>> wordlist;
        wordlist = new ArrayList<HashMap<String, String>>();
        String query = "SELECT * FROM "+ TABLE_SQLite;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        if(c.moveToFirst()){
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(KEY_ID, c.getString(0));
                map.put(KEY_NAMA, c.getString(1));
                map.put(KEY_ALAMAT, c.getString(2));
                wordlist.add(map);
            } while (c.moveToNext());
        }
        Log.e("select sqlite", "" + wordlist);
        db.close();
        return wordlist;
    }

    public void insert(String nama, String alamat){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "INSERT INTO " + TABLE_SQLite + " (nama, alamat) " +
                "VALUES ('" + nama + "', '" + alamat + "')";
        Log.e("insert query", "" + query);
        db.execSQL(query);
        db.close();
    }

    public void update(int id, String nama, String alamat){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_SQLite + " SET " +
                KEY_NAMA + " = '" + nama + "', " + KEY_ALAMAT + " = '" + alamat + "'" +
                "WHERE " + KEY_ID + " = '" + id + "'";
        Log.e("insert query", "" + query);
        db.execSQL(query);
        db.close();
    }

    public void delete(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_SQLite + " WHERE " + KEY_ID + " = '" + id + "'";
        Log.e("insert query", "" + query);
        db.execSQL(query);
        db.close();
    }
}
