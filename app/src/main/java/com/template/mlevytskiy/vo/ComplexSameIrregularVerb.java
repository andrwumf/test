package com.template.mlevytskiy.vo;

import android.text.Spannable;

/**
 * Created by Макс on 21.04.2014.
 */
public class ComplexSameIrregularVerb extends SameIrregularVerb {

    private String[] mForm1;
    private String[] mForm2;
    private String[] mForm3;

    public ComplexSameIrregularVerb(String prefix, String[] translate, String[] form1, String[] form2,
                                    String[] form3, IrregularVerb irregularVerb) {
        super(prefix, translate, irregularVerb);
        mForm1 = form1;
        mForm2 = form2;
        mForm3 = form3;
    }

    public Spannable[] getForm2() {
        return null;
    }

    public Spannable[] getForm3() {
        return null;
    }

    public Spannable[] getForm1() {
        return null;
    }
}
