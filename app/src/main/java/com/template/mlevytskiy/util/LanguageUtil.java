package com.template.mlevytskiy.util;

import android.text.TextUtils;
import com.template.mlevytskiy.memory.Language;

/**
 * Created by Макс on 29.04.2014.
 */
public class LanguageUtil {

    public static String getStrId(int position) {
        for (Language current : Language.values()) {
            if (current.ordinal() == position) {
                return current.toString();
            }
        }
        return Language.ru.toString();
    };

    public static int getPosition(String strId) {
        for (Language current : Language.values()) {
            if (TextUtils.equals(current.toString(), strId)) {
                return current.ordinal();
            }
        }
        return Language.ru.ordinal();
    }
}
