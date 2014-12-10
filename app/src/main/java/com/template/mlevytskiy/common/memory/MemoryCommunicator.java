package com.template.mlevytskiy.common.memory;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.template.mlevytskiy.App;

/**
 * Created by Макс on 24.04.2014.
 */
public class MemoryCommunicator {

    private final String STORE_KEY = "memory";
    private final SharedPreferences sharedPreferences;
    private final EnumMemoryKey[] enumMemoryKeyArray;

    public MemoryCommunicator(EnumMemoryKey... enumMemoryKey) {
        sharedPreferences = App.instance.getSharedPreferences(STORE_KEY, Context.MODE_PRIVATE);
        this.enumMemoryKeyArray = enumMemoryKey;
    }

    public void saveString(String str) {
        if (enumMemoryKeyArray.length != 1) {
            throw new IllegalArgumentException("use saveString(String str, EnumMemoryKey key)");
        }
        saveString(str, enumMemoryKeyArray[0]);
    }

    public void saveString(String str, EnumMemoryKey enumMemoryKey) {
        sharedPreferences.edit().putString(enumMemoryKey.toString(), str).commit();
    }

    public String loadString(EnumMemoryKey enumMemoryKey) {
        return sharedPreferences.getString(enumMemoryKey.toString(), "");
    }

    public void saveBoolean(boolean bool, EnumMemoryKey enumMemoryKey) {
        sharedPreferences.edit().putBoolean(enumMemoryKey.toString(), bool).commit();
    }

    public boolean loadBoolean(EnumMemoryKey enumMemoryKey) {
        return loadBoolean(enumMemoryKey, false);
    }

    public boolean loadBoolean(EnumMemoryKey enumMemoryKey, boolean defaultValue) {
        return sharedPreferences.getBoolean(enumMemoryKey.toString(), defaultValue);
    }

    public int loadInt(EnumMemoryKey enumMemoryKey, int defaultValue) {
        return sharedPreferences.getInt(enumMemoryKey.toString(), defaultValue);
    }

    public void saveInt(EnumMemoryKey enumMemoryKey, int value) {
        sharedPreferences.edit().putInt(enumMemoryKey.toString(), value);
    }

    public String loadString() {
        if (enumMemoryKeyArray.length != 1) {
            throw new IllegalArgumentException("use loadString(EnumMemoryKey key)");
        }
        String result = loadString(enumMemoryKeyArray[0]);
        if (TextUtils.isEmpty(result)) {
            throw new MemoryIsEmptyIllegalArgumentException();
        }
        return result;
    }

    public boolean isEmpty() {
        boolean isEmpty = true;
        for (EnumMemoryKey enumMemoryKey : enumMemoryKeyArray) {
            isEmpty = !sharedPreferences.contains(enumMemoryKey.toString());
            if (isEmpty) {
                break;
            }
        }
        return isEmpty;
    }

}
