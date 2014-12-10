package com.template.mlevytskiy.util;

import android.view.View;

import com.template.mlevytskiy.R;
import com.template.mlevytskiy.ui.widget.FlipAnimation;

/**
 * Created by Макс on 06.04.2014.
 */
public class FlipStateAnimator {

    private View mView;
    private View mCardFace;
    private View mCardBack;

    public FlipStateAnimator(View view, int cardFaceId, int cardBackId) {
        mView = view;
        mCardFace = (View) view.findViewById(cardFaceId);
        mCardBack = (View) view.findViewById(cardBackId);
    }

    public FlipStateAnimator(View view) {
        this(view, R.id.main_activity_card_face, R.id.main_activity_card_back);
    }

    public FlipStateAnimator(View view, boolean isCardFaceState) {
        this(view);
        if (isCardFaceState) {
            mCardFace.setVisibility(View.VISIBLE);
            mCardBack.setVisibility(View.GONE);
        } else {
            mCardFace.setVisibility(View.GONE);
            mCardBack.setVisibility(View.VISIBLE);
        }
    }

    public void initOnClickListeners() {
        mCardFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStateWithAnimation();
            }
        });
        mCardBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStateWithAnimation();
            }
        });
    }

    public boolean isCardFaceState() {
        return mCardFace.getVisibility() == View.VISIBLE;
    }

    public void changeStateWithAnimation() {
        flipCard(mView, mCardFace, mCardBack);
    }

    public void setCardFaceStateWithAnimation() {
        if (!isCardFaceState()) {
            changeStateWithAnimation();
        }
    }

    public void setCardFaceStateWithoutAnimation() {
        if (!isCardFaceState()) {
            changeStateWithoutAnimation();
        }
    }

    public void setCardBackStateWithAnimation() {
        if (isCardFaceState()) {
            changeStateWithAnimation();
        }
    }

    public void setCardBackStateWithoutAnimation() {
        if (isCardFaceState()) {
            changeStateWithoutAnimation();
        }
    }

    private void changeStateWithoutAnimation() {
        if (isCardFaceState()) {
            mCardFace.setVisibility(View.GONE);
            mCardBack.setVisibility(View.VISIBLE);
        } else {
            mCardFace.setVisibility(View.VISIBLE);
            mCardBack.setVisibility(View.GONE);
        }
    }

    private void flipCard(View rootView, View cardFace, View cardBack) {
        View rootLayout = mView;//(View) rootView.findViewById(R.id.main_activity_root);
        FlipAnimation flipAnimation = new FlipAnimation(cardFace, cardBack);

        if (cardFace.getVisibility() == View.GONE) {
            flipAnimation.reverse();
        }
        rootLayout.startAnimation(flipAnimation);
    }
}
