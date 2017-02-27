package com.example.priyankanandiraju.inventory.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.priyankanandiraju.inventory.data.InventoryContract.InventoryEvent;

/**
 * Created by priyankanandiraju on 1/19/17.
 */

public class InventoryProvider extends ContentProvider {
    public static final String LOG_TAG = InventoryProvider.class.getSimpleName();
    private static final int INVENTORY = 100;
    private static final int INVENTORY_ITEM = 200;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(InventoryContract.CONTENT_AUTHORITY, InventoryContract.PATH_INVENTORY, INVENTORY);
        sUriMatcher.addURI(InventoryContract.CONTENT_AUTHORITY, InventoryContract.PATH_INVENTORY + "/#", INVENTORY_ITEM);
    }

    private InventoryDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = InventoryDbHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case INVENTORY:
                cursor = database.query(InventoryEvent.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case INVENTORY_ITEM:
                selection = InventoryEvent._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(InventoryEvent.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case INVENTORY:
                return InventoryEvent.CONTENT_LIST_TYPE;
            case INVENTORY_ITEM:
                return InventoryEvent.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case INVENTORY:
                return insertInventory(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertInventory(Uri uri, ContentValues values) {
        Integer image = values.getAsInteger(InventoryEvent.COLUMN_INVENTORY_IMAGE);
        if (image == null) {
            throw new IllegalArgumentException("Inventory requires a image");
        }

        String name = values.getAsString(InventoryEvent.COLUMN_INVENTORY_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Inventory requires a name");
        }

        Integer quantity = values.getAsInteger(InventoryEvent.COLUMN_INVENTORY_QUANTITY);
        if (quantity == null) {
            throw new IllegalArgumentException("Inventory requires valid quantity");
        }

        Integer price = values.getAsInteger(InventoryEvent.COLUMN_INVENTORY_PRICE);
        if (price != null && price < 0) {
            throw new IllegalArgumentException("Inventory requires valid price");
        }

        String supplier = values.getAsString(InventoryEvent.COLUMN_INVENTORY_SUPPLIER);
        if (supplier == null) {
            throw new IllegalArgumentException("Inventory requires a supplier");
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        long id = database.insert(InventoryEvent.TABLE_NAME, null, values);
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsDeleted;
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case INVENTORY:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(InventoryEvent.TABLE_NAME, selection, selectionArgs);
                break;
            case INVENTORY_ITEM:
                // Delete a single row given by the ID in the URI
                selection = InventoryEvent._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(InventoryEvent.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case INVENTORY:
                return updateInventory(uri, values, selection, selectionArgs);
            case INVENTORY_ITEM:
                selection = InventoryEvent._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateInventory(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateInventory(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (values.containsKey(InventoryEvent.COLUMN_INVENTORY_IMAGE)) {
            Integer image = values.getAsInteger(InventoryEvent.COLUMN_INVENTORY_IMAGE);
            if (image == null) {
                throw new IllegalArgumentException("Inventory requires a image");
            }
        }

        if (values.containsKey(InventoryEvent.COLUMN_INVENTORY_NAME)) {
            String name = values.getAsString(InventoryEvent.COLUMN_INVENTORY_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Inventory requires a name");
            }
        }
        if (values.containsKey(InventoryEvent.COLUMN_INVENTORY_QUANTITY)) {
            Integer quantity = values.getAsInteger(InventoryEvent.COLUMN_INVENTORY_QUANTITY);
            if (quantity == null) {
                throw new IllegalArgumentException("Inventory requires valid quantity");
            }
        }
        if (values.containsKey(InventoryEvent.COLUMN_INVENTORY_PRICE)) {
            Integer price = values.getAsInteger(InventoryEvent.COLUMN_INVENTORY_PRICE);
            if (price != null && price < 0) {
                throw new IllegalArgumentException("Inventory requires valid price");
            }
        }
        if (values.containsKey(InventoryEvent.COLUMN_INVENTORY_SUPPLIER)) {
            String supplier = values.getAsString(InventoryEvent.COLUMN_INVENTORY_SUPPLIER);
            if (supplier == null) {
                throw new IllegalArgumentException("Inventory requires a supplier");
            }
        }
        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsUpdated = database.update(InventoryEvent.TABLE_NAME, values, selection, selectionArgs);
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }
}
