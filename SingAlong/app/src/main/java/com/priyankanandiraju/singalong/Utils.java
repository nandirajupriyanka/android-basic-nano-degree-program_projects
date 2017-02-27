package com.priyankanandiraju.singalong;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

/**
 * Created by priyankanandiraju on 10/13/16.
 */

public class Utils {

    public static void openWebPage(Context context, @NonNull String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }
}
