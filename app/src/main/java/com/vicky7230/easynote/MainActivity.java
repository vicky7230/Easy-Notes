package com.vicky7230.easynote;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.vicky7230.easynote.database.NoteEntity;
import com.vicky7230.easynote.ui.NotesAdapter;
import com.vicky7230.easynote.viewModel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private List<NoteEntity> notesData = new ArrayList<>();
    private NotesAdapter notesAdapter;
    private MainViewModel MainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        init();
    }

    private void init() {

        setSupportActionBar(toolbar);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        MainViewModel.notes.observe(this, new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(@Nullable List<NoteEntity> noteEntities) {
                notesData.clear();
                notesData.addAll(noteEntities);

                if (notesAdapter == null) {
                    notesAdapter = new NotesAdapter(notesData, MainActivity.this);
                    recyclerView.setAdapter(notesAdapter);
                } else {
                    notesAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @OnClick(R.id.fab)
    public void fabClickHandler() {
        Intent intent = new Intent(this, EditorActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_sample_data) {
            addSampleData();
            return true;
        } else if (id == R.id.action_delete_all) {
            deleteAllNotes();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteAllNotes() {
        MainViewModel.deleteAllNotes();
    }

    private void addSampleData() {
        MainViewModel.addSampleData();
    }
}
