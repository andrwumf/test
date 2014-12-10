package com.template.mlevytskiy.ui.widget;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import com.template.mlevytskiy.R;
import com.template.mlevytskiy.vo.MorePopularSetting;
import eu.inmite.android.lib.dialogs.ISimpleDialogListener;
import eu.inmite.android.lib.dialogs.SimpleDialogFragment;

/**
 * Created by Макс on 06.04.2014.
 */
public class ChoiceWordGroupDialog extends SimpleDialogFragment {

    private static View rootView;

    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private CheckBox checkBox3;

    private RadioButton word100;
    private RadioButton word50;
    private RadioButton word20;

    private MorePopularSetting morePopularSetting;
    private Runnable afterClickOK;

    public ChoiceWordGroupDialog(MorePopularSetting morePopularSetting,
                                 Runnable afterClickOK) {
        this.morePopularSetting = morePopularSetting;
        this.afterClickOK = afterClickOK;
    }

    public static void show(FragmentActivity activity, MorePopularSetting morePopularSetting,
                            Runnable afterClickOK) {
        activity.setTheme(R.style.DialogStyleLight);
        rootView = LayoutInflater.from(activity).inflate(R.layout.dialog_choise_word_group, null);
        new ChoiceWordGroupDialog(morePopularSetting, afterClickOK).show(
                activity.getSupportFragmentManager(), "test");
    }

    @Override
    public Builder build(Builder builder) {
        builder.setTitle(this.getString(R.string.choice_only));
        builder.setView(rootView);
        builder.setPositiveButton(rootView.getContext().getResources().getString(R.string.ok), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                morePopularSetting.more = checkBox1.isChecked();
                morePopularSetting.normal = checkBox2.isChecked();
                morePopularSetting.less = checkBox3.isChecked();
                if (word100.isChecked()) {
                    morePopularSetting.morePopularWordAmount = 100;
                }
                if (word50.isChecked()) {
                    morePopularSetting.morePopularWordAmount = 50;
                }
                if (word20.isChecked()) {
                    morePopularSetting.morePopularWordAmount = 20;
                }
                afterClickOK.run();
                ISimpleDialogListener listener = getDialogListener();
                if (listener != null) {
                    listener.onPositiveButtonClicked(0);
                }
                dismiss();
            }
        });

        checkBox1 = (CheckBox) rootView.findViewById(R.id.check_box_1);
        checkBox1.setChecked(morePopularSetting.more);
        checkBox2 = (CheckBox) rootView.findViewById(R.id.check_box_2);
        checkBox2.setChecked(morePopularSetting.normal);
        checkBox3 = (CheckBox) rootView.findViewById(R.id.check_box_3);
        checkBox3.setChecked(morePopularSetting.less);

        word100 = (RadioButton) rootView.findViewById(R.id.word_100);
        word100.setChecked(morePopularSetting.morePopularWordAmount == 100);
        word50 = (RadioButton) rootView.findViewById(R.id.word_50);
        word50.setChecked(morePopularSetting.morePopularWordAmount == 50);
        word20 = (RadioButton) rootView.findViewById(R.id.word_20);
        word20.setChecked(morePopularSetting.morePopularWordAmount == 20);
        word100.setEnabled(checkBox1.isChecked());
        word50.setEnabled(checkBox1.isChecked());
        word20.setEnabled(checkBox1.isChecked());

        rootView.findViewById(R.id.linear_layout_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox1.setChecked(!checkBox1.isChecked());
                word100.setEnabled(checkBox1.isChecked());
                word50.setEnabled(checkBox1.isChecked());
                word20.setEnabled(checkBox1.isChecked());
            }
        });

        rootView.findViewById(R.id.linear_layout_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox2.setChecked(!checkBox2.isChecked());
            }
        });

        rootView.findViewById(R.id.linear_layout_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox3.setChecked(!checkBox3.isChecked());
            }
        });

        return builder;
    }

}
