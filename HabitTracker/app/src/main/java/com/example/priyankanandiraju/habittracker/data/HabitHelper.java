package com.example.priyankanandiraju.habittracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by priyankanandiraju on 1/14/17.
 */

public class HabitHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "habit.db";
    private static final int DATABASE_VERSION = 1;

    public HabitHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TABLE = "CREATE TABLE " + HabitContract.HabitEntry.TABLE_NAME +
                " (" +
                HabitContract.HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                HabitContract.HabitEntry.COLUMN_HABIT_NAME + " TEXT NOT NULL," +
                HabitContract.HabitEntry.COLUMN_HABIT_DURATION + " INTEGER NOT NULL DEFAULT 0);";

        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + HabitContract.HabitEntry.TABLE_NAME;

        db.execSQL(SQL_DROP_TABLE);
        onCreate(db);
    }
}
