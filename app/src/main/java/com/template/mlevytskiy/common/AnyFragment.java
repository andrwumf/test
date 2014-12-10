package com.template.mlevytskiy.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.template.mlevytskiy.R;

/**
 * Created by Макс on 16.04.2014.
 */
public abstract class AnyFragment extends Fragment implements ChangeLanguage {

    protected boolean mIsCreated = false;

    public AnyFragment() {
        super();
        setRetainInstance(true);
    }

    public boolean isCreated() {
        return mIsCreated;
    }

    public void setIsCreated(boolean mIsCreated) {
        this.mIsCreated = mIsCreated;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mIsCreated = true;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public abstract void onCreateOptionsMenu(Menu menu, android.view.MenuInflater menuInflater);
    public abstract boolean onOptionsItemSelected(MenuItem item);

}
