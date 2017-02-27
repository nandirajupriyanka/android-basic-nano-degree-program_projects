package com.example.priyankanandiraju.inventory.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.priyankanandiraju.inventory.data.InventoryContract.InventoryEvent;

/**
 * Created by priyankanandiraju on 1/19/17.
 */

public class InventoryDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Inventory.db";
    public static final int DATABASE_VERSION = 1;
    private static InventoryDbHelper mDbHelper;

    public static synchronized InventoryDbHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (mDbHelper == null) {
            mDbHelper = new InventoryDbHelper(context.getApplicationContext());
        }
        return mDbHelper;
    }


    public InventoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TABLE = "CREATE TABLE " + InventoryEvent.TABLE_NAME + " (" +
                InventoryEvent._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                InventoryEvent.COLUMN_INVENTORY_IMAGE + " INTEGER, " +
                InventoryEvent.COLUMN_INVENTORY_NAME + " TEXT NOT NULL, " +
                InventoryEvent.COLUMN_INVENTORY_QUANTITY + " INTEGER, " +
                InventoryEvent.COLUMN_INVENTORY_PRICE + " INTEGER NOT NULL DEFAULT 0, " +
                InventoryEvent.COLUMN_INVENTORY_SUPPLIER + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
