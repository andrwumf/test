package com.template.mlevytskiy.ui.widget;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by Макс on 02.08.2014.
 */
public abstract class CustomTextChangeListener implements TextWatcher {

    private String oldText;

    public abstract void change(String oldText, Editable newText);

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        oldText = s.toString();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        change(oldText, s);
    }
}
