package com.template.mlevytskiy.ui.trainingModule;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.template.mlevytskiy.R;
import com.template.mlevytskiy.common.AnyFragment;
import com.template.mlevytskiy.ui.MainActivity;
import com.template.mlevytskiy.util.RandomInt;
import com.template.mlevytskiy.vo.IrregularVerb;
import java.util.List;

/**
 * Created by Макс on 30.07.2014.
 */
public class TrainingWriteWordPagerAdapter extends PagerAdapter {

    private List<IrregularVerb> list;
    private ViewPager viewPager;

    private int incorrectWordsInt = 0;
    private int correctWordsInt = 0;

    private Button nextButton;
    private TextView correctWords;
    private TextView incorrectWords;

    public TrainingWriteWordPagerAdapter(List<IrregularVerb> list, final ViewPager viewPager, Button nextButton,
                                         TextView correctWords, TextView incorrectWords) {
        this.list = list;
        this.viewPager = viewPager;

        this.nextButton = nextButton;
        this.correctWords = correctWords;
        this.incorrectWords = incorrectWords;
    }

    @Override
    public int getCount() {
        return list.size() + 1;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public Object instantiateItem(final ViewGroup collection, final int i) {
        if ( i < list.size()) {
            final View view = View.inflate(collection.getContext(), R.layout.pager_adapter_training_write_word, null);

            correctWords.setText(String.valueOf(correctWordsInt));
            incorrectWords.setText(String.valueOf(incorrectWordsInt));

            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    incorrectWordsInt = Integer.parseInt(incorrectWords.getText().toString());
                    correctWordsInt = Integer.parseInt(correctWords.getText().toString());
                    int currentPage = viewPager.getCurrentItem();
                    if (currentPage < list.size()) {
                        currentPage = currentPage + 1;
                        viewPager.setCurrentItem(currentPage);
                    } else {
                        //doNothing
                    }
                }
            });

            ListView listView = (ListView) view.findViewById(R.id.list_view);
            int secret = new RandomInt().get(0, 3);
            TrainingWriteWordSimpleAdapter adapter = new TrainingWriteWordSimpleAdapter(list.get(i),
                    secret, nextButton, correctWords, incorrectWords);
            listView.setAdapter(adapter);
            collection.addView(view, 0);
//            if (i == list.size() - 1) {
//                nextButton.setText("Результат");
//            }
            return view;
        } else {
            View view = View.inflate(collection.getContext(), R.layout.pager_adapter_training_write_word_last_page, null);
            collection.addView(view, 0);
            return view;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
