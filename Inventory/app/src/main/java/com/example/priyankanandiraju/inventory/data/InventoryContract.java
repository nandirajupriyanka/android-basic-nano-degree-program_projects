package com.example.priyankanandiraju.inventory.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by priyankanandiraju on 1/19/17.
 */

public class InventoryContract {

    public static final String CONTENT_AUTHORITY = "com.example.priyankanandiraju.inventory";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_INVENTORY = "inventory";

    public InventoryContract() {
    }

    public static final class InventoryEvent implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_INVENTORY);
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORY;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORY;

        public static final String TABLE_NAME = "entry";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_INVENTORY_IMAGE = "image";
        public static final String COLUMN_INVENTORY_NAME = "name";
        public static final String COLUMN_INVENTORY_QUANTITY = "quantity";
        public static final String COLUMN_INVENTORY_PRICE = "price";
        public static final String COLUMN_INVENTORY_SUPPLIER = "supplier";


    }
}
