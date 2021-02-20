package com.example.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.List;
import java.util.UUID;

public class ReminderPagerActivity extends AppCompatActivity {
    private static final String EXTRA_REMINDER_ID =
            "com.example.todolist.reminder_id";

    private ViewPager mViewPager;
    private List<Reminder> reminders;

    public static Intent newIntent(Context packageContext, UUID reminderId) {
        Intent intent = new Intent(packageContext, ReminderPagerActivity.class);
        intent.putExtra(EXTRA_REMINDER_ID, reminderId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_pager);

        UUID reminderId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_REMINDER_ID);

        mViewPager = (ViewPager) findViewById(R.id.reminderViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager,true);

        reminders = ReminderLab.get(this).getReminders();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                Reminder reminder = reminders.get(position);
                return ReminderFragment.newInstance(reminder.getId());
            }

            @Override
            public int getCount() {
                return reminders.size();
            }
        });

        for (int i = 0; i < reminders.size(); i++) {
            if (reminders.get(i).getId().equals(reminderId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}


