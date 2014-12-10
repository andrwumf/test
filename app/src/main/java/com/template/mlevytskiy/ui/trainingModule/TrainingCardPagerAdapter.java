package com.template.mlevytskiy.ui.trainingModule;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.template.mlevytskiy.R;
import com.template.mlevytskiy.util.FlipStateAnimator;
import com.template.mlevytskiy.util.SpannableUtils;
import com.template.mlevytskiy.vo.IrregularVerb;
import com.template.mlevytskiy.vo.SameIrregularVerb;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Макс on 05.04.14.
 */
public class TrainingCardPagerAdapter extends PagerAdapter {

    private List<IrregularVerb> mList;
    private SparseArray<View> views = new SparseArray<View>();
    private boolean mIsFaceState = true;
    private MediaPlayer mPlayer = new MediaPlayer();

    public TrainingCardPagerAdapter(List<IrregularVerb> list, boolean isFaceState) {
        mList = list;
        mIsFaceState = isFaceState;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, final int i) {
        final View view = View.inflate(collection.getContext(), R.layout.view_pager_item_easy_training, null);
        initFlipAnimation(view);

        TextView form1 = (TextView) view.findViewById(R.id.form1);
        TextView form2 = (TextView) view.findViewById(R.id.form2);
        TextView form3 = (TextView) view.findViewById(R.id.form3);
        TextView translate = (TextView) view.findViewById(R.id.translate);

        ImageView imageView = (ImageView) view.findViewById(R.id.image_view);
        imageView.setImageDrawable(collection.getResources().getDrawable(mList.get(i).getDrawableId()));

        ImageButton mp3 = (ImageButton) view.findViewById(R.id.volume_image_button);

        initSameIrregularVerbs(view, mList.get(i), form1, form2, form3, translate, mp3);

        ImageButton volume = (ImageButton) view.findViewById(R.id.volume_image_button);
        volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play(mList.get(i).getMp3Raw(), v.getContext());
            }
        });

        collection.addView(view, 0);
        views.put(i, view);
        return view;
    }

    private void initSameIrregularVerbs(View view, IrregularVerb irregularVerb, TextView form1,
                                        TextView form2, TextView form3, TextView translate,
                                        ImageButton mp3) {
        CheckBox[] checkBoxes = new CheckBox[6];
        checkBoxes[0] = (CheckBox) view.findViewById(R.id.check_box_1);
        checkBoxes[1] = (CheckBox) view.findViewById(R.id.check_box_2);
        checkBoxes[2] = (CheckBox) view.findViewById(R.id.check_box_3);
        checkBoxes[3] = (CheckBox) view.findViewById(R.id.check_box_4);
        checkBoxes[4] = (CheckBox) view.findViewById(R.id.check_box_5);
        checkBoxes[5] = (CheckBox) view.findViewById(R.id.check_box_6);

        initText(checkBoxes, irregularVerb, form1, form2, form3, translate, mp3);

        CompoundButton.OnCheckedChangeListener listener = new CustomOnCheckedChangeListener(checkBoxes,
                irregularVerb, form1, form2, form3, translate, mp3);

        for (int i = 0; i < irregularVerb.getSameIrregularVerbs().size(); i++) {
            if (i == 6) {
                break;
            }
            SameIrregularVerb verb = irregularVerb.getSameIrregularVerbs().get(i);
            checkBoxes[i].setText(verb.getPrefix());
            checkBoxes[i].setVisibility(View.VISIBLE);
            checkBoxes[i].setOnCheckedChangeListener(listener);
        }
    }

    private static class CustomOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {

        private CheckBox[] checkBoxes;
        private IrregularVerb irregularVerb;
        private TextView form1;
        private TextView form2;
        private TextView form3;
        private TextView translate;
        private ImageView mp3;

        public CustomOnCheckedChangeListener(CheckBox[] checkBoxes, IrregularVerb irregularVerb,
                                             TextView form1, TextView form2, TextView form3,
                                             TextView translate, ImageView mp3) {
            this.checkBoxes = checkBoxes;
            this.irregularVerb = irregularVerb;
            this.form1 = form1;
            this.form2 = form2;
            this.form3 = form3;
            this.translate = translate;
            this.mp3 = mp3;
        }


        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                for (CheckBox checkBox : checkBoxes) {
                    if ( !TextUtils.equals(checkBox.getText(), buttonView.getText())) {
                        checkBox.setChecked(false);
                    }
                }
            }
            initText(checkBoxes, irregularVerb, form1, form2, form3, translate, mp3);
        }
    }

    private static void initText(CheckBox[] checkBoxes, IrregularVerb irregularVerb,
                                 TextView form1, TextView form2, TextView form3,
                                 TextView translate, ImageView mp3) {
        int checkedIndex = -1;
        for (int i = 0; i < checkBoxes.length; i++) {
            if (checkBoxes[i].isChecked()) {
                checkedIndex = i;
            }
        }
        if (checkedIndex == -1) {
            mp3.setVisibility(View.VISIBLE);
            form1.setText(TextUtils.join(", ", irregularVerb.getForm1()));
            form2.setText(TextUtils.join(", ", irregularVerb.getForm2()));
            form3.setText(TextUtils.join(", ", irregularVerb.getForm3()));
            translate.setText(TextUtils.join(", ", irregularVerb.getTranslate()));
        } else {
            mp3.setVisibility(View.INVISIBLE);
            SameIrregularVerb sameIrregularVerb = irregularVerb.getSameIrregularVerbs().get(checkedIndex);
            form1.setText(SpannableUtils.join(", ", sameIrregularVerb.getForm1()));
            form2.setText(SpannableUtils.join(", ", sameIrregularVerb.getForm2()));
            form3.setText(SpannableUtils.join(", ", sameIrregularVerb.getForm3()));
            translate.setText(TextUtils.join(", ", sameIrregularVerb.getTranslate()));
        }
    }

    public int getItemPosition(Object object) {
        return POSITION_UNCHANGED;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
        views.remove(position);
    }

    private View getViewByPosition(int position) {
        return views.get(position);
    }

    public void setCardFaceState(int currentItem) {
        mIsFaceState = true;
        View view = getViewByPosition(currentItem);
        FlipStateAnimator flipStateAnimator = new FlipStateAnimator(view.findViewById(R.id.main_activity_root));
        flipStateAnimator.setCardFaceStateWithAnimation();

        List<View> list = getOtherViews(currentItem);
        for (View item : list) {
            FlipStateAnimator flipStateAnimator1 = new FlipStateAnimator(item.findViewById(R.id.main_activity_root));
            flipStateAnimator1.setCardFaceStateWithoutAnimation();
        }

    }

    public void setCardBackState(int currentItem) {
        mIsFaceState = false;
        View view = getViewByPosition(currentItem);
        FlipStateAnimator flipStateAnimator = new FlipStateAnimator(view.findViewById(R.id.main_activity_root));
        flipStateAnimator.setCardBackStateWithAnimation();

        List<View> list = getOtherViews(currentItem);
        for (View item : list) {
            FlipStateAnimator flipStateAnimator1 = new FlipStateAnimator(item.findViewById(R.id.main_activity_root));
            flipStateAnimator1.setCardBackStateWithoutAnimation();
        }
    }

    private List<View> getOtherViews(int position) {
        List<View> result = new ArrayList<View>();
        for (int i = position-1; ; i--) {
            View beforeCurrentPosition = views.get(i);
            if (beforeCurrentPosition == null) {
                break;
            } else {
                result.add(beforeCurrentPosition);
            }
        }

        for (int i = position+1; ; i++) {
            View afterCurrentPosition = views.get(i);
            if (afterCurrentPosition == null) {
                break;
            } else {
                result.add(afterCurrentPosition);
            }
        }

        return result;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    private void initFlipAnimation(View view) {
        new FlipStateAnimator(view.findViewById(R.id.main_activity_root), mIsFaceState).initOnClickListeners();
    }

    private void play(Uri rawMp3, Context context) {
        mPlayer.reset();
        try {
            try {
                mPlayer.setDataSource(context, rawMp3);
            } catch (IllegalStateException e) {
                mPlayer.reset();
                mPlayer.setDataSource(context, rawMp3);
            }
            mPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mPlayer.start();
    }

}
