package com.template.mlevytskiy.ui.trainingModule;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.template.mlevytskiy.App;
import com.template.mlevytskiy.R;
import com.template.mlevytskiy.common.AnyFragment;
import com.template.mlevytskiy.common.SideMenuActivity;
import com.template.mlevytskiy.util.FlipStateAnimator;

/**
 * Created by Макс on 10.07.2014.
 */
public class TrainingMainFragment extends AnyFragment {

    private FlipStateAnimator lastFlipStateAnimator = null;
    private Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_training_main, container, false);

        spinner = (Spinner) v.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.word_lists_for_learn_array));
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    App.instance.getTrainingWords().setAmount(TrainingWords.AMOUNT_20);
                } else if (position == 1) {
                    App.instance.getTrainingWords().setAmount(TrainingWords.AMOUNT_50);
                } else if (position == 2) {
                    App.instance.getTrainingWords().setAmount(TrainingWords.AMOUNT_100);
                } else if (position == 3) {
                    App.instance.getTrainingWords().setAmount(TrainingWords.AMOUNT_NORMAL);
                } else if (position == 4) {
                    App.instance.getTrainingWords().setAmount(TrainingWords.AMOUNT_ALL);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //doNothing
            }
        });

        return v;
    }

    public void onClickWriteWordTrainingStart(View view) {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        AnyFragment currentFragment = null;

        for (AnyFragment anyFragment : ((SideMenuActivity) this.getActivity()).otherFragments) {
            if ( anyFragment instanceof TrainingWriteWordFragment ) {
                anyFragment.updateText();
                ft.remove(anyFragment);
                currentFragment = anyFragment;
            }
        }

        if (currentFragment != null) {
            ((SideMenuActivity) this.getActivity()).otherFragments.remove(currentFragment);
        }

        for (AnyFragment anyFragment : ((SideMenuActivity) this.getActivity()).mMenuFragments) {
            ft.hide(anyFragment);
        }

//        if (!hasTrainingWriteWordFragment) {
            TrainingWriteWordFragment fragment = new TrainingWriteWordFragment();
            ((SideMenuActivity) this.getActivity()).otherFragments.add(fragment);
            ft.add(R.id.container, fragment);
//        }

        ft.commit();

        if (lastFlipStateAnimator != null) {
            lastFlipStateAnimator.setCardFaceStateWithoutAnimation();
        }
    }

    public void onClickCardsTrainingStart(View view) {

        //start TrainingCardFragments
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        boolean hasTrainingCardFragment = false;

        for (AnyFragment anyFragment : ((SideMenuActivity) this.getActivity()).otherFragments) {
            if ( anyFragment instanceof TrainingCardFragment ) {
                ((TrainingCardFragment) anyFragment).updateText();
                ft.show(anyFragment);
                hasTrainingCardFragment = true;
            }
        }

        for (AnyFragment anyFragment : ((SideMenuActivity) this.getActivity()).mMenuFragments) {
            ft.hide(anyFragment);
        }

        if (!hasTrainingCardFragment) {
            TrainingCardFragment trainingCardFragment = new TrainingCardFragment();
            ((SideMenuActivity) this.getActivity()).otherFragments.add(trainingCardFragment);
            ft.add(R.id.container, trainingCardFragment);
        }

        ft.commit();

        if (lastFlipStateAnimator != null) {
            lastFlipStateAnimator.setCardFaceStateWithoutAnimation();
        }

    }

    public void onClickCards(View view) {
        if (view.findViewById(R.id.main_activity_card_face) != null) {
            if (
                    true //hasResult
                    ) {
                view.findViewById(R.id.image_button_result_1).setVisibility(View.GONE);
                view.findViewById(R.id.image_button_start_1).setVisibility(View.GONE);
                view.findViewById(R.id.image_button_start_center_1).setVisibility(View.VISIBLE);
            } else {
                //doNothing
            }
            lastFlipStateAnimator = new FlipStateAnimator(view);

        } else {
            if (
                    true //hasResult
                    ) {
                view.findViewById(R.id.image_button_result_2).setVisibility(View.GONE);
                view.findViewById(R.id.image_button_start_2).setVisibility(View.GONE);
                view.findViewById(R.id.image_button_start_center_2).setVisibility(View.VISIBLE);
            } else {
                //doNothing
            }
            lastFlipStateAnimator = new FlipStateAnimator(view, R.id.main_activity_card_face_2, R.id.main_activity_card_back_2);
        }
        lastFlipStateAnimator.changeStateWithAnimation();
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
        ((TextView) this.getView().findViewById(R.id.text_view_1)).setText(this.getString(R.string.cards));
        ((TextView) this.getView().findViewById(R.id.text_view_2)).setText(this.getString(R.string.enter_the_word));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.word_lists_for_learn_array));
        spinner.setAdapter(adapter);
    }
}
