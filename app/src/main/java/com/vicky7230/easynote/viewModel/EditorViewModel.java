package com.vicky7230.easynote.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.vicky7230.easynote.database.AppRepository;
import com.vicky7230.easynote.database.NoteEntity;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EditorViewModel extends AndroidViewModel {

    public MutableLiveData<NoteEntity> noteLiveData = new MutableLiveData<>();
    private AppRepository repository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public EditorViewModel(@NonNull Application application) {
        super(application);
        repository = AppRepository.getInstance(application.getApplicationContext());
    }

    public void loadData(final int noteId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                NoteEntity note = repository.getNoteById(noteId);
                noteLiveData.postValue(note);
            }
        });
    }

    public void saveNote(String noteText) {
        NoteEntity note = noteLiveData.getValue();
        if (note == null) {
            if (TextUtils.isEmpty(noteText.trim())) {
                return;
            } else {
                note = new NoteEntity(new Date(), noteText.trim());
            }
        } else {
            note.setText(noteText.trim());
        }
        repository.insertNote(note);
    }
}
