package com.example.priyankanandiraju.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.priyankanandiraju.habittracker.data.HabitContract.HabitEntry;
import com.example.priyankanandiraju.habittracker.data.HabitHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_db_data) TextView tvDbData;
    private HabitHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mDbHelper = new HabitHelper(MainActivity.this);

        insertHabit();
        displayData(readDbEntries());
    }

    private void displayData(Cursor cursor) {
        try {
            tvDbData.setText("Habits table contains " + cursor.getCount() + " habit.");
        } finally {
            cursor.close();
        }
    }

    /**
     * Insert habit into database
     */
    private void insertHabit() {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        //contentValues.put(HabitEntry._ID, 1);
        contentValues.put(HabitEntry.COLUMN_HABIT_NAME, "Walk dog");
        contentValues.put(HabitEntry.COLUMN_HABIT_DURATION, 30);

        db.insert(HabitEntry.TABLE_NAME, null, contentValues);
    }

    /**
     * Read database entries
     */
    private Cursor readDbEntries() {

        mDbHelper = new HabitHelper(MainActivity.this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String projection[] = {
                HabitEntry._ID,
                HabitEntry.COLUMN_HABIT_NAME,
                HabitEntry.COLUMN_HABIT_DURATION
        };

        Cursor cursor = db.query(
                HabitEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
        return cursor;
    }
}
