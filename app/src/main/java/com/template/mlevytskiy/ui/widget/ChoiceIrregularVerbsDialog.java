package com.template.mlevytskiy.ui.widget;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import com.template.mlevytskiy.R;
import eu.inmite.android.lib.dialogs.BaseDialogFragment;
import eu.inmite.android.lib.dialogs.ISimpleDialogListener;
import eu.inmite.android.lib.dialogs.SimpleDialogFragment;

/**
 * Created by Макс on 06.04.2014.
 */
public class ChoiceIrregularVerbsDialog extends SimpleDialogFragment {

    private static View rootView;

    public static void show(FragmentActivity activity, boolean isAllVerbs, final Runnable allVerbsRunnable, final Runnable frequentlyUsed) {
        rootView = LayoutInflater.from(activity).inflate(R.layout.dialog_radio_buttons, null);

        if (isAllVerbs) {
            ((RadioButton) rootView.findViewById(R.id.all_verbs)).setChecked(true);
        } else {
            ((RadioButton) rootView.findViewById(R.id.frequently)).setChecked(true);
        }

        final ChoiceIrregularVerbsDialog dialog = new ChoiceIrregularVerbsDialog();

        RadioButton allVerbs = (RadioButton) dialog.rootView.findViewById(R.id.all_verbs);
        allVerbs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    allVerbsRunnable.run();
                    dialog.dismiss();
                }
            }
        });

        RadioButton frequently = (RadioButton) dialog.rootView.findViewById(R.id.frequently);
        frequently.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    frequentlyUsed.run();
                    dialog.dismiss();
                }
            }
        });

        dialog.show(activity.getSupportFragmentManager(), "test");
    }

    @Override
    public BaseDialogFragment.Builder build(BaseDialogFragment.Builder builder) {
        builder.setTitle(rootView.getContext().getResources().getString(R.string.choise_verb_for_training));

        builder.setView(rootView);
        builder.setPositiveButton(rootView.getContext().getResources().getString(R.string.cancel), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ISimpleDialogListener listener = getDialogListener();
                if (listener != null) {
                    listener.onPositiveButtonClicked(0);
                }
                dismiss();
            }
        });
        return builder;
    }

}
