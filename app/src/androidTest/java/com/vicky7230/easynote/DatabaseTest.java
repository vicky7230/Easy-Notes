package com.vicky7230.easynote;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.vicky7230.easynote.database.AppDatabase;
import com.vicky7230.easynote.database.NoteDao;
import com.vicky7230.easynote.database.NoteEntity;
import com.vicky7230.easynote.utilities.SampleData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    public static final String TAG = "Junit";
    private AppDatabase db;
    private NoteDao dao;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .build();
        dao = db.noteDao();
        Log.i(TAG, "cretaedDB");
    }

    @After
    public void closeDb() {
        db.clearAllTables();
        Log.i(TAG, "closeDB");
    }

    @Test
    public void createAndRetrieveNotes() {
        dao.insertAll(SampleData.getNotes());
        int count = dao.getCount();
        Log.i(TAG, "createAndRetrieveNotes : count = " + count);
        assertEquals(SampleData.getNotes().size(), count);
    }

    @Test
    public void compareStrings() {
        dao.insertAll(SampleData.getNotes());
        NoteEntity original = SampleData.getNotes().get(0);
        NoteEntity fromDb = dao.getNoteById(1);
        assertEquals(original.getText(), fromDb.getText());
        assertEquals(1, fromDb.getId());
    }
}
