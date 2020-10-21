package com.khanhlam.mynotes.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CreateDatabase extends SQLiteOpenHelper {

    private static int DATABASE_VESION = 1;
    private static String DATABASE_NAME = "MyNotes";

    public static String TB_NOTES = "NOTES";

    public static String TB_NOTES_ID = "IDNOTES";
    public static String TB_NOTES_TITLE = "TITLE";
    public static String TB_NOTES_CONTENT = "CONTENT";
    public static String TB_NOTES_DATETIME = "DATETIME";

    public CreateDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VESION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String tbNOTES = "CREATE TABLE " + TB_NOTES
                + " ( " + TB_NOTES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TB_NOTES_TITLE + " TEXT, "
                + TB_NOTES_CONTENT + " TEXT, "
                + TB_NOTES_DATETIME + " TEXT )";

        sqLiteDatabase.execSQL(tbNOTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public SQLiteDatabase open() {
        return this.getWritableDatabase();
    }

}
