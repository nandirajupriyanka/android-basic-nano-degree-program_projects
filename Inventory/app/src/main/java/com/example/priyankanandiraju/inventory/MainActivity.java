package com.example.priyankanandiraju.inventory;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.priyankanandiraju.inventory.data.InventoryContract.InventoryEvent;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, InventoryAdapter.BtnClickListener {

    private static final int INVENTORY_LOADER = 0;
    private Button btnAdd;
    private View tvEmptyView;
    private ListView lvInventory;
    private InventoryAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = (Button) findViewById(R.id.btn_add);
        tvEmptyView = findViewById(R.id.tv_empty);
        lvInventory = (ListView) findViewById(R.id.lv_inventory);
        lvInventory.setEmptyView(tvEmptyView);

        mCursorAdapter = new InventoryAdapter(this, null, this);
        lvInventory.setAdapter(mCursorAdapter);

        lvInventory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, InventoryDetailActivity.class);
                Uri currentItemUri = ContentUris.withAppendedId(InventoryEvent.CONTENT_URI, id);
                intent.setData(currentItemUri);
                startActivity(intent);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InventoryDetailActivity.class);
                startActivity(intent);
            }
        });
        getLoaderManager().initLoader(INVENTORY_LOADER, null, MainActivity.this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String projection[] = {
                InventoryEvent._ID,
                InventoryEvent.COLUMN_INVENTORY_IMAGE,
                InventoryEvent.COLUMN_INVENTORY_NAME,
                InventoryEvent.COLUMN_INVENTORY_QUANTITY,
                InventoryEvent.COLUMN_INVENTORY_PRICE,
                InventoryEvent.COLUMN_INVENTORY_SUPPLIER
        };

        return new CursorLoader(this,
                InventoryEvent.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }

    @Override
    public void onBtnClick(int id, int quantity) {
        Log.v("TAG", "onBtnClick()");

        int quantityInt = quantity - 1;

        Uri currentUri = ContentUris.withAppendedId(InventoryEvent.CONTENT_URI, id);
        if (quantityInt < 0) {
            Toast.makeText(this, R.string.quantity_negative,
                    Toast.LENGTH_SHORT).show();
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(InventoryEvent.COLUMN_INVENTORY_QUANTITY, quantityInt);

            int rowsAffected = getContentResolver().update(currentUri, contentValues, null, null);
            if (rowsAffected == 0) {
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(this, R.string.update_failed,
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.update_successful,
                        Toast.LENGTH_SHORT).show();
            }

        }
    }
}
