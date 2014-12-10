package com.template.mlevytskiy.ui;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.template.mlevytskiy.R;
import com.template.mlevytskiy.common.AnyFragment;
import com.template.mlevytskiy.util.ResourceUtils;

import java.io.IOException;

/**
 * Created by Макс on 01.05.2014.
 */
public class StoriesFragment extends AnyFragment {

    private MediaPlayer mPlayer = new MediaPlayer();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_pager_container, container, false);
        ViewPager viewPager = (ViewPager) v.findViewById(R.id.view_pager);
        viewPager.setAdapter(new StoriesPagerAdapter());
        return v;
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
        //doNothing
    }

    public void onClickIrregularRapVolume(View view) {
        Button button = (Button) view;
        if (TextUtils.equals("listen", button.getText())) {
            button.setText("stop listen");
            Uri rawMp3 = new ResourceUtils(view.getContext()).getRaw("irregular_verb_rap");
            mPlayer.reset();
            try {
                try {
                    mPlayer.setDataSource(view.getContext(), rawMp3);
                } catch (IllegalStateException e) {
                    mPlayer.reset();
                    mPlayer.setDataSource(view.getContext(), rawMp3);
                }
                mPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mPlayer.start();
        } else {
            mPlayer.reset();
            button.setText("listen");
        }
    }

    public void onPause() {
        mPlayer.reset();
        super.onPause();
    }
}
