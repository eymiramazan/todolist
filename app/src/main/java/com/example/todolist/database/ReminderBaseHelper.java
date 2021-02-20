package com.example.todolist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.todolist.database.ReminderDbSchema.ReminderTable;

public class ReminderBaseHelper extends SQLiteOpenHelper {

    public static final int VERSION = 1;
    public static final String DATABASE_NAME = "remindersBase.db";

    public ReminderBaseHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ReminderTable.NAME  + "(" +
                " _id integer primary key autoincrement, " +
                ReminderTable.Columns.ID + ", " +
                ReminderTable.Columns.TITLE + ", " +
                ReminderTable.Columns.DETAIL + ", " +
                ReminderTable.Columns.COMPLETED + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
