package com.vicky7230.easynote.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.vicky7230.easynote.database.AppRepository;
import com.vicky7230.easynote.database.NoteEntity;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    public LiveData<List<NoteEntity>> notes;
    public AppRepository repository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = AppRepository.getInstance(application.getApplicationContext());
        notes = repository.notes;
    }

    public void addSampleData() {
        repository.addSampleData();
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }
}
