package com.example.mynote_lesson6;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import java.util.Calendar;

public class NoteFragment extends Fragment {
    public static final String NOTE_ARGS_KEY = "NOTE_ARGS_KEY";
    private static final int FLAG_EDIT = 1;
    private static final int FLAG_NEW = 0;

    private NoteEntity note = null;
    private EditText headEt;
    private EditText textEt;
    private MaterialButton saveButton;
    private DatePicker datePicker;
    private EditText editTextDate;

    public static NoteFragment newInstance(NoteEntity noteEntity) {
        NoteFragment noteFragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(NOTE_ARGS_KEY, noteEntity);
        noteFragment.setArguments(args);
        return noteFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, null);
        headEt = view.findViewById(R.id.head_edit_text);
        textEt = view.findViewById(R.id.note_edit_text);
        editTextDate = view.findViewById(R.id.editText_date);
        datePicker = view.findViewById(R.id.datePicker);
        saveButton = view.findViewById(R.id.save_button);
        datePickerInit();
        saveButton.setOnClickListener(v -> {
            Controller controller = (Controller) getActivity();
            if (note != null) {
                note.setText(textEt.getText().toString());
                note.setHead(headEt.getText().toString());
                note.setDate(editTextDate.getText().toString());
                note.setFlag(FLAG_EDIT);
                controller.saveResult(note);
            } else {
                controller.saveResult(
                        new NoteEntity(
                                headEt.getText().toString(),
                                textEt.getText().toString(),
                                editTextDate.getText().toString(),
                                FLAG_NEW
                        ));
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        note = (NoteEntity) getArguments().getParcelable(NOTE_ARGS_KEY);
        if (note == null) return;
        headEt.setText(note.head);
        textEt.setText(note.text);
        editTextDate.setText(note.date);
    }

    private void datePickerInit() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                datePickerChange(datePicker, year, month, dayOfMonth);
            }
        });
    }

    private void datePickerChange(DatePicker datePicker, int year, int month, int dayOfMonth) {
        editTextDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof Controller)) {
            throw new RuntimeException("Activity must implements ProfileFragment.Controller");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(NOTE_ARGS_KEY, note);
        super.onSaveInstanceState(outState);
    }

    public interface Controller {
        void saveResult(NoteEntity note);
    }
}
