package com.template.mlevytskiy.vo;

import android.net.Uri;
import com.template.mlevytskiy.util.search.ContainsInIrregularVerbDetail;
import com.template.mlevytskiy.util.search.ContainsInStringArrayDetail;
import com.template.mlevytskiy.util.search.Detail;
import com.template.mlevytskiy.util.search.Searchable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Макс on 08.03.14.
 */
public class IrregularVerb extends MorePopular implements Searchable {

    private String[] mForm1;
    private String[] mForm2;
    private String[] mForm3;
    private String[] mTranslate;
    private Uri mMp3Raw;
    private int mDrawableId;
    private boolean mIsFrequentlyUsed = false;
    private List<SameIrregularVerb> mSameIrregularVerbs = new ArrayList<SameIrregularVerb>();
    private ContainsInIrregularVerbDetail mLastDetail = null;
    private int oldPosition;

    public IrregularVerb(String[] form1, String[] form2, String[] form3, String[] translate,
                         Uri mp3Raw, int drawableId, boolean isFrequentlyUsed) {
        mForm1 = form1;
        mForm2 = form2;
        mForm3 = form3;
        mTranslate = translate;
        mMp3Raw = mp3Raw;
        mDrawableId = drawableId;
        mIsFrequentlyUsed = isFrequentlyUsed;
    }

    public int getOldPosition() {
        return oldPosition;
    }

    public void setOldPosition(int oldPosition) {
        this.oldPosition = oldPosition;
    }

    public int getDrawableId() {
        return mDrawableId;
    }

    public String[] getForm1() {
        return mForm1;
    }

    public String[] getForm2() {
        return mForm2;
    }

    public String[] getForm3() {
        return mForm3;
    }

    public String[] getTranslate() {
        return mTranslate;
    }

    public List<SameIrregularVerb> getSameIrregularVerbs() {
        return mSameIrregularVerbs;
    }

    public Uri getMp3Raw() { return mMp3Raw; }

    public boolean ismIsFrequentlyUsed() {
        return mIsFrequentlyUsed;
    }

    public ContainsInIrregularVerbDetail getLastDetail() {
        return mLastDetail;
    }

    public void setLastDetail(ContainsInIrregularVerbDetail lastDetail) {
        mLastDetail = lastDetail;
    }

    public ContainsInIrregularVerbDetail contains(String str) {
        ContainsInStringArrayDetail containsInStringArrayDetail;
        ContainsInIrregularVerbDetail result;
        if ( !(containsInStringArrayDetail = ContainsInStringArrayDetail.contains(mForm1, str)).isEmpty ) {
            result = new ContainsInIrregularVerbDetail(Detail.FORM1, containsInStringArrayDetail);
        } else if ( !(containsInStringArrayDetail = ContainsInStringArrayDetail.contains(mForm2, str)).isEmpty ) {
            result = new ContainsInIrregularVerbDetail(Detail.FORM2, containsInStringArrayDetail);
        } else if ( !(containsInStringArrayDetail = ContainsInStringArrayDetail.contains(mForm3, str)).isEmpty ) {
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
    public String getForm1SimpleStr() {
        return getForm1()[0];
    }
}
