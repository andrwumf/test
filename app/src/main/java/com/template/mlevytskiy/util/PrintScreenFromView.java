package com.template.mlevytskiy.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

/**
 * Created by Макс on 08.05.2014.
 */
public class PrintScreenFromView {

    public static Bitmap bitmap(View view) {
        int width = view.getWidth();
        int height = view.getHeight();
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(result);
        view.layout(0, 0, width, height);
        view.draw(c);
        return result;
    }
}
