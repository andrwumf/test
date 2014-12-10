package com.template.mlevytskiy.ui.irregularVerbModule;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.template.mlevytskiy.App;
import com.template.mlevytskiy.R;
import com.template.mlevytskiy.common.AdapterItemCreateHelper;
import com.template.mlevytskiy.ui.widget.PopularWordView;
import com.template.mlevytskiy.util.SpannableUtils;
import com.template.mlevytskiy.util.search.ContainsInIrregularVerbDetail;
import com.template.mlevytskiy.util.search.Detail;
import com.template.mlevytskiy.vo.MorePopularSetting;
import com.template.mlevytskiy.vo.SameIrregularVerb;

/**
 * Created by Макс on 21.04.2014.
 */
public class SameIrregularVerbPagerAdapterItemCreateHelper extends AdapterItemCreateHelper<SameIrregularVerb> {

    private MorePopularSetting morePopularSetting;

    public SameIrregularVerbPagerAdapterItemCreateHelper(SameIrregularVerb data,
                                                         MorePopularSetting morePopularSetting) {
        super(data);
        this.morePopularSetting = morePopularSetting;
    }

    @Override
    public View fill(View view, View parent) {
        if (view == null) {
            view = create(parent);
        } else {
            //doNothing
        }
        return view;
    }

    private void fill(SameIrregularVerb sameIrregularVerb, ViewHolder viewHolder) {
        viewHolder.form1.setText(SpannableUtils.join(", ", sameIrregularVerb.getForm1()));
        viewHolder.form2.setText(SpannableUtils.join(", ", sameIrregularVerb.getForm2()));
        viewHolder.form3.setText(SpannableUtils.join(", ", sameIrregularVerb.getForm3()));
        viewHolder.translate.setText(TextUtils.join(", ", sameIrregularVerb.getTranslate()));

        if (sameIrregularVerb.getLastDetail() != null) {
            if (sameIrregularVerb.getLastDetail().isEmpty) {
                //doNothing
            } else if (sameIrregularVerb.getLastDetail().detail == Detail.FORM1) {
                fillText(viewHolder.form1, sameIrregularVerb.getForm1(), sameIrregularVerb.getLastDetail());
            } else if (sameIrregularVerb.getLastDetail().detail == Detail.FORM2) {
                fillText(viewHolder.form2, sameIrregularVerb.getForm2(), sameIrregularVerb.getLastDetail());
            } else if (sameIrregularVerb.getLastDetail().detail == Detail.FORM3) {
                fillText(viewHolder.form3, sameIrregularVerb.getForm3(), sameIrregularVerb.getLastDetail());
            } else if (sameIrregularVerb.getLastDetail().detail == Detail.TRANSLATE) {
                fillText(viewHolder.translate, sameIrregularVerb.getTranslate(), sameIrregularVerb.getLastDetail());
            }
        }

        //set more popular
        if ((morePopularSetting.morePopularWordAmount == 20) && sameIrregularVerb.isMorePopular20()) {
            viewHolder.popularWord.setCriteria(PopularWordView.CRITERIA_MORE);
        } else if ((morePopularSetting.morePopularWordAmount == 50) && sameIrregularVerb.isMorePopular50()) {
            viewHolder.popularWord.setCriteria(PopularWordView.CRITERIA_MORE);
        } else if ((morePopularSetting.morePopularWordAmount == 100) && sameIrregularVerb.isMorePopular100()) {
            viewHolder.popularWord.setCriteria(PopularWordView.CRITERIA_MORE);
        } else if (sameIrregularVerb.isLessPopular()) {
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

    private void fillText(final TextView textView, Spannable[] textArray, ContainsInIrregularVerbDetail detail) {
        int symbolsCountBeforeHighlightWorld = 0;
        for (int i = 0; i < detail.arrayNumber; i++) {
            symbolsCountBeforeHighlightWorld = symbolsCountBeforeHighlightWorld + textArray[i].length() + 2;
        }
        Spannable span = SpannableUtils.join(", ", textArray);
        span.setSpan(new BackgroundColorSpan(App.instance.getResources().getColor(R.color.highlight)),
                detail.startIndex + symbolsCountBeforeHighlightWorld, detail.lastIndex + symbolsCountBeforeHighlightWorld,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(span);
    }

    @Override
    public View create(View parent) {
        View view = View.inflate(parent.getContext(), R.layout.item_same_irregular_verbs, null);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.form1 = (TextView) view.findViewById(R.id.form1);
        viewHolder.form2 = (TextView) view.findViewById(R.id.form2);
        viewHolder.form3 = (TextView) view.findViewById(R.id.form3);
        viewHolder.translate = (TextView) view.findViewById(R.id.translate);
        viewHolder.popularWord = (PopularWordView) view.findViewById(R.id.popular_word_view);
        fill(data, viewHolder);
        return view;
    }

    public static class ViewHolder {
        public TextView form1;
        public TextView form2;
        public TextView form3;
        public TextView translate;
        public PopularWordView popularWord;
    }
}
