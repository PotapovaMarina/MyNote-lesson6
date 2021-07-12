package com.example.mynote_lesson6;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class NoteListFragment extends Fragment {
    private LinearLayout linearLayout;
    private MaterialButton createNoteButton;
    public static final int TEXT_ALIGNMENT_TEXT_START = 2;
    private ArrayList<NoteEntity> noteList = new ArrayList<>();

    public void addNoteToList(NoteEntity noteEntity) {
        if (noteEntity != null && noteEntity.flag == 0) {
            noteList.add(noteEntity);
            addButtonWithNote(noteEntity);
        } else if (noteEntity != null && noteEntity.flag == 1) {
            linearLayout.removeAllViewsInLayout();
            for (NoteEntity note : noteList) {
                addButtonWithNote(note);
            }
        }
    }

    public void addButtonWithNote(NoteEntity noteEntity) {
        Button button = new Button(getContext());
        button.setText(noteEntity.toString());
        button.setOnClickListener(v -> ((Controller) getActivity()).openProfileScreen(noteEntity));

        LinearLayout.LayoutParams buttonparam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(buttonparam);
        linearLayout.addView(button);
        button.setGravity(Gravity.START);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_list, null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof Controller)) {
            throw new RuntimeException("Activity must implements NoteListFragment.Controller");
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        linearLayout = view.findViewById(R.id.linear);
        createNoteButton = view.findViewById(R.id.create_button);
        createNoteButton.setOnClickListener(v -> {
            ((Controller) getActivity()).createNewNote();
        });
    }

    interface Controller {
        void openProfileScreen(NoteEntity note);

        void createNewNote();
    }
}
