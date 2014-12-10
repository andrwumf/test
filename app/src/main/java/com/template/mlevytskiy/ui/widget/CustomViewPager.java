package com.template.mlevytskiy.ui.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Макс on 18.07.2014.
 */
public class CustomViewPager extends ViewPager {

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent event) {
////        int current = this.getCurrentItem();
////        int count = this.getChildCount();
////        if (current < count-1) {
////            if (this.getChildAt(current+1).getAlpha() == 1) {
////                return true;
////            } else {
////                //doNothing
////            }
////        } else {
////            //doNothing;
////        }
//        return false;
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        // Never allow swiping to switch between pages
//        return false;
//    }

}
