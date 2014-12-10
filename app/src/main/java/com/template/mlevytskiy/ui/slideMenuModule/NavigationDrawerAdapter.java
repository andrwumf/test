package com.template.mlevytskiy.ui.slideMenuModule;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.template.mlevytskiy.R;
import com.template.mlevytskiy.util.UnitUtils;

/**
 * Created by Макс on 16.04.2014.
 */
public class NavigationDrawerAdapter extends ArrayAdapter<String> {

    private int mSelectedPosition = 0;

    public NavigationDrawerAdapter(Context context, int selected) {
        this(context);
        mSelectedPosition = selected;
    }


    public NavigationDrawerAdapter(Context context) {
        super(context, R.layout.item_navigation,
                android.R.id.text1, getMenuItems(context)
                );
    }

    private static String[] getMenuItems(Context context) {
        String[] differentLanguagesStrs = context.getResources().getStringArray(R.array.rivers);
        String[] strs = new String[differentLanguagesStrs.length + 1];
        for (int i = 0; i < differentLanguagesStrs.length; i++) {
            strs[i] = differentLanguagesStrs[i];
        }
        strs[differentLanguagesStrs.length] = "Irregular verbs stories  ;)";
        return strs;
    }

    public int getSelectedPosition() {
        return mSelectedPosition;
    }

    public void setSelectedPosition(int position) {
        mSelectedPosition = position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
//        View textView = view.findViewById(android.R.id.text1);
        view.setTag(position);
        if (mSelectedPosition == position) {
            view.setBackgroundResource(R.drawable.apptheme_list_focused_holo);
        } else {
            view.setBackgroundResource(R.drawable.apptheme_list_selector_holo_dark);
        }
        int px = (int) UnitUtils.convertPixelsToDp(16, parent.getContext());
        view.setPadding(px, 0, px, 0);
        return view;
    }
}
