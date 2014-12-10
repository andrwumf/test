package com.template.mlevytskiy.ui.trainingModule;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.template.mlevytskiy.App;
import com.template.mlevytskiy.R;
import com.template.mlevytskiy.common.AnyFragment;
import com.template.mlevytskiy.common.memory.MemoryCommunicator;
import com.template.mlevytskiy.memory.MemoryKey;
import com.template.mlevytskiy.ui.widget.ChoiceIrregularVerbsDialog;
import com.template.mlevytskiy.util.IrregularVerbFilterUtils;
import com.template.mlevytskiy.vo.IrregularVerb;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrainingCardFragment extends AnyFragment {

    private static final boolean DEFAULT_VALUE_IS_ALL_VERBS = false;
    private static final boolean DEFAULT_VALUE_IS_ENGLISH_STATE = true;

    private ViewPager mViewPager;
    private TrainingCardPagerAdapter mTrainingCardPagerAdapter;
    private boolean isAllVerbs = DEFAULT_VALUE_IS_ALL_VERBS;
    private boolean isEnglishState = DEFAULT_VALUE_IS_ENGLISH_STATE;

    private MemoryCommunicator memory = new MemoryCommunicator(MemoryKey.isShowAllVerbs, MemoryKey.trainingFragmentIsEnglishState);

    public TrainingCardFragment() {
        super();
        this.setHasOptionsMenu(true);
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_pager_container, container, false);
        mViewPager = (ViewPager) v.findViewById(R.id.view_pager);
        if (!memory.isEmpty()) {
            isAllVerbs = memory.loadBoolean(MemoryKey.isShowAllVerbs);
            isEnglishState = memory.loadBoolean(MemoryKey.trainingFragmentIsEnglishState);
        }
        fillViewPager();
		return v;
	}

    public void onPause() {
        memory.saveBoolean(isAllVerbs, MemoryKey.isShowAllVerbs);
        memory.saveBoolean(isEnglishState, MemoryKey.trainingFragmentIsEnglishState);
        super.onPause();
    }

    private void fillViewPager() {
        fillViewPagerAllList();
//        if (isAllVerbs) {
//            fillViewPagerAllList();
//        } else {
//            fillViewPager2();
//        }
    }

    private void fillViewPagerAllList() {
        List<IrregularVerb> list = new ArrayList<IrregularVerb>();
        int amount = App.instance.getTrainingWords().getAmount();
        IrregularVerbFilterUtils filterUtils = new IrregularVerbFilterUtils();
        list.addAll(filterUtils.filter(App.getList(), amount));
        Collections.shuffle(list);
        mTrainingCardPagerAdapter = new TrainingCardPagerAdapter(list, isEnglishState);
        mViewPager.setAdapter(mTrainingCardPagerAdapter);
    }

    private void fillViewPager2() {
        List<IrregularVerb> list = new ArrayList<IrregularVerb>();

        for (IrregularVerb verb : App.getList()) {
            if (verb.ismIsFrequentlyUsed()) {
                list.add(verb);
            }
        }

        Collections.shuffle(list);
        mTrainingCardPagerAdapter = new TrainingCardPagerAdapter(list, isEnglishState);
        mViewPager.setAdapter(mTrainingCardPagerAdapter);
    }

    public void setEnglishState() {
        mTrainingCardPagerAdapter.setCardFaceState(mViewPager.getCurrentItem());
    }

    public void setTranslateState() {
        mTrainingCardPagerAdapter.setCardBackState(mViewPager.getCurrentItem());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menu.clear();
        menuInflater.inflate(R.menu.turn_and_filter, menu);
        MenuItem item = menu.getItem(0);
        if (!isEnglishState) {
            item.setTitle("turn back");
            item.setIcon(R.drawable.ic_turn_back);
        } else {
            item.setTitle("turn");
            item.setIcon(R.drawable.ic_turn);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_turn:
                isEnglishState = !isEnglishState;

                if (isEnglishState) {
                    item.setTitle("turn");
                    item.setIcon(R.drawable.ic_turn);
                    setEnglishState();
                } else {
                    item.setTitle("turn back");
                    item.setIcon(R.drawable.ic_turn_back);
                    setTranslateState();
                }

                return true;
            case R.id.menu_filter:
                getActivity().setTheme(R.style.DialogStyleLight);
                ChoiceIrregularVerbsDialog.show(TrainingCardFragment.this.getActivity(), isAllVerbs, new Runnable() {
                    @Override
                    public void run() {
                        isAllVerbs = true;
                        fillViewPager();
                    }
                }, new Runnable() {
                    @Override
                    public void run() {
                        isAllVerbs = false;
                        fillViewPager();
                    }
                });
                return true;
        }
        return false;
    }

    @Override
    public void updateText() {
        if (mIsCreated) {
            fillViewPager();
        }
    }

    public void shuffle() {
        fillViewPager();
    }
}