package com.template.mlevytskiy.vo;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import com.template.mlevytskiy.App;
import com.template.mlevytskiy.R;
import com.template.mlevytskiy.util.search.ContainsInIrregularVerbDetail;
import com.template.mlevytskiy.util.search.ContainsInStringArrayDetail;
import com.template.mlevytskiy.util.search.Detail;
import com.template.mlevytskiy.util.search.Searchable;

/**
 * Created by Макс on 21.04.2014.
 */
public class SameIrregularVerb extends MorePopular implements Searchable {

    private final String mPrefix;
    private final String[] mTranslate;
    private IrregularVerb mIrregularVerb;
    private ContainsInIrregularVerbDetail mLastDetail;

    public SameIrregularVerb(String prefix, String[] translate, IrregularVerb irregularVerb) {
        mPrefix = prefix;
        mTranslate = translate;
        mIrregularVerb = irregularVerb;
    }

    public IrregularVerb getWrapper() {
        return mIrregularVerb;
    }

    public String getPrefix() {
        return mPrefix;
    }

    public String[] getTranslate() {
        return mTranslate;
    }

    @Override
    public ContainsInIrregularVerbDetail contains(String str) {
        ContainsInStringArrayDetail containsInStringArrayDetail;
        ContainsInIrregularVerbDetail result;
        if ( !(containsInStringArrayDetail = ContainsInStringArrayDetail.contains(getForm1(), str)).isEmpty ) {
            result = new ContainsInIrregularVerbDetail(Detail.FORM1, containsInStringArrayDetail);
        } else if ( !(containsInStringArrayDetail = ContainsInStringArrayDetail.contains(getForm2(), str)).isEmpty ) {
            result = new ContainsInIrregularVerbDetail(Detail.FORM2, containsInStringArrayDetail);
        } else if ( !(containsInStringArrayDetail = ContainsInStringArrayDetail.contains(getForm3(), str)).isEmpty ) {
            result = new ContainsInIrregularVerbDetail(Detail.FORM3, containsInStringArrayDetail);
        } else if ( !(containsInStringArrayDetail = ContainsInStringArrayDetail.contains(mTranslate, str)).isEmpty ) {
            result = new ContainsInIrregularVerbDetail(Detail.TRANSLATE, containsInStringArrayDetail);
        } else {
            result = new ContainsInIrregularVerbDetail();
        }
        mLastDetail = result;
        return result;
    }

    @Override
    public ContainsInIrregularVerbDetail getLastDetail() {
        return mLastDetail;
    }

    @Override
    public void setLastDetail(ContainsInIrregularVerbDetail lastDetail) {
        mLastDetail = lastDetail;
    }

    public Spannable[] getForm1() {
        return getFormWithPrefix(mIrregularVerb.getForm1(), getPrefix());
    }

    public Spannable[] getForm2() {
        return getFormWithPrefix(mIrregularVerb.getForm2(), getPrefix());
    }

    public Spannable[] getForm3() {
        return getFormWithPrefix(mIrregularVerb.getForm3(), getPrefix());
    }

    private Spannable[] getFormWithPrefix(String[] form, String prefix) {
        Spannable[] result = new Spannable[form.length];
        for (int i = 0; i < form.length; i++) {
            result[i] = new SpannableString(prefix + form[i]);
            result[i].setSpan(new ForegroundColorSpan(App.instance.getResources().getColor(R.color.same_irregular_prefix)), 0, prefix.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return result;
    }

    @Override
    public String getForm1SimpleStr() {
        return getForm1()[0].toString();
    }
}
