package com.example.todolist;

import androidx.fragment.app.Fragment;

public class ReminderListActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        return new ReminderListFragment();
    }
}
