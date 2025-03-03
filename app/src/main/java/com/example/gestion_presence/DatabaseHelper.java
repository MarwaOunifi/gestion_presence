package com.example.gestion_presence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "PresenceDB";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Employees (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, salary REAL);");
        db.execSQL("CREATE TABLE Attendance (id INTEGER PRIMARY KEY AUTOINCREMENT, employee_id INTEGER, type TEXT, days INTEGER, hours INTEGER, FOREIGN KEY(employee_id) REFERENCES Employees(id));");
        db.execSQL("CREATE TABLE Admin (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Employees");
        db.execSQL("DROP TABLE IF EXISTS Attendance");
        db.execSQL("DROP TABLE IF EXISTS Admin");
        onCreate(db);
    }

    public boolean addEmployee(String name, double salary) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("salary", salary);
        long result = db.insert("Employees", null, values);
        return result != -1;
    }

    public Cursor getAllEmployees() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM Employees", null);
    }

    public boolean markAttendance(int employeeId, String type, int days, int hours) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("employee_id", employeeId);
        values.put("type", type);
        values.put("days", days);
        values.put("hours", hours);
        long result = db.insert("Attendance", null, values);
        return result != -1;
    }

    public Cursor getAttendance(int employeeId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM Attendance WHERE employee_id = ?", new String[]{String.valueOf(employeeId)});
    }

    public boolean addAdmin(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        long result = db.insert("Admin", null, values);
        return result != -1;
    }

    public boolean checkAdminLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Admin WHERE username = ? AND password = ?", new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}
