package com.template.mlevytskiy.ui.trainingModule;

import android.graphics.Color;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.template.mlevytskiy.R;
import com.template.mlevytskiy.ui.widget.CustomTextChangeListener;
import com.template.mlevytskiy.vo.IrregularVerb;

/**
 * Created by Макс on 01.08.2014.
 */
public class TrainingWriteWordSimpleAdapter extends BaseAdapter {

    private static final int USUALLY = 0;
    private static final int SECRET = 1;
    private IrregularVerb irregularVerb;
    private int secret;
    private Button nextButton;
    private EditText editText;
    private TextView correctWordsTextView;
    private TextView incorrectWords;


    public TrainingWriteWordSimpleAdapter(IrregularVerb irregularVerb, int secret, Button nextButton,
                                          TextView correctWords, TextView incorrectWords) {
        this.irregularVerb = irregularVerb;
        this.secret = secret;
        this.nextButton = nextButton;
        nextButton.setEnabled(false);
        this.correctWordsTextView = correctWords;
        this.incorrectWords = incorrectWords;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public int getItemViewType(int position) {
        if (position == secret) {
            return SECRET;
        } else {
            return USUALLY;
        }
    }

    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        String[] words = getWords(irregularVerb, position);

        View view;
        if (getItemViewType(position) == SECRET) {
            view = View.inflate(parent.getContext(), R.layout.adapter_training_write_word_2, null);
            final String[] correctWords = getWords(irregularVerb, position);
            final Button askButton = (Button) view.findViewById(R.id.ask_button);
            final Button trueButton = (Button) view.findViewById(R.id.true_button);
            final Button falseButton = (Button) view.findViewById(R.id.false_button);
            final TextView correctTextView = ((TextView) view.findViewById(R.id.text_view));
            editText = ((EditText) view.findViewById(R.id.edit_text));
            editText.setInputType(editText.getInputType()
                    | EditorInfo.TYPE_TEXT_FLAG_NO_SUGGESTIONS
                    | EditorInfo.TYPE_TEXT_VARIATION_FILTER);

            askButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    correctTextView.setVisibility(View.VISIBLE);
                    falseButton.setVisibility(View.VISIBLE);
                    askButton.setVisibility(View.GONE);
                    trueButton.setVisibility(View.GONE);
                    nextButton.setEnabled(true);
                    editText.setPaintFlags(editText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    editText.setEnabled(false);
                    if (correctTextView.getText().length() >= 12) {
                        editText.setText("");
                    }

                    String wordsStr = incorrectWords.getText().toString();
                    int wordsInt = Integer.parseInt(wordsStr);
                    wordsInt = wordsInt + 1;
                    incorrectWords.setText(String.valueOf(wordsInt));
                }
            });
            editText.addTextChangedListener(new CustomTextChangeListener() {
                @Override
                public void change(String oldText, Editable newText) {
                    if (falseButton.getVisibility() == View.VISIBLE) {
                        return;
                    }
                    String correctWord1 = "xxxxx";
                    boolean condition = false;
                    if (position == 0) {
                        if (newText.length() >=3) {
                            for (String correctWord : correctWords) {
                                condition = correctWord.startsWith(newText.toString().toLowerCase());
                                if (condition) {
                                    correctWord1 = correctWord;
                                    break;
                                }
                            }
                        } else {
                            condition = false;
                        }
                    } else {
                        for (String correctWord : correctWords) {
                            condition = TextUtils.equals(correctWord, newText.toString().toLowerCase());
                            if (condition) {
                                correctWord1 = correctWord;
                                break;
                            }
                        }
                    }

                    if (condition) {
                        if (nextButton.isEnabled()) {
                            return;
                        }
                        nextButton.setEnabled(true);
                        askButton.setText("+");
                        editText.setEnabled(false);
                        editText.setTextColor(Color.BLACK);
                        askButton.setVisibility(View.GONE);
                        trueButton.setVisibility(View.VISIBLE);

                        if (position == 0) {
                            editText.setTextColor(0xDf669900);
                            newText.append(correctWord1.substring(newText.length()));
                        }

                        String wordsStr = correctWordsTextView.getText().toString();
                        int wordsInt = Integer.parseInt(wordsStr);
                        wordsInt = wordsInt + 1;
                        correctWordsTextView.setText(String.valueOf(wordsInt));
                    } else {

                        if (nextButton.isEnabled()) {
                            askButton.setText("?");
                            askButton.setVisibility(View.VISIBLE);
                            trueButton.setVisibility(View.GONE);
                            nextButton.setEnabled(false);
                        }

                        if ((position == 0)) {
                            if (newText.length() >=3) {
                                editText.setTextColor(Color.RED);
                            } else {
                                editText.setTextColor(Color.BLACK);
                            }
                        }
                    }
                }
            });
        } else {
            view = View.inflate(parent.getContext(), R.layout.adapter_training_write_word, null);
        }
        ((TextView) view.findViewById(R.id.text_view)).setText(TextUtils.join(", ", words));
        return view;
    }

    private String[] getWords(IrregularVerb irregularVerb, int position) {
        if (position == 0) {
            return irregularVerb.getTranslate();
        } else if (position == 1) {
            return irregularVerb.getForm1();
        } else if (position == 2) {
            return irregularVerb.getForm2();
        } else if (position == 3) {
            return irregularVerb.getForm3();
        }
        return null;
    }
}
