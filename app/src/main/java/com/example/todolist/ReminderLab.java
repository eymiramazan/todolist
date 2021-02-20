package com.example.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.todolist.database.ReminderBaseHelper;
import com.example.todolist.database.ReminderCursorWrapper;
import com.example.todolist.database.ReminderDbSchema.ReminderTable;
import static com.example.todolist.database.ReminderDbSchema.ReminderTable.Columns.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReminderLab {
    private static ReminderLab reminderLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    //Singleton
    public static ReminderLab get(Context context) {
        if (reminderLab == null) {
            reminderLab = new ReminderLab(context);
        }
        return reminderLab;
    }

    private ReminderLab(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new ReminderBaseHelper(mContext).getWritableDatabase();
    }

    //add reminder to database
    public void addReminder(Reminder r){
        ContentValues values = getContentValues(r);
        mDatabase.insert(ReminderTable.NAME,null,values);
    }

    //get reminders for recyclerview
    public List<Reminder> getReminders(){
        List<Reminder> reminders = new ArrayList<>();
        ReminderCursorWrapper cursor = queryReminders(null,null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                reminders.add(cursor.getReminder());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return reminders;
    }

    //get specific reminder with id
    public Reminder getReminder(UUID id){
        ReminderCursorWrapper cursor = queryReminders(
                ReminderTable.Columns.ID + " = ?",
                new String[]{id.toString()}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getReminder();
        } finally {
            cursor.close();
        }
    }

    //update the content of reminder
    public void updateReminder(Reminder reminder){
        String idString = reminder.getId().toString();
        ContentValues values = getContentValues(reminder);
        mDatabase.update(ReminderTable.NAME,values,
                ReminderTable.Columns.ID + " = ?",
                new String[]{idString});
    }

    //Reminder query operations
    private ReminderCursorWrapper queryReminders(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                ReminderTable.NAME,
                null,whereClause,whereArgs,null,null,null
        );
        return new ReminderCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Reminder reminder){
        ContentValues values = new ContentValues();
        values.put(ID,reminder.getId().toString());
        values.put(TITLE,reminder.getTitle());
        values.put(DETAIL, reminder.getDetails());
        values.put(COMPLETED,reminder.isCompleted() ? 1 : 0);
        return values;
    }

    //delete reminder from database
    public void deleteReminder(Reminder reminder){
        String whereClause = reminder.getId().toString();
        mDatabase.delete(ReminderTable.NAME,"id=?",new String[]{whereClause});
    }

}
