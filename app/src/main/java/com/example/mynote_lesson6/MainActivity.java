package com.example.mynote_lesson6;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NoteFragment.Controller, NoteListFragment.Controller {
    private boolean isLandscape;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        Toolbar toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        initDrawer(toolbar);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, new NoteListFragment(), "NoteListFragment")
                .commit();
    }

    private void initDrawer(Toolbar toolbar) {

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (navigateFragment(id)) {
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (navigateFragment(item.getItemId())) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean navigateFragment(int id) {
        switch (id) {
            case R.id.action_search:
                Toast.makeText(this, "Searching the note", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_main:
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.container, new NoteListFragment(), "NoteListFragment")
                        .addToBackStack(null)
                        .commit();
                return true;
            case R.id.action_about_app:
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.container, new AboutAppFragment())
                        .addToBackStack(null)
                        .commit();
                return true;
            default:
                break;
        }
        return false;
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