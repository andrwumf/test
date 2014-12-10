package com.template.mlevytskiy.ui.irregularVerbModule;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.template.mlevytskiy.App;
import com.template.mlevytskiy.R;
import com.template.mlevytskiy.common.AdapterItemCreateHelper;
import com.template.mlevytskiy.ui.widget.PopularWordView;
import com.template.mlevytskiy.util.search.ContainsInIrregularVerbDetail;
import com.template.mlevytskiy.util.search.Detail;
import com.template.mlevytskiy.vo.IrregularVerb;
import com.template.mlevytskiy.vo.MorePopularSetting;

/**
 * Created by Макс on 21.04.2014.
 */
public class IrregularVerbAdapterItemCreateHelper extends AdapterItemCreateHelper<IrregularVerb> {

    private int layoutId = R.layout.item_irregular_verbs;
    private MorePopularSetting morePopularSetting;

    public IrregularVerbAdapterItemCreateHelper(IrregularVerb data, MorePopularSetting morePopularSetting) {
        super(data);
        this.morePopularSetting = morePopularSetting;
    }

    public IrregularVerbAdapterItemCreateHelper(IrregularVerb data, int layoutId, MorePopularSetting morePopularSetting) {
        this(data, morePopularSetting);
        this.layoutId = layoutId;
    }

    @Override
    public View fill(View view, View parent) {
        if (view == null) {
            view = create(parent);
        } else {
            ViewHolder viewHolder = (ViewHolder) view.getTag();
            fill(data, viewHolder);
        }
        return view;
    }

    private void fill(IrregularVerb irregularVerb, ViewHolder viewHolder) {
        viewHolder.form1.setText(TextUtils.join(", ", irregularVerb.getForm1()));
        viewHolder.form2.setText(TextUtils.join(", ", irregularVerb.getForm2()));
        viewHolder.form3.setText(TextUtils.join(", ", irregularVerb.getForm3()));
        viewHolder.translate.setText(TextUtils.join(", ", irregularVerb.getTranslate()));
        viewHolder.mp3.setTag(irregularVerb.getMp3Raw());

        if (irregularVerb.getLastDetail() != null) {
            if (irregularVerb.getLastDetail().isEmpty) {
                //doNothing
            } else if (irregularVerb.getLastDetail().detail == Detail.FORM1) {
                fillText(viewHolder.form1, irregularVerb.getForm1(), irregularVerb.getLastDetail());
            } else if (irregularVerb.getLastDetail().detail == Detail.FORM2) {
                fillText(viewHolder.form2, irregularVerb.getForm2(), irregularVerb.getLastDetail());
            } else if (irregularVerb.getLastDetail().detail == Detail.FORM3) {
                fillText(viewHolder.form3, irregularVerb.getForm3(), irregularVerb.getLastDetail());
            } else if (irregularVerb.getLastDetail().detail == Detail.TRANSLATE) {
                fillText(viewHolder.translate, irregularVerb.getTranslate(), irregularVerb.getLastDetail());
            }
        }

        //set more popular
        if ((morePopularSetting.morePopularWordAmount == 20) && irregularVerb.isMorePopular20()) {
            viewHolder.popularWord.setCriteria(PopularWordView.CRITERIA_MORE);
        } else if ((morePopularSetting.morePopularWordAmount == 50) && irregularVerb.isMorePopular50()) {
            viewHolder.popularWord.setCriteria(PopularWordView.CRITERIA_MORE);
        } else if ((morePopularSetting.morePopularWordAmount == 100) && irregularVerb.isMorePopular100()) {
            viewHolder.popularWord.setCriteria(PopularWordView.CRITERIA_MORE);
        } else if (irregularVerb.isLessPopular()) {
            viewHolder.popularWord.setCriteria(PopularWordView.CRITERIA_LESS);
        } else {
            viewHolder.popularWord.setCriteria(PopularWordView.CRITERIA_NORMAL);
        }

    }

    private void fillText(final TextView textView, String[] textArray, ContainsInIrregularVerbDetail detail) {
        int symbolsCountBeforeHighlightWorld = 0;
        for (int i = 0; i < detail.arrayNumber; i++) {
            symbolsCountBeforeHighlightWorld = symbolsCountBeforeHighlightWorld + textArray[i].length() + 2;
        }
        Spannable span = new SpannableString(TextUtils.join(", ", textArray));
        span.setSpan(new BackgroundColorSpan(App.instance.getResources().getColor(R.color.highlight)),
                detail.startIndex + symbolsCountBeforeHighlightWorld, detail.lastIndex + symbolsCountBeforeHighlightWorld,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(span);
    }

    @Override
    public View create(View parent) {
        View view = View.inflate(parent.getContext(), layoutId, null);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.form1 = (TextView) view.findViewById(R.id.form1);
        viewHolder.form2 = (TextView) view.findViewById(R.id.form2);
        viewHolder.form3 = (TextView) view.findViewById(R.id.form3);
        viewHolder.translate = (TextView) view.findViewById(R.id.translate);
        viewHolder.mp3 = (ImageButton) view.findViewById(R.id.volume_image_button);
        viewHolder.popularWord = (PopularWordView) view.findViewById(R.id.popular_word_view);
        fill(data, viewHolder);
        view.setTag(viewHolder);
        return view;
    }

    public static class ViewHolder {
        public TextView form1;
        public TextView form2;
        public TextView form3;
        public TextView translate;
        public ImageButton mp3;
        public PopularWordView popularWord;
    }
}
