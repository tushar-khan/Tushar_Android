package com.example.tusharkhan.fetchmail;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Mitch on 2016-05-13.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mylist.db";
    public static final String TABLE_NAME = "mylist";
    public static final String COL1 = "ID";
    public static final String COL2 = "ITEM1";
    public static final String COL3 = "ITEM2";
    public static final String COL4 = "ITEM3";
    //new added lines starts
    public static final String TABLE = "mylist2";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "word";
    Context context;

    //new added lines starts
    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);
        this.context=context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " ITEM1 TEXT, " + " ITEM2 TEXT, " + " ITEM3 TEXT)";
        db.execSQL(createTable);
        //new added lines starts
        createTable = "CREATE TABLE " + TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " word TEXT)";
        db.execSQL(createTable);
        //new added lines starts
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String item1,String item2,String item3) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item1);
        contentValues.put(COL3, item2);
        contentValues.put(COL4, item3);

        long result=db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            System.out.println("Not inserted");
            return false;
        } else {
            System.out.println("Inserted 1");
            return true;
        }

    }
    public boolean addData2(String item1) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, item1);

        long result=db.insert(TABLE, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            System.out.println("Inserted imp");
            return true;
        }

    }
    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }
    public Cursor getListContents2(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE, null);
        return data;
    }
    public void delete(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int res=db.delete(TABLE_NAME,"id=?",new String[]{String.valueOf(id)});
        if(res>0)
        {
            System.out.println("Deleted Successfully ");
            Toast.makeText(context,"Deleted Successfully",Toast.LENGTH_LONG).show();
        }
        else
        {
            System.out.println("Something Wrong");
        }
    }

}