package com.template.mlevytskiy.ui.irregularVerbModule;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.template.mlevytskiy.R;
import com.template.mlevytskiy.common.AnyFragment;
import com.template.mlevytskiy.common.memory.MemoryCommunicator;
import com.template.mlevytskiy.memory.MemoryKey;
import com.template.mlevytskiy.ui.widget.ChoiceWordGroupDialog;
import com.template.mlevytskiy.util.PlayUtil;
import com.template.mlevytskiy.util.search.SearchQuery;
import com.template.mlevytskiy.vo.IrregularVerb;
import com.template.mlevytskiy.vo.MorePopularSetting;
import com.template.mlevytskiy.vo.SameIrregularVerb;

import java.util.ArrayList;
import java.util.List;

public class IrregularVerbsFragment extends AnyFragment {

    private IrregularVerbsAdapter mAdapter;
    private ListView mListView;
    private MediaPlayer mPlayer = new MediaPlayer();
    private SearchView mSearchView;
    private MorePopularSetting morePopularSetting = new MorePopularSetting();

    public boolean isCreated() {
        return mIsCreated;
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        loadMorePopularSettings();
		View v = inflater.inflate(R.layout.fragment_irregular_verbs, container, false);
        mListView = (ListView) v.findViewById(R.id.list_view);
        mAdapter = new IrregularVerbsAdapter(morePopularSetting);
        mListView.addHeaderView(View.inflate(mListView.getContext(), R.layout.header_irregular_verbs_empty, null));
        mListView.setAdapter(mAdapter);
		return v;
	}

    public void updateAdapterAndScrollListTop(List<IrregularVerb> irregularVerbs) {
        mAdapter.update(irregularVerbs);
        if (irregularVerbs.isEmpty() || ((mListView.getChildCount() == 0) || (mListView.getChildAt(0).getTop() == 0))) {
            return;
        }
        mListView.setSelectionAfterHeaderView();
    }

    public IrregularVerbsAdapter getAdapter() {
        return mAdapter;
    }

    public void onClickVolumeImageButton(View view) {
        Uri rawMp3 = (Uri) view.getTag();
        PlayUtil.play(mPlayer, rawMp3, view.getContext());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, android.view.MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.default_search, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //final List<IrregularVerb> list = new ArrayList<IrregularVerb>();
        new SearchQuery(mSearchView) {
            @Override
            public boolean onQuery(String paramString) {
                if (TextUtils.isEmpty(paramString)) {
                    if (IrregularVerbsFragment.this != null && IrregularVerbsFragment.this.getAdapter() != null) {
                        List<IrregularVerb> list = IrregularVerbsFragment.this.getAdapter().getListBeforeSearchWithFilter();
                        for (IrregularVerb item : list) {
                            item.setLastDetail(null);
                            for (SameIrregularVerb sameItem : item.getSameIrregularVerbs()) {
                                sameItem.setLastDetail(null);
                            }
                        }
                        IrregularVerbsFragment.this.updateAdapterAndScrollListTop(list);
                    }
                    return false;
                }
                List<IrregularVerb> result = new ArrayList<IrregularVerb>();
                List<IrregularVerb> contains = new ArrayList<IrregularVerb>();
                List<IrregularVerb> startWith = new ArrayList<IrregularVerb>();
                List<IrregularVerb> same = new ArrayList<IrregularVerb>();
                List<IrregularVerb> wrappers = new ArrayList<IrregularVerb>();
                List<IrregularVerb> list = IrregularVerbsFragment.this.getAdapter().getListBeforeSearchWithFilter();
                for (IrregularVerb item : list) {
                    if (!item.contains(paramString).isEmpty) {
                        if (item.getLastDetail().isSame) {
                            same.add(item);
                        } else if (item.getLastDetail().isStartWith) {
                            startWith.add(item);
                        } else {
                            contains.add(item);
                        }
                    } else {
                        for (SameIrregularVerb sameItem : item.getSameIrregularVerbs()) {
                            if (!sameItem.contains(paramString).isEmpty) {
                                wrappers.add(sameItem.getWrapper());
                            }
                        }
                    }
                }
                result.addAll(same);
                result.addAll(startWith);
                result.addAll(contains);
                result.addAll(wrappers);
                IrregularVerbsFragment.this.updateAdapterAndScrollListTop(result);
                return !TextUtils.isEmpty(paramString);

            }
        };

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_filter:
                ChoiceWordGroupDialog.show(this.getActivity(), morePopularSetting,
                        new Runnable() {
                            @Override
                            public void run() {
                                saveMorePopularSettings();
                                mAdapter.update(morePopularSetting);
                            }
                        });
                return true;
        }
        return false;
    }

    public void saveMorePopularSettings() {
        MemoryCommunicator memory = new MemoryCommunicator();
        memory.saveBoolean(morePopularSetting.more, MemoryKey.morePopularWords_more);
        memory.saveBoolean(morePopularSetting.normal, MemoryKey.morePopularWords_normal);
        memory.saveBoolean(morePopularSetting.less, MemoryKey.morePopularWords_less);
        memory.saveInt(MemoryKey.morePopularWords_more_amount, morePopularSetting.morePopularWordAmount);
    }

    public void loadMorePopularSettings() {
        MemoryCommunicator memory = new MemoryCommunicator();
        morePopularSetting.more = memory.loadBoolean(MemoryKey.morePopularWords_more, true);
        morePopularSetting.normal = memory.loadBoolean(MemoryKey.morePopularWords_normal, true);
        morePopularSetting.less = memory.loadBoolean(MemoryKey.morePopularWords_less, true);
        morePopularSetting.morePopularWordAmount = memory.loadInt(MemoryKey.morePopularWords_more_amount, 100);
    }

    @Override
    public void updateText() {
        if (mIsCreated) {
            mAdapter = new IrregularVerbsAdapter(morePopularSetting);
            mListView.setAdapter(mAdapter);
        }
    }
}