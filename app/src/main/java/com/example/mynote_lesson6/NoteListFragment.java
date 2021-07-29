package com.example.mynote_lesson6;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class NoteListFragment extends Fragment {
    private LinearLayout linearLayout;
    private static final int FLAG_EDIT = 1;
    private static final int FLAG_NEW = 0;
    private MaterialButton createNoteButton;
    public static final int TEXT_ALIGNMENT_TEXT_START = 2;
    private View view;
    private ScrollView scrollView;
    private ArrayList<NoteEntity> noteList = new ArrayList<>();

    public void addNoteToList(NoteEntity noteEntity) {
        if (noteEntity != null && noteEntity.flag == FLAG_NEW) {
            noteList.add(noteEntity);
            addButtonWithNote(noteEntity);
        } else if (noteEntity != null && noteEntity.flag == FLAG_EDIT) {
            linearLayout.removeAllViewsInLayout();
            for (NoteEntity note : noteList) {
                addButtonWithNote(note);
            }
        }
    }

    public void addButtonWithNote(NoteEntity noteEntity) {
        Button button = new Button(getContext());
        button.setText(noteEntity.toString());
        button.setOnClickListener(v -> {
            initPopupMenu(view, noteEntity);
        });
        LinearLayout.LayoutParams buttonparam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(buttonparam);
        linearLayout.addView(button);
        button.setGravity(Gravity.START);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_note_list, null);
        return view;
    }

    private void initPopupMenu(View view, NoteEntity noteEntity) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), linearLayout, Gravity.TOP);
        getActivity().getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        Menu menu = popupMenu.getMenu();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.menu_clear:
                        linearLayout.removeAllViewsInLayout();
                        return true;
                    case R.id.menu_add:
                        ((Controller) getActivity()).createNewNote();
                        return true;
                    case R.id.menu_open:
                        ((Controller) getActivity()).openProfileScreen(noteEntity);
                }
                return true;
            }
        });
        popupMenu.show();
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
        scrollView = view.findViewById(R.id.scroll_view);
        createNoteButton = view.findViewById(R.id.create_button);
        createNoteButton.setOnClickListener(v -> {
            ((Controller) getActivity()).createNewNote();
        });
        setHasOptionsMenu(true);
    }

    interface Controller {
        void openProfileScreen(NoteEntity note);
        void createNewNote();
    }
}
