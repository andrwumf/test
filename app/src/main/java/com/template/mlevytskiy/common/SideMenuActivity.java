package com.template.mlevytskiy.common;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.template.mlevytskiy.ui.slideMenuModule.NavigationDrawerFragment;
import com.template.mlevytskiy.ui.slideMenuModule.OnCloseDrawerListener;
import com.template.mlevytskiy.R;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Макс on 16.04.2014.
 */
public abstract class SideMenuActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    protected NavigationDrawerFragment mNavigationDrawerFragment;
    private int mCurrentPosition = 0;
    private boolean isFirstStart = true;
    public List<AnyFragment> otherFragments = new ArrayList<AnyFragment>();
    public List<AnyFragment> mMenuFragments; {
        initFragments();
    }
    private Date lastOptionsItemSelectedClickTime = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                drawerLayout);

        mNavigationDrawerFragment.setOnCloseDrawerListener(new OnCloseDrawerListener() {
            @Override
            public void onClose(int currentPosition, View view) {
                onMenuItemSelected(currentPosition);

                boolean otherFragmentIsVisible = false;
                for (AnyFragment anyFragment : otherFragments) {
                    if (anyFragment.isVisible()) {
                        otherFragmentIsVisible = true;
                        break;
                    }
                }
                if (otherFragmentIsVisible) {
                    return;
                }

                SideMenuActivity.this.findViewById(R.id.progress).setVisibility(View.INVISIBLE);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                for (int i = 0; i < mMenuFragments.size(); i++) {
                    if (i == currentPosition) {
                        ft.show(mMenuFragments.get(i));
                    } else {
                        ft.hide(mMenuFragments.get(i));
                    }
                }

                ft.commit();
            }
        });
    }

    protected abstract void initFragments();

    public void onClickSideMenuItem(View view) {
        int position = (Integer) view.getTag();
        mNavigationDrawerFragment.selectItem(position);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        try {
            InputMethodManager input = (InputMethodManager) this
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            input.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }catch(Exception e) {
            e.printStackTrace();
        }

        if (!isFirstStart) {
            if (position == mCurrentPosition) {
                return;
            }
            this.findViewById(R.id.progress).setVisibility(View.VISIBLE);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.hide(mMenuFragments.get(mCurrentPosition));
            for (AnyFragment anyFragment : otherFragments) {
                ft.hide(anyFragment);
            }
            ft.commit();
        } else {
            isFirstStart = false;
            //onMenuItemSelected(mCurrentPosition);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();

            List<Fragment> oldFragments = new ArrayList<Fragment>(fragmentManager.getFragments());
            for (Fragment fragment : oldFragments) {
                if ( !(fragment instanceof NavigationDrawerFragment) ) {
                    ft.remove(fragment);
                }
            }

            for (int i = mMenuFragments.size()-1; i > 0; i--) {
                ft.add(R.id.container, mMenuFragments.get(i));
                ft.hide(mMenuFragments.get(i));
            }

            ft.add(R.id.container, mMenuFragments.get(0));
            ft.commit();
        }
        mCurrentPosition = position;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            onCreateOptionsMenuInCurrentFragment(menu, getMenuInflater(), mCurrentPosition);
//            restoreActionBar();
            return true;
        }
        return false;//super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean isClicked = onOptionsItemSelectedInCurrentFragment(item, mCurrentPosition);
        if (isClicked) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    protected void onMenuItemSelected(int position) {
        //doNothing
    }

    public boolean onCreateOptionsMenuInCurrentFragment(Menu menu, android.view.MenuInflater menuInflater,
                                                        int position) {
        getCurrentFragment(position).onCreateOptionsMenu(menu, menuInflater);
        return true;
    }

    public boolean onOptionsItemSelectedInCurrentFragment(MenuItem item, int position) {
        return getCurrentFragment(position).onOptionsItemSelected(item);
    }

    private AnyFragment getCurrentFragment(int menuPosition) {
        for (AnyFragment fragment : otherFragments) {
            if (fragment.isVisible()) {
                return fragment;
            }
        }
        return mMenuFragments.get(menuPosition);
    }
}
