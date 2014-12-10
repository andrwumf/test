package com.template.mlevytskiy.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.template.mlevytskiy.App;
import com.template.mlevytskiy.R;
import com.template.mlevytskiy.common.AnyFragment;
import com.template.mlevytskiy.common.memory.MemoryCommunicator;
import com.template.mlevytskiy.memory.Language;
import com.template.mlevytskiy.memory.MemoryKey;
import com.template.mlevytskiy.util.LanguageUtil;

/**
 * Created by Макс on 23.04.2014.
 */
public class SettingsFragment extends AnyFragment {

    private MemoryCommunicator memory;
    private Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        spinner = (Spinner) v.findViewById(R.id.spinner);
        memory = new MemoryCommunicator(MemoryKey.language);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.language_array));
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                App.instance.setLocale(LanguageUtil.getStrId(position));
                ((MainActivity) SettingsFragment.this.getActivity()).updateText();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //doNothing
            }
        });
        return v;
    }

    public void detectLanguage() {
        if (memory.isEmpty()) {
            String language = getResources().getConfiguration().locale.getLanguage();
            int position = LanguageUtil.getPosition(language);
            memory.saveString(language);
            spinner.setSelection(position);
        } else {
            String languageStr = memory.loadString();
            Language language = Language.valueOf(languageStr);
            spinner.setSelection(language.ordinal());
        }
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
}
