package com.khanhlam.mynotes.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.khanhlam.mynotes.Database.CreateDatabase;
import com.khanhlam.mynotes.Model.Notes;

import java.util.ArrayList;
import java.util.List;

public class NotesDAO {
    private SQLiteDatabase sqLiteDatabase;

    public NotesDAO(Context context) {
        CreateDatabase createDatabase = new CreateDatabase(context);
        sqLiteDatabase = createDatabase.open();
    }


    public boolean AddNotes(String Title, String Content, String DateTime) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_NOTES_TITLE, Title);
        contentValues.put(CreateDatabase.TB_NOTES_CONTENT, Content);
        contentValues.put(CreateDatabase.TB_NOTES_DATETIME, DateTime);

        long kiemtra = sqLiteDatabase.insert(CreateDatabase.TB_NOTES, null, contentValues);
        if (kiemtra != 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<Notes> AllNotes() {
        List<Notes> notesList = new ArrayList<>();
        String truyvan = " SELECT * FROM " + CreateDatabase.TB_NOTES;
        Cursor cursor = sqLiteDatabase.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Notes notes = new Notes();
            notes.setIdNotes(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NOTES_ID)));
            notes.setTitle(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NOTES_TITLE)));
            notes.setContent(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NOTES_CONTENT)));
            notes.setDateTime(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NOTES_DATETIME)));

            notesList.add(notes);
            cursor.moveToNext();
        }
        return notesList;
    }

    public Notes GetListId(int id) {
        Notes notes = new Notes();
        String truyvan = " SELECT * FROM " + CreateDatabase.TB_NOTES + " WHERE " + CreateDatabase.TB_NOTES_ID + " = " + id;
        Cursor cursor = sqLiteDatabase.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            notes.setIdNotes(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NOTES_ID)));
            notes.setTitle(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NOTES_TITLE)));
            notes.setContent(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NOTES_CONTENT)));
            cursor.moveToNext();
        }
        return notes;
    }

    public boolean UpdateNotes(String Title, String Content, String DateTime, int id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_NOTES_TITLE, Title);
        contentValues.put(CreateDatabase.TB_NOTES_CONTENT, Content);
        contentValues.put(CreateDatabase.TB_NOTES_DATETIME, DateTime);

        long kiemtra = sqLiteDatabase.update(CreateDatabase.TB_NOTES, contentValues, CreateDatabase.TB_NOTES_ID + " = '" + id + " '", null);
        if (kiemtra != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean DeleteNotes(int id) {
        long kiemtra = sqLiteDatabase.delete(CreateDatabase.TB_NOTES, CreateDatabase.TB_NOTES_ID + " = " + id, null);
        if (kiemtra != 0) {
            return true;
        } else {
            return false;
        }
    }

}
