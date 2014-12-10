package com.template.mlevytskiy.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by Макс on 05.04.14.
 */
public class HelpAnimationFrameLayout extends FrameLayout {

    public HelpAnimationFrameLayout(Context context) {
        super(context);
    }

    public HelpAnimationFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HelpAnimationFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public float getXFraction() {
        return getX() / getWidth();
    }

    public void setXFraction(float xFraction) {
        final int width = getWidth();
        setX((width > 0) ? (xFraction * width) : -9999);
    }
}
