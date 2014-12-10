package com.template.mlevytskiy.ui.irregularVerbModule;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import com.template.mlevytskiy.R;
import com.template.mlevytskiy.vo.IrregularVerb;
import com.template.mlevytskiy.vo.MorePopularSetting;
import com.template.mlevytskiy.vo.SameIrregularVerb;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Макс on 05.04.14.
 */
public class IrregularVerbComplexPagerAdapter extends PagerAdapter {

    private List<SameIrregularVerb> mList;
    private IrregularVerb mIrregularVerb;
    private MorePopularSetting morePopularSetting;

    public IrregularVerbComplexPagerAdapter(List<SameIrregularVerb> list, IrregularVerb irregularVerb,
                                            MorePopularSetting morePopularSetting) {
        mList = new ArrayList<SameIrregularVerb>();
        mList.addAll(list);
        mIrregularVerb = irregularVerb;
        this.morePopularSetting = morePopularSetting;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, final int i) {
        final View view;
        if (i == 0) {
            view = new IrregularVerbAdapterItemCreateHelper(mIrregularVerb,
                    R.layout.item_irregular_verbs_2, morePopularSetting).fill(null, collection);
        } else {
            view = new SameIrregularVerbPagerAdapterItemCreateHelper(mList.get(i-1), morePopularSetting).fill(null, collection);
        }
        collection.addView(view, 0);
        return view;
    }

    public int getItemPosition(Object object) {
        return POSITION_UNCHANGED;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return mList.size() + 1;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

}
