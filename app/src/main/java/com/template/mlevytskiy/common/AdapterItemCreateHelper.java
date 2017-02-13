package com.template.mlevytskiy.common;

import android.view.View;

/**
 * Created by Макс on 21.04.2014.
 * 
 */
public abstract class AdapterItemCreateHelper<T> {

    protected T data;

    public AdapterItemCreateHelper(T data) {
        this.data = data;
    }

    public abstract View fill(View view, View parent);
    protected abstract View create(View parent);
}
