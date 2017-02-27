package com.example.priyankanandiraju.inventory;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.priyankanandiraju.inventory.data.InventoryContract.InventoryEvent;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class InventoryDetailActivity extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    public static final String IMAGE_DATA = "image_data ";
    private static final String TAG = InventoryDetailActivity.class.getSimpleName();
    private ImageView imageView;
    private LinearLayout llButtons;
    private ImageView ibImage;
    private EditText etName;
    private EditText etQuantity;
    private EditText etPrice;
    private EditText etSupplier;
    private Button btnDelete;
    private Button btnOrder;
    private Button btnUpdate;
    private Button btnAdd;
    private Uri mCurrentUri;
    private boolean mItemChanged = false;
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mItemChanged = true;
            return false;
        }
    };
    private SharedPreferences mSharedPref;
    private Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_detail);

        Intent intent = getIntent();
        mCurrentUri = intent.getData();

        if (mCurrentUri == null) {
            setTitle(getString(R.string.add_inventory_item));
        } else {
            setTitle(R.string.edit_inventory_item);
            getLoaderManager().initLoader(0, null, this);
        }

        imageView = (ImageView) findViewById(R.id.iv_item_image);
        llButtons = (LinearLayout) findViewById(R.id.ll_buttons);
        ibImage = (ImageView) findViewById(R.id.iv_camera);
        etName = (EditText) findViewById(R.id.edit_name);
        etQuantity = (EditText) findViewById(R.id.edit_quantity);
        etPrice = (EditText) findViewById(R.id.edit_price);
        etSupplier = (EditText) findViewById(R.id.edit_supplier);

        btnDelete = (Button) findViewById(R.id.btn_delete);
        btnOrder = (Button) findViewById(R.id.btn_order);
        btnUpdate = (Button) findViewById(R.id.btn_update);
        btnAdd = (Button) findViewById(R.id.btn_add_item);

        btnOrder.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        ibImage.setOnClickListener(this);

        etName.setOnTouchListener(mTouchListener);
        etQuantity.setOnTouchListener(mTouchListener);
        etPrice.setOnTouchListener(mTouchListener);
        etSupplier.setOnTouchListener(mTouchListener);

        setButtonsVisibility();

    }

    private void setImage() {
        if (mCurrentUri != null) {
            String nameString = etName.getText().toString().trim();
            SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(this);
            String previouslyEncodedImage = shre.getString(IMAGE_DATA + nameString, "");
            if (!previouslyEncodedImage.equalsIgnoreCase("")) {
                byte[] b = Base64.decode(previouslyEncodedImage, Base64.DEFAULT);
                Bitmap bm = BitmapFactory.decodeByteArray(b, 0, b.length);
                bitmap = bm;
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    private void setButtonsVisibility() {
        if (mCurrentUri == null) {
            llButtons.setVisibility(View.GONE);
            btnAdd.setVisibility(View.VISIBLE);
        } else {
            llButtons.setVisibility(View.VISIBLE);
            btnAdd.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_delete:
                showDeleteConfirmationDialog();
                break;
            case R.id.btn_order:
                orderItem();
                break;
            case R.id.btn_update:
                saveInventory();
                finish();
                break;
            case R.id.btn_add_item:
                saveInventory();
                finish();
                break;
            case R.id.iv_camera:
                uploadImage();
                break;
            default:
                break;
        }
    }

    private void orderItem() {
        Log.v(TAG, "orderItem()");

        if (!validateItem()) {
            return;
        }

        String nameString = etName.getText().toString().trim();
        String quantityString = etQuantity.getText().toString().trim();
        String priceString = etPrice.getText().toString().trim();
        String supplierString = etSupplier.getText().toString().trim();

        String body = getString(R.string.name) + " " + nameString +
                "\n" + getString(R.string.quantity) + " " + quantityString +
                "\n" + getString(R.string.price) + " " + priceString +
                "\n" + getString(R.string.supplier) + " " + supplierString;

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setData(Uri.parse("mailto:"));
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"inventory@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_inventory));
        i.putExtra(Intent.EXTRA_TEXT, body);
        if (i.resolveActivity(getPackageManager()) != null) {
            startActivity(i);
        } else {
            Toast.makeText(this, R.string.email_not_setup, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateItem() {
        String nameString = etName.getText().toString().trim();
        String quantityString = etQuantity.getText().toString().trim();
        String priceString = etPrice.getText().toString().trim();
        String supplierString = etSupplier.getText().toString().trim();

        if (bitmap == null) {
            Toast.makeText(this, R.string.image_empty, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(nameString)) {
            Toast.makeText(this, R.string.name_empty, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(quantityString)) {
            Toast.makeText(this, R.string.quantity_empty, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(priceString)) {
            Toast.makeText(this, R.string.price_empty, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(supplierString)) {
            Toast.makeText(this, R.string.supplier_empty, Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 101 && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                bitmap = getBitmapFromUri(selectedImage);
                imageView.setImageBitmap(bitmap);
            } else {
                Toast.makeText(this, R.string.havent_picked_image,
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, R.string.something_went_wrong, Toast.LENGTH_LONG)
                    .show();
        }
    }

    private Bitmap saveImageBitmap(Bitmap thumbnail, String nameString) {
        // Removing image saved earlier in shared preferences
        mSharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = mSharedPref.edit();
        edit.remove(IMAGE_DATA + nameString);
        edit.commit();


        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] b = bytes.toByteArray();
        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        //saving image to shared preferences
        edit.putString(IMAGE_DATA + nameString, encodedImage);
        edit.commit();
        return thumbnail;
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    private void uploadImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)), 101);
    }

    private void saveInventory() {
        if (!validateItem()) {
            return;
        }

        String nameString = etName.getText().toString().trim();
        String quantityString = etQuantity.getText().toString().trim();
        String priceString = etPrice.getText().toString().trim();
        String supplierString = etSupplier.getText().toString().trim();
        int quantityInt = 0;
        int priceInt = 0;

        quantityInt = Integer.parseInt(quantityString);
        priceInt = Integer.parseInt(priceString);

        ContentValues contentValues = new ContentValues();
        contentValues.put(InventoryEvent.COLUMN_INVENTORY_IMAGE, R.mipmap.ic_launcher);
        contentValues.put(InventoryEvent.COLUMN_INVENTORY_NAME, nameString);
        contentValues.put(InventoryEvent.COLUMN_INVENTORY_QUANTITY, quantityInt);
        contentValues.put(InventoryEvent.COLUMN_INVENTORY_PRICE, priceInt);
        contentValues.put(InventoryEvent.COLUMN_INVENTORY_SUPPLIER, supplierString);

        if (mCurrentUri == null) {
            Uri newUri = getContentResolver().insert(InventoryEvent.CONTENT_URI, contentValues);
            if (newUri == null) {
                Toast.makeText(this, R.string.insert_inventory_failed, Toast.LENGTH_SHORT).show();
            } else {
                if (bitmap != null) {
                    saveImageBitmap(bitmap, nameString);
                }
                Toast.makeText(this, R.string.insert_inventory_successful, Toast.LENGTH_SHORT).show();
            }
        } else {
            int rowsAffected = getContentResolver().update(mCurrentUri, contentValues, null, null);
            if (rowsAffected == 0) {
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(this, R.string.update_failed,
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast.
                saveImageBitmap(bitmap, nameString);
                Toast.makeText(this, R.string.update_successful,
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_this_item);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteItem();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteItem() {
        if (mCurrentUri != null) {
            int rowsDeleted = getContentResolver().delete(mCurrentUri, null, null);
            if (rowsDeleted == 0) {
                Toast.makeText(this, R.string.delete_failed, Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the delete was successful and we can display a toast.
                Toast.makeText(this, R.string.delete_successful, Toast.LENGTH_SHORT).show();
            }
        }
        // Close the activity
        finish();
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
                mCurrentUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }
        if (cursor.moveToFirst()) {
            int nameColumnIndex = cursor.getColumnIndex(InventoryEvent.COLUMN_INVENTORY_NAME);
            int quantityColumnIndex = cursor.getColumnIndex(InventoryEvent.COLUMN_INVENTORY_QUANTITY);
            int priceColumnIndex = cursor.getColumnIndex(InventoryEvent.COLUMN_INVENTORY_PRICE);
            int supplierColumnIndex = cursor.getColumnIndex(InventoryEvent.COLUMN_INVENTORY_SUPPLIER);

            // Extract out the value from the Cursor for the given column index
            String name = cursor.getString(nameColumnIndex);
            int quantity = cursor.getInt(quantityColumnIndex);
            int price = cursor.getInt(priceColumnIndex);
            String supplier = cursor.getString(supplierColumnIndex);

            // Update the views on the screen with the values from the database
            etName.setText(name);
            etQuantity.setText(Integer.toString(quantity));
            etPrice.setText(Integer.toString(price));
            etSupplier.setText(supplier);

            setImage();

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        etName.setText("");
        etQuantity.setText("");
        etPrice.setText("");
        etSupplier.setText("");
        bitmap = null;
    }
}
