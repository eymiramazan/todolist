package com.example.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.ComponentActivity;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.core.app.SharedElementCallback;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;
import java.util.UUID;

public class ReminderFragment extends Fragment  {

    private static final String REMINDER_ID = "reminder_id";

    private Reminder reminder;
    private EditText reminderEditText;
    private EditText remindEditText;
    private CheckBox completedCheckBox;
    private Button deleteButton, addButton;

    public static ReminderFragment newInstance(UUID reminderId) {
        Bundle args = new Bundle();
        args.putSerializable(REMINDER_ID, reminderId);

        ReminderFragment fragment = new ReminderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID reminderId = (UUID) getArguments().getSerializable(REMINDER_ID);
        reminder = ReminderLab.get(getActivity()).getReminder(reminderId);
    }

    //create components and define their functions
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reminder, container, false);

        reminderEditText = (EditText) v.findViewById(R.id.reminder_title);
        reminderEditText.setText(reminder.getTitle());

        remindEditText = (EditText) v.findViewById(R.id.reminderDetail);
        remindEditText.setText(reminder.getDetails());

        addButton = (Button) v.findViewById(R.id.addButton);
        addButton.setOnClickListener(v1 -> {
            reminder.setTitle(reminderEditText.getText().toString());
            reminder.setDetails(remindEditText.getText().toString());
            Objects.requireNonNull(getActivity()).onBackPressed();
        });

        completedCheckBox = (CheckBox) v.findViewById(R.id.reminderCheckImage);
        completedCheckBox.setChecked(reminder.isCompleted());
        completedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                reminder.setCompleted(isChecked);
            }
        });

        deleteButton = (Button) v.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ReminderLab.get(getContext()).deleteReminder(reminder);
                getActivity().onBackPressed();
            }
        });

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        ReminderLab.get(getActivity()).updateReminder(reminder);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
    }

}