package com.example.todoapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class MySQLiteHelper extends SQLiteOpenHelper {


    public static final String TABLE_LIST = "list";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LISTS = "lists";

    private static final String DATABASE_NAME = "mylists.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_LIST + "(" + COLUMN_ID
            + " INTEGER PRIMARY KEY, " + COLUMN_NAME
            + " VARCHAR, " + COLUMN_LISTS
            + " VARCHAR );";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase database) { database.execSQL(DATABASE_CREATE); }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIST);
        onCreate(db);
    }

    public void addList(MyList list) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, list.getName());
        values.put(COLUMN_LISTS, list.getLists());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_LIST, null, values);
        db.close();
    }

    public boolean deleteList(MyList list) {
        SQLiteDatabase db = this.getWritableDatabase();
        int nrows = db.delete(TABLE_LIST, COLUMN_ID + " = ?", new String[] {list.get_id()});
        db.close();
        return nrows != 0;
    }

    public boolean updateList(MyList list) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, list.getName());
        values.put(COLUMN_LISTS, list.getLists());
        int nrows = db.update(TABLE_LIST,values, COLUMN_ID + " = ?", new String[] {list.get_id()});
        db.close();
        return nrows != 0;
    }

    private MyList cursorToList(Cursor cursor)
    {
        MyList list = new MyList();
        list.set_id(cursor.getString(0));
        list.setName(cursor.getString(1));
        list.setLists(cursor.getString(2));
        return list;
    }

    public MyList findList(String name) {
        String query = "Select * FROM " + TABLE_LIST + " WHERE " + COLUMN_NAME+ " =  \"" + name + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        MyList list=null;
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            list = cursorToList(cursor);
            cursor.close();
        }
        db.close();
        return list;
    }

    public List<MyList> findAllLists() {
        List<MyList> lists = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(MySQLiteHelper.TABLE_LIST,
                new String[]{COLUMN_ID,COLUMN_NAME,COLUMN_LISTS}, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MyList list = cursorToList(cursor);
            lists.add(list);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        db.close();
        return lists;
    }
    public Cursor viewData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " +TABLE_LIST;
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }
    public Cursor viewList(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_LIST + " WHERE " + COLUMN_NAME+ " = \"" + name + "\"";
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }
}
