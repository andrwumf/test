package com.template.mlevytskiy.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import com.template.mlevytskiy.R;

/**
 * Created by Макс on 18.07.2014.
 * xmlns:app="http://schemas.android.com/apk/res-auto"
 */
public class PopularWordView extends View {

    public final static int CRITERIA_NORMAL = 0;
    public final static int CRITERIA_MORE = 1;
    public final static int CRITERIA_LESS = 2;

    public PopularWordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.popular_word_view);
        int value = a.getInt(R.styleable.popular_word_view_criteria, -1);
        setCriteria(value);
        a.recycle();
    }

    public void setCriteria(int value) {
        if (value != -1) {
            if (value == CRITERIA_NORMAL) {
                this.setBackgroundColor(getResources().getColor(R.color.popular_normal));
            } else if (value == CRITERIA_MORE) {
                this.setBackgroundColor(getResources().getColor(R.color.popular_more));
            } else if (value == CRITERIA_LESS) {
                this.setBackgroundColor(getResources().getColor(R.color.popular_less));
            }
        }
    }


}
