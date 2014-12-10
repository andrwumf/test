package com.template.mlevytskiy.util;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;

import com.template.mlevytskiy.R;

import java.lang.reflect.Field;

/**
 * Created by Макс on 05.04.14.
 */
public class ResourceUtils {

    private static final int EMPTY_RESOURCE = -1;
    private final String CONSTANTS_RES_PREFIX;
    private Resources mResources;

    public ResourceUtils(Context context) {
        mResources = context.getResources();
        CONSTANTS_RES_PREFIX = "android.resource://" + context.getApplicationContext().getPackageName() + "/";
    }

    public Uri getRaw(String name) {
        int id = getRawId(name);
        if (id != EMPTY_RESOURCE) {
            return Uri.parse(CONSTANTS_RES_PREFIX + id);
        } else {
            return null;
        }
    }

    public int getRandomDrawableId() {
        int randomInt = (int) (Math.random() * 29);
        String randomDrawableName = "a" + randomInt;
        return getDrawableId(randomDrawableName);
    }

    public int getRawId(String name) {
        Class res = R.raw.class;
        return getId(res, name);
    }

    public String getString(String name) {
        int id = getStringId(name);
        if (id != EMPTY_RESOURCE) {
            return mResources.getString(id);
        } else {
            return null;
        }
    }

    private int getStringId(String name) { //faster than getIdentifier
        Class res = R.string.class;
        return getId(res, name);
    }

    private int getDrawableId(String name) {
        Class res = R.drawable.class;
        return getId(res, name);
    }

    private int getId(Class res, String name) {
        try {
            Field field = res.getField(name);
            int id = field.getInt(null);
            return id;
        } catch (Exception e) {
            return EMPTY_RESOURCE;
        }
    }

}
