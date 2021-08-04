package com.example.todolist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReminderListFragment extends Fragment {

    private RecyclerView reminderRecyclerView;
    private ReminderAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminder_list, container, false);

        reminderRecyclerView = (RecyclerView) view
                .findViewById(R.id.reminderRecyclerView);
        reminderRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_reminder_list, menu);
    }

    //click add icon
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Reminder reminder = new Reminder();
        ReminderLab.get(getActivity()).addReminder(reminder);
        Intent intent = ReminderPagerActivity.newIntent(getActivity(), reminder.getId());
        startActivity(intent);
        return true;
    }

    //update ui after change or add reminder
    @SuppressLint("NotifyDataSetChanged")
    private void updateUI() {
        System.out.println("update ui");
        ReminderLab reminderLab = ReminderLab.get(getActivity());
        List<Reminder> reminders = reminderLab.getReminders();

        for (Reminder reminder : reminders) {
            if (reminder.getId() != null && reminder.getTitle() == null
                    && reminder.getDetails() == null) {
                reminders.remove(reminder);
                reminderLab.deleteReminder(reminder);
            }
        }

        if (mAdapter == null) {
            mAdapter = new ReminderAdapter(reminders);
            reminderRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setReminder(reminders);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class ReminderHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Reminder reminder;

        private TextView mTitleTextView;
        private TextView mDetailTextView;
        private ImageView mCompletedImageView;

        public ReminderHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_reminder, parent, false));
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.reminder_title);
            mDetailTextView = (TextView) itemView.findViewById(R.id.reminderDetail);
            mCompletedImageView = (ImageView) itemView.findViewById(R.id.reminderCheckImage);
        }

        //set recyclerview items title and details
        public void bind(Reminder reminder) {
            this.reminder = reminder;
            mTitleTextView.setText(reminder.getTitle());
            mDetailTextView.setText(reminder.getDetails());
            mCompletedImageView.setVisibility(reminder.isCompleted() ? View.VISIBLE : View.GONE);
        }

        //click specific reminder
        @Override
        public void onClick(View view) {
            Intent intent = ReminderPagerActivity.newIntent(getActivity(), reminder.getId());
            startActivity(intent);
        }
    }

    private class ReminderAdapter extends RecyclerView.Adapter<ReminderHolder> {

        private List<Reminder> reminders;

        public ReminderAdapter(List<Reminder> reminder) {
            reminders = reminder;
        }

        @Override
        public ReminderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ReminderHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ReminderHolder holder, int position) {
            Reminder reminder = reminders.get(position);
            holder.bind(reminder);
        }

        @Override
        public int getItemCount() {
            return reminders.size();
        }

        public void setReminder(List<Reminder> reminder) {
            reminders = reminder;
        }
    }
}
