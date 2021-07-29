package com.example.mynote_lesson6;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements NoteFragment.Controller, NoteListFragment.Controller {
    private boolean isLandscape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, new NoteListFragment(), "NoteListFragment")
                .commit();
    }

    @Override
    public void saveResult(NoteEntity note) {
        getSupportFragmentManager().popBackStack();
        NoteListFragment noteListFragment = (NoteListFragment) getSupportFragmentManager().
                findFragmentByTag("NoteListFragment");

        if (note != null) {
            noteListFragment.addNoteToList(note);
        }
        NoteFragment noteFragment = (NoteFragment) getSupportFragmentManager().findFragmentByTag("NoteFragment");
        if (noteFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(noteFragment).commit();
        }
    }

    @Override
    public void createNewNote() {
        openProfileScreen(null);
    }

    @Override
    public void openProfileScreen(NoteEntity note) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(isLandscape ? R.id.detail_container : R.id.container, NoteFragment.newInstance(note), "NoteFragment")
                .addToBackStack(null)
                .commit();
    }
}