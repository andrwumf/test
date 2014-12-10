package com.template.mlevytskiy.ui.irregularVerbModule;

import android.support.v4.view.ViewPager;
import android.view.View;
import com.template.mlevytskiy.R;
import com.template.mlevytskiy.common.AdapterItemCreateHelper;
import com.template.mlevytskiy.util.search.ContainsInIrregularVerbDetail;
import com.template.mlevytskiy.vo.IrregularVerb;
import com.template.mlevytskiy.vo.MorePopularSetting;

/**
 * Created by Макс on 21.04.2014.
 */
public class SameIrregularVerbAdapterItemCreateHelper extends AdapterItemCreateHelper<IrregularVerb> {

    private MorePopularSetting morePopularSetting;

    public SameIrregularVerbAdapterItemCreateHelper(IrregularVerb data, MorePopularSetting morePopularSetting) {
        super(data);
        this.morePopularSetting = morePopularSetting;
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
        viewHolder.viewPager.setAdapter(new IrregularVerbComplexPagerAdapter(irregularVerb.getSameIrregularVerbs(),
                irregularVerb, morePopularSetting));
        int index = -1;
        if (irregularVerb.getLastDetail() == null || irregularVerb.getLastDetail().isEmpty) {
            for (int i = 0; i < irregularVerb.getSameIrregularVerbs().size(); i++) {
                ContainsInIrregularVerbDetail verbDetail = irregularVerb.getSameIrregularVerbs().get(i).getLastDetail();
                if (verbDetail != null && !verbDetail.isEmpty) {
                    index = i;
                    break;
                }
            }
        }
        viewHolder.viewPager.setCurrentItem(index+1);
    }

    @Override
    public View create(View parent) {
        View view = View.inflate(parent.getContext(), R.layout.item_irregular_verbs_complex, null);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        viewHolder.viewPager.setPageTransformer(true, new AlphaTransformer(viewHolder.viewPager));
        fill(data, viewHolder);
        view.setTag(viewHolder);
        return view;
    }

    public static class ViewHolder {
        public ViewPager viewPager;
    }
}
