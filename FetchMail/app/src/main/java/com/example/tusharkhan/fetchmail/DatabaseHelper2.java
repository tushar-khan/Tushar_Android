package com.example.tusharkhan.fetchmail;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper2 extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mylist1.db";
    public static final String TABLE_NAME = "mylist1";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "WORD";
    Context context;



    public DatabaseHelper2(Context context) {

        super(context, DATABASE_NAME, null, 1);
        this.context=context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " WORD TEXT)";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean DataAdd(String item1) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, item1);

        long result=db.insert(TABLE_NAME, null, contentValues);
        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            System.out.println("Not inserted");
            return false;
        } else {
            System.out.println("Inserted");
            return true;
        }

    }

    public Cursor getlistContent(){
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
            System.out.println("Deleted Successfully");

        }
        else
        {
            System.out.println("Something Wrong");
        }
    }
}

