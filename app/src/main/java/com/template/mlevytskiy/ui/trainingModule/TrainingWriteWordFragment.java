package com.template.mlevytskiy.ui.trainingModule;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import com.template.mlevytskiy.App;
import com.template.mlevytskiy.R;
import com.template.mlevytskiy.common.AnyFragment;
import com.template.mlevytskiy.ui.MainActivity;
import com.template.mlevytskiy.util.IrregularVerbFilterUtils;
import com.template.mlevytskiy.vo.IrregularVerb;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Макс on 24.07.2014.
 */
public class TrainingWriteWordFragment extends AnyFragment {

    private ViewPager viewPager;
    private InputMethodManager inputMethodManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_pager_container_2, container, false);
        viewPager = (ViewPager) v.findViewById(R.id.view_pager);
        final List<IrregularVerb> list = new IrregularVerbFilterUtils().filter(App.getList(), App.instance.getTrainingWords().getAmount());

        long seed = System.nanoTime();
        Collections.shuffle(list, new Random(seed));
        final List<IrregularVerb> result = new ArrayList<IrregularVerb>();
        for (int i = 0; i < list.size(); i++) {
            result.add(list.get(i));
        }

        final Button nextButton = (Button) v.findViewById(R.id.next_button);
        TextView correctWords = (TextView) v.findViewById(R.id.correct_words);
        TextView incorrectWords = (TextView) v.findViewById(R.id.incorrect_words);
        final TextView pagination = (TextView) v.findViewById(R.id.text_view);
        pagination.setText("" + (viewPager.getCurrentItem() + 1) + "/" + result.size());

        TrainingWriteWordPagerAdapter adapter = new TrainingWriteWordPagerAdapter(result, viewPager,
                nextButton, correctWords, incorrectWords);
        viewPager.setAdapter(adapter);
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showKeyboard();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        View view = viewPager.getFocusedChild().findViewById(R.id.edit_text);
                        view.setFocusableInTouchMode(true);
                        view.requestFocus();
                    }
                }, 200);
            }
        }, 200);


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                if (position == viewPager.getAdapter().getCount() - 1) {
                    try {
                        InputMethodManager input = (InputMethodManager) getActivity()
                                .getSystemService(Activity.INPUT_METHOD_SERVICE);
                        input.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    }catch(Exception e) {
                        e.printStackTrace();
                    }

                    nextButton.setText(TrainingWriteWordFragment.this.getString(R.string.exit));
                    nextButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MainActivity activity = (MainActivity) getActivity();
                            android.support.v4.app.FragmentManager fragmentManager = activity.getSupportFragmentManager();
                            FragmentTransaction ft = fragmentManager.beginTransaction();
                            for (AnyFragment fragment : activity.otherFragments) {
                                if (fragment.isVisible()) {
                                    ft.hide(fragment);
                                    break;
                                }
                            }
                            ft.show(activity.mMenuFragments.get(1));
                            ft.commit();
                        }
                    });

                } else {
                    pagination.setText("" + (position+1) + "/" + result.size());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showKeyboard();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    View view = viewPager.getFocusedChild().findViewById(R.id.edit_text);
                                    view.setFocusableInTouchMode(true);
                                    view.requestFocus();
                                }
                            }, 200);
                        }
                    }, 200);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return v;
    }

    private void showKeyboard() {
        View view = viewPager.getFocusedChild().findViewById(R.id.edit_text);
        inputMethodManager.toggleSoftInputFromWindow(view.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public void updateText() {

    }
}
