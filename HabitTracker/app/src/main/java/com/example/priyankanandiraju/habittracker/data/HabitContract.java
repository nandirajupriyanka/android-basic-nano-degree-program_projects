package com.example.priyankanandiraju.habittracker.data;

import android.provider.BaseColumns;

/**
 * Created by priyankanandiraju on 1/14/17.
 */

public final class HabitContract {

    public HabitContract() {
    }

    public static final class HabitEntry implements BaseColumns {
        public static final String TABLE_NAME = "habit";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_HABIT_NAME = "name";
        public static final String COLUMN_HABIT_DURATION = "duration";

    }
}
