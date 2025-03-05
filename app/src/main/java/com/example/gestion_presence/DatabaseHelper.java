package com.example.gestion_presence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "employes.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE employes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nom TEXT NOT NULL, " +
                "poste TEXT NOT NULL, " +
                "salaire REAL NOT NULL)";
        db.execSQL(createTable);
        String crateTablePresence = "CREATE TABLE presences (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT ," +
                " employe_id INTEGER ," +
                "date TEXT , " +
                "presence INTEGER,FOREIGN KEY (employe_id) REFERENCES employes(id))";
        db.execSQL(crateTablePresence);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS employes");
        onCreate(db);
    }

    public void marquerPresence(int employeId, boolean present) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("employe_id", employeId);
        values.put("date", System.currentTimeMillis()); // Date actuelle
        values.put("presence", present ? 1 : 0);

        db.insert("presences", null, values);
        db.close();
    }

    public List<Employe> getAllEmployes() {
        List<Employe> employeList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM employes", null);

        if (cursor.moveToFirst()) {
            do {
                Employe employe = new Employe(
                        cursor.getString(0),  // ID
                        cursor.getString(1),  // Nom
                        cursor.getDouble(2)   // Salaire
                );
                employeList.add(employe);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return employeList;
    }




}


