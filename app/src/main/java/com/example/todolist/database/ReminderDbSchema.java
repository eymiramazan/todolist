package com.example.todolist.database;

public class ReminderDbSchema {
    public static final class ReminderTable {
        public static final String NAME = "reminders";

        public static final class Columns{
            public static final String ID = "id";
            public static final String TITLE = "title";
            public static final String DETAIL = "detail";
            public static final String COMPLETED = "completed";
        }
    }
}
