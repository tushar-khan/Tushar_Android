package com.example.tusharkhan.fetchmail;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper3 extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="Non_important";
    public static final String TABLE = "non_imp";
    public static final String COL1 = "ID";
    public static final String COL2 = "ITEM1";
    public static final String COL3 = "ITEM2";
    public static final String COL4 = "ITEM3";
    Context context;

    public DatabaseHelper3(Context context) {

        super(context, DATABASE_NAME, null, 1);
        this.context=context;
    }
    public void onCreate(SQLiteDatabase db) {

       String createTable = "CREATE TABLE " + TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " ITEM1 TEXT, " + " ITEM2 TEXT, " + " ITEM3 TEXT)";
        db.execSQL(createTable);

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE);
        onCreate(db);
    }
    public boolean addData(String item1,String item2,String item3) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item1);
        contentValues.put(COL3, item2);
        contentValues.put(COL4, item3);

        long result=db.insert(TABLE, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            System.out.println("Not inserted 2");
            return false;
        } else {
            System.out.println("Inserted 2");
            return true;
        }

    }
    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE, null);
        return data;
    }

    public void delete(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int res=db.delete(TABLE,"id=?",new String[]{String.valueOf(id)});
        if(res>0)
        {
            System.out.println("Deleted Successfully");
            Toast.makeText(context,"Deleted Successfully",Toast.LENGTH_LONG).show();

        }
        else
        {
            System.out.println("Something Wrong");
        }
    }
    public void deleteAll(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int res=db.delete(TABLE,"id=?",new String[]{String.valueOf(id)});
        if(res>0)
        {
            System.out.println("Deleted Successfully"+id);
            //Toast.makeText(context,"Deleted Successfully",Toast.LENGTH_LONG).show();

        }
        else
        {
            System.out.println("Something Wrong");
        }
    }
}
