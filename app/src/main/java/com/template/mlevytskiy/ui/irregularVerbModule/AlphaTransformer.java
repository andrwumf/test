package com.template.mlevytskiy.ui.irregularVerbModule;

import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import com.nineoldandroids.view.ViewHelper;
import com.template.mlevytskiy.R;

/**
 * Created by Макс on 18.07.2014.
 */
public class AlphaTransformer implements ViewPager.PageTransformer {

    private static final float MIN_ALPHA = 0.4f;
    private static final float MIN_ALPHA_2 = 0.6f;
    private ViewPager viewPager;
    private float startX = 0;
    private float endX = 0;

    public AlphaTransformer(final ViewPager viewPager) {
        this.viewPager = viewPager;
        viewPager.setOnTouchListener(new View.OnTouchListener() {
                                         @Override
                                         public boolean onTouch(View v, MotionEvent event) {
                                             if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                                 startX = event.getX();
                                             } else if (event.getAction() == MotionEvent.ACTION_UP) {
                                                 endX = event.getX();

                                                 if (Math.abs(endX - startX) < 120) {
                                                     return false;
                                                 } else if ((endX - startX) < 0) {
                                                     swipeToNextPage();
                                                     return true;
                                                 } else {
                                                     swipeToPrePage();
                                                     return true;
                                                 }
                                             }
                                             return false;
                                         }
                                     }
        );
    }

    public void transformPage(View view, float position) {
        if (position < -1) {
            ViewHelper.setAlpha(view, 0);
        } else if (position <= 1) { // [-1,1]
            if (position < 0) {
                if (position < -0.9) {
                    View view2 = view.findViewById(R.id.volume_image_button);
                    if (view2 != null) {
                        ViewHelper.setAlpha(view2, MIN_ALPHA);
                    }
                    ViewHelper.setAlpha(view.findViewById(R.id.translate), MIN_ALPHA);
                    ViewHelper.setAlpha(view, MIN_ALPHA_2);
                } else {
                    View view2 = view.findViewById(R.id.volume_image_button);
                    if (view2 != null) {
                        ViewHelper.setAlpha(view2, 1);
                    }
                    ViewHelper.setAlpha(view.findViewById(R.id.translate), 1);
                    ViewHelper.setAlpha(view, 1);
                }
            } else {
                if (position > 0.9) {
                    ViewHelper.setAlpha(view, MIN_ALPHA);
                } else {
                    ViewHelper.setAlpha(view, 1);
                }
            }
        } else {
            ViewHelper.setAlpha(view,0);
        }
    }

    private void swipeToNextPage() {
        viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
    }

    private void swipeToPrePage() {
        viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
    }
}
