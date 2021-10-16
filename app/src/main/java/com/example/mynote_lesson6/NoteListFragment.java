package com.example.mynote_lesson6;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class NoteListFragment extends Fragment {
    private static final int FLAG_EDIT = 1;
    private static final int FLAG_NEW = 0;
    public static final int TEXT_ALIGNMENT_TEXT_START = 2;

    private static final String PREF_NAME = "notes";
    private SharedPreferences sp;

    private LinearLayout linearLayout;
    private RecyclerView recyclerView;
    private NotesAdapter adapter;
    private MaterialButton createNoteButton;
    private View view;
    private ScrollView scrollView;

    private ArrayList<NoteEntity> noteList = new ArrayList<>();

    public void addNoteToList(NoteEntity noteEntity) {

        if (noteEntity != null && noteEntity.flag == FLAG_EDIT) {
            linearLayout.removeAllViewsInLayout();
        }
        noteList.add(noteEntity);
        renderList(noteList);
    }


    private void renderList(ArrayList<NoteEntity> notes) {

        adapter.setData(notes);
    }

    private Controller getContract() {
        return (Controller) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_note_list, container, false);
        linearLayout = view.findViewById(R.id.linear);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        createNoteButton = view.findViewById(R.id.create_button);

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
        adapter = new NotesAdapter();
        adapter.setOnItemClickListener(getContract()::openProfileScreen);//eto pravilno
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        createNoteButton.setOnClickListener(v -> {
            getContract().createNewNote();
        });
    }

    interface Controller {
        void openProfileScreen(NoteEntity note);

        void createNewNote();
    }
}
