package com.example.tusharkhan.fetchmail;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseUser extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "User";
    public static final String TABLE_NAME = "User1";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "username";
    public static final String COL_3 = "password";

    public DatabaseUser(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " username TEXT, " + " password TEXT)";
        db.execSQL(createTable);

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public boolean addData(String item1,String item2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, item1);
        contentValues.put(COL_3, item2);

        long result=db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            System.out.println("Not inserted user");
            return false;
        } else {
            System.out.println("Inserted user");
            return true;
        }

    }
    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    public void delete(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int res=db.delete(TABLE_NAME,"id=?",new String[]{String.valueOf(id)});
        if(res>0)
        {
            System.out.println("Deleted user Successfully");
        }
        else
        {
            System.out.println("Something Wrong deleting user");
        }
    }
}
