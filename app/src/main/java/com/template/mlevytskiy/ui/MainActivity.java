package com.template.mlevytskiy.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import com.template.mlevytskiy.App;
import com.template.mlevytskiy.R;
import com.template.mlevytskiy.common.AnyFragment;
import com.template.mlevytskiy.common.ChangeLanguage;
import com.template.mlevytskiy.common.SideMenuActivity;
import com.template.mlevytskiy.common.memory.MemoryCommunicator;
import com.template.mlevytskiy.memory.MemoryKey;
import com.template.mlevytskiy.ui.irregularVerbModule.IrregularVerbsFragment;
import com.template.mlevytskiy.ui.trainingModule.TrainingCardFragment;
import com.template.mlevytskiy.ui.trainingModule.TrainingMainFragment;
import com.template.mlevytskiy.ui.trainingModule.TrainingWriteWordFragment;
import com.template.mlevytskiy.util.LanguageUtil;
import java.util.ArrayList;

public class MainActivity extends SideMenuActivity implements ChangeLanguage {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (new MemoryCommunicator(MemoryKey.language).isEmpty()) {
            showLanguageDialog();
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        AnyFragment currentOtherFragment = null;
        for (AnyFragment fragment : otherFragments) {
            if (fragment.isVisible()) {
                currentOtherFragment = fragment;
                ft.hide(fragment);
                break;
            }
        }
        if (currentOtherFragment == null) {
            super.onBackPressed();
        } else {
            if ((currentOtherFragment instanceof TrainingCardFragment)
                || (currentOtherFragment instanceof TrainingWriteWordFragment)) {
                ft.show(mMenuFragments.get(1));
                ft.commit();
            }
        }

    }

    protected void onMenuItemSelected(int position) {
        if (position == 1) {
//            ((TrainingCardFragment) mMenuFragments.get(1)).shuffle();
        } else if (position == 2) {
            ((SettingsFragment) mMenuFragments.get(2)).detectLanguage();
        }
    }

    @Override
    protected void initFragments() {
        mMenuFragments = new ArrayList<AnyFragment>();
        mMenuFragments.add(new IrregularVerbsFragment());
//        mMenuFragments.add(new TrainingCardFragment());
        mMenuFragments.add(new TrainingMainFragment());
        mMenuFragments.add(new SettingsFragment());
        mMenuFragments.add(new StoriesFragment());
//        otherFragments.add(new TrainingCardFragment());
    }

    public void onClickWriteWordTrainingStart(View view) {
        ((TrainingMainFragment) mMenuFragments.get(1)).onClickWriteWordTrainingStart(view);
    }

    public  void onClickCardsTrainingStart(View view) {
        ((TrainingMainFragment) mMenuFragments.get(1)).onClickCardsTrainingStart(view);
    }

    public void onClickCards(View view) {
        ((TrainingMainFragment) mMenuFragments.get(1)).onClickCards(view);
    }

    public void onClickVolumeImageButton(View view) {
        ((IrregularVerbsFragment) mMenuFragments.get(0)).onClickVolumeImageButton(view);
    }

    public void setLocale(String localeName) {
        App.instance.setLocale(localeName);
        updateText();
    }

    public void onClickIrregularRapVolume(View view) {
        ((StoriesFragment) mMenuFragments.get(3)).onClickIrregularRapVolume(view);
    }

    public void updateText() {
        App.asyncInitStringFromRes(new Runnable() {
            @Override
            public void run() {
                for (AnyFragment fragment : mMenuFragments) {
                    fragment.updateText();
                }
                mNavigationDrawerFragment.updateText();
            }
        });
        setTitle(this.getResources().getString(R.string.app_name));
    }

    private void showLanguageDialog() {
        final CharSequence[] items = this.getResources().getStringArray(R.array.language_array);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int position) {
                setLocale(LanguageUtil.getStrId(position));
            }
        }).show();
    }
}
