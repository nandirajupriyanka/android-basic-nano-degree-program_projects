package com.example.priyankanandiraju.inventory;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.priyankanandiraju.inventory.data.InventoryContract.InventoryEvent;

import static com.example.priyankanandiraju.inventory.InventoryDetailActivity.IMAGE_DATA;

/**
 * Created by priyankanandiraju on 1/19/17.
 */

public class InventoryAdapter extends CursorAdapter {

    BtnClickListener btnClickListener;

    public InventoryAdapter(Context context, Cursor c, BtnClickListener clickListener) {
        super(context, c, 0);
        btnClickListener = clickListener;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_inventory, parent, false);
    }

    @Override
    public void bindView(final View view, Context context, final Cursor cursor) {

        ImageView imageView = (ImageView) view.findViewById(R.id.iv_item);
        TextView tvName = (TextView) view.findViewById(R.id.tv_name);
        TextView tvQuantity = (TextView) view.findViewById(R.id.tv_quantity);
        TextView tvPrice = (TextView) view.findViewById(R.id.tv_price);
        TextView tvSupplier = (TextView) view.findViewById(R.id.tv_supplier);
        final Button btnTrack = (Button) view.findViewById(R.id.btn_track);

        final int itemId = cursor.getInt(cursor.getColumnIndex(InventoryEvent._ID));
        String name = cursor.getString(cursor.getColumnIndex(InventoryEvent.COLUMN_INVENTORY_NAME));
        final String quantity = cursor.getString(cursor.getColumnIndex(InventoryEvent.COLUMN_INVENTORY_QUANTITY));
        String price = cursor.getString(cursor.getColumnIndex(InventoryEvent.COLUMN_INVENTORY_PRICE));
        String supplier = cursor.getString(cursor.getColumnIndex(InventoryEvent.COLUMN_INVENTORY_SUPPLIER));

        SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(context);
        String previouslyEncodedImage = shre.getString(IMAGE_DATA + name, "");
        if (!previouslyEncodedImage.equalsIgnoreCase("")) {
            byte[] b = Base64.decode(previouslyEncodedImage, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageBitmap(null);
        }

        btnTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnClickListener != null)
                    btnClickListener.onBtnClick(itemId, Integer.parseInt(quantity));
            }
        });

        tvName.setText(name);
        tvQuantity.setText(quantity);
        tvPrice.setText(price);
        tvSupplier.setText(supplier);
    }

    public interface BtnClickListener {
        void onBtnClick(int id, int quantity);
    }
}
