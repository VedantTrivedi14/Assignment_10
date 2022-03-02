package com.tatvasoftassignment.myapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tatvasoftassignment.myapplication.Utils.Constant;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static int id = 0;

    public DatabaseHelper(Context context) {
        super(context, "student.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table studentList(id NUMBER, name TEXT,email TEXT,contactNo TEXT,address TEXT,birthDate TEXT,bloodGroup TEXT,gender TEXT,language TEXT )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertData(String name, String email, String contactNo, String address, String birthDate, String bloodGroup, String gender, String language) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.Id, id);
        id = id + 1;
        contentValues.put(Constant.name, name);
        contentValues.put(Constant.email, email);
        contentValues.put(Constant.contactNo, contactNo);
        contentValues.put(Constant.address, address);
        contentValues.put(Constant.birthDate, birthDate);
        contentValues.put(Constant.bloodGroup, bloodGroup);
        contentValues.put(Constant.gender, gender);
        contentValues.put(Constant.language, language);
        db.insert(Constant.studentList, null, contentValues);

    }

    public Cursor getData() {
        SQLiteDatabase Db = this.getWritableDatabase();
        return Db.rawQuery("Select * from studentList", null);
    }

    public Cursor getDataById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.rawQuery("select * from studentList Where id=?", new String[]{String.valueOf(id)});
    }

    public Boolean updateData(int id, String name, String email, String contactNo, String address, String birthDate, String bloodGroup, String gender, String language) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.name, name);
        contentValues.put(Constant.email, email);
        contentValues.put(Constant.contactNo, contactNo);
        contentValues.put(Constant.address, address);
        contentValues.put(Constant.birthDate, birthDate);
        contentValues.put(Constant.bloodGroup, bloodGroup);
        contentValues.put(Constant.gender, gender);
        contentValues.put(Constant.language, language);
        long result = db.update(Constant.studentList, contentValues, "id=?", new String[]{String.valueOf(id)});
        return result != -1;
    }


    public void deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("studentList", "id=?", new String[]{String.valueOf(id)});
    }
}
