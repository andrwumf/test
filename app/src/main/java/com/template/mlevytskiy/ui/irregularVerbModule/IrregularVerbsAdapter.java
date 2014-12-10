package com.template.mlevytskiy.ui.irregularVerbModule;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.template.mlevytskiy.App;
import com.template.mlevytskiy.util.IrregularVerbFilterUtils;
import com.template.mlevytskiy.vo.IrregularVerb;
import com.template.mlevytskiy.vo.MorePopularSetting;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Макс on 09.03.14.
 */
public class IrregularVerbsAdapter extends BaseAdapter {

    private static final int VIEW_TYPE_SIMPLE = 0;
    private static final int VIEW_TYPE_WITH_SAME_VERBS = 1;
    private List<IrregularVerb> mListBeforeFilter;
    private List<IrregularVerb> mListBeforeSearchWithFilter = new ArrayList<IrregularVerb>();
    private List<IrregularVerb> mList = new ArrayList<IrregularVerb>();
    private MorePopularSetting morePopularSetting = new MorePopularSetting();
    private IrregularVerbFilterUtils filter = new IrregularVerbFilterUtils();


    public IrregularVerbsAdapter(final MorePopularSetting morePopularSetting) {
        if (App.listIsEmpty()) {
            App.asyncInitStringFromRes(new Runnable() {
                @Override
                public void run() {
                    mListBeforeFilter = App.getList();
                    update(morePopularSetting);
                }
            });
        } else {
            mListBeforeFilter = App.getList();
            updateMorePopularSetting(morePopularSetting);
        }

    }

    private void updateMorePopularSetting(MorePopularSetting morePopularSetting) {
        this.morePopularSetting.less = morePopularSetting.less;
        this.morePopularSetting.more = morePopularSetting.more;
        this.morePopularSetting.normal = morePopularSetting.normal;
        this.morePopularSetting.morePopularWordAmount = morePopularSetting.morePopularWordAmount;
        mList = filter.filter(mListBeforeFilter, morePopularSetting);
        mListBeforeSearchWithFilter.clear();
        mListBeforeSearchWithFilter.addAll(mList);
    }

    public List<IrregularVerb> getListBeforeSearchWithFilter() {
        return mListBeforeSearchWithFilter;
    }

    public void update(MorePopularSetting morePopularSetting) {
        updateMorePopularSetting(morePopularSetting);
        notifyDataSetChanged();
    }

    public void update(List<IrregularVerb> irregularVerbs) {
        if ( (irregularVerbs.size() == App.getList().size() && App.getList().size() == mList.size())
                || (mList.isEmpty() && irregularVerbs.isEmpty())) {
            return;
        }
        mList.clear();
        mList.addAll(irregularVerbs);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (getItemViewType(i) == VIEW_TYPE_SIMPLE) {
            view = new IrregularVerbAdapterItemCreateHelper(mList.get(i), morePopularSetting).fill(view, viewGroup);
        } else {
            view = new SameIrregularVerbAdapterItemCreateHelper(mList.get(i), morePopularSetting).fill(view, viewGroup);
        }
        return view;
    }

    public int getItemViewType(int position) {
        IrregularVerb irregularVerb = mList.get(position);
        if (irregularVerb.getSameIrregularVerbs().isEmpty()) {
            return VIEW_TYPE_SIMPLE;
        } else {
            return VIEW_TYPE_WITH_SAME_VERBS;
        }
    }

    public int getViewTypeCount() {
        return 2;
    }
}
