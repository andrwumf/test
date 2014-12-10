package com.template.mlevytskiy.util;

import android.text.Spannable;
import android.text.SpannableStringBuilder;

/**
 * Created by Макс on 23.04.2014.
 */
public class SpannableUtils {

    public static Spannable join(String delimiter, Spannable[] array) {
        if (array.length == 1) {
            return array[0];
        } else if (array.length < 1) {
            return null;
        } else {
            SpannableStringBuilder spannable = new SpannableStringBuilder();
            for (int i = 0; i < array.length; i++) {
                spannable.append(array[i]);
                if (i != (array.length-1)) {
                    spannable.append(delimiter);
                }
            }
            return spannable;
        }
    }
}
