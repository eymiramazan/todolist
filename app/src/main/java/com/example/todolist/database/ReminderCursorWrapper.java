package com.example.todolist.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.todolist.Reminder;
import com.example.todolist.database.ReminderDbSchema.ReminderTable;

import java.util.UUID;

public class ReminderCursorWrapper extends CursorWrapper {

    public ReminderCursorWrapper(Cursor cursor){ super(cursor);}

    public Reminder getReminder(){
        String idString = getString(getColumnIndex(ReminderTable.Columns.ID));
        String title = getString(getColumnIndex(ReminderTable.Columns.TITLE));
        String detail = getString(getColumnIndex(ReminderTable.Columns.DETAIL));
        int isCompleted = getInt(getColumnIndex(ReminderTable.Columns.COMPLETED));

        Reminder reminder = new Reminder(UUID.fromString(idString));
        reminder.setTitle(title);
        reminder.setDetails(detail);
        reminder.setCompleted(isCompleted != 0);

        return reminder;
    }
}
