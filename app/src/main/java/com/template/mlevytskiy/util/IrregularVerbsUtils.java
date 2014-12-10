package com.template.mlevytskiy.util;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.template.mlevytskiy.MorePopularIrregularVerbs;
import com.template.mlevytskiy.common.L;
import com.template.mlevytskiy.vo.IrregularVerb;
import com.template.mlevytskiy.vo.SameIrregularVerb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Макс on 08.03.14.
 */
public class IrregularVerbsUtils {

//    private final Integer[] frequentlyUsed = new Integer[] {
//        106, 89, 64, 160, 31, 107, 77, 61, 63, 49, 164, 163, 6,
//            120, 83, 47, 95, 19, 8, 74, 72, 182, 145, 69, 85, 90,
//            111, 91, 104, 93, 125, 134, 86, 97, 66, 88, 45, 110,
//            21, 169, 39, 17, 137, 34, 103, 42, 24, 173, 28
//    };

    private final Integer[] frequentlyUsed = new Integer[] {
            6,7,15,17,18,21,25,28,31,36,39,42,44,46,55,57,58,60,63,66,
            68,71,77,79,80,82,83,84,85,87,89,91,96,97,99,100,103,104,113,118,127,
            130,138,152,155,156,162,170
    };

    public List<IrregularVerb> fromStringsRes(Context context) {
        List<Integer> frequentlyUsedList = Arrays.asList(frequentlyUsed);
        List<IrregularVerb> result = new ArrayList<IrregularVerb>();
        ResourceUtils r = new ResourceUtils(context);
        for (int i = 0; i <= 217; i++) {
            String verbsForm = r.getString("verb_form_" + i);
            if (!TextUtils.isEmpty(verbsForm)) {
                IrregularVerb irregularVerb = createIrregularVerbsFromRes(verbsForm, r.getString("verb_name_" + i),
                        r.getRaw("a" + i), r.getRandomDrawableId(), frequentlyUsedList.contains(i));
                addSameIrregularVerbs(irregularVerb, r, i);
                result.add(irregularVerb);
                irregularVerb.setOldPosition(i);
            }
        }

        Collections.sort(result, new Comparator<IrregularVerb>() {
            @Override
            public int compare(IrregularVerb lhs, IrregularVerb rhs) {
                return (lhs.getForm1()[0].compareTo(rhs.getForm1()[0]));
            }
        });
        L.print("irregular verbs amount=" + result.size());


        // more popular init

        MorePopularIrregularVerbs morePopular = new MorePopularIrregularVerbs();

        for (IrregularVerb irregularVerb : result) {
            morePopular.init(irregularVerb);
            List<SameIrregularVerb> sameIrregularVerbs = irregularVerb.getSameIrregularVerbs();
            for (SameIrregularVerb sameIrregularVerb : sameIrregularVerbs) {
                morePopular.init(sameIrregularVerb);
            }
        }

        return result;
    }

    private void addSameIrregularVerbs(IrregularVerb irregularVerb, ResourceUtils r, int i) {
        for (int j = 0; j < 10; j++) {
            String prefix = r.getString("verb_form_" + i + "_same_"+ j);
            if (!TextUtils.isEmpty(prefix)) {
                String translateStr = r.getString("verb_form_" + i + "_same_"+ j + "_translate");
                String[] translate;
                if (translateStr == null) {
                    translate = new String[] {
                            "i=" + i + " j=" + j
                    };
                } else {
                    translate = translateStr.split(",[ \t]*");
                }
                if (prefix.indexOf(",") == -1) {
                    SameIrregularVerb sameIrregularVerb = new SameIrregularVerb(prefix, translate, irregularVerb);
                    irregularVerb.getSameIrregularVerbs().add(sameIrregularVerb);
                } else {
                    //todo: create complex same irregular verb
                }
            }
        }
    }

    private IrregularVerb createIrregularVerbsFromRes(String englishStr, String translateStr, Uri mp3RawId, int drawableId, boolean isFrequentlyUsed) {
        String[] verb_3_forms = englishStr.split(",[ \t]*");
        String[] form1 = verbFormFromString(verb_3_forms[0]);
        String[] form2 = verbFormFromString(verb_3_forms[1]);
        String[] form3 = verbFormFromString(verb_3_forms[2]);
        String[] translate = translateStr.split(",[ \t]*");
        return new IrregularVerb(form1, form2, form3, translate, mp3RawId, drawableId, isFrequentlyUsed);
    }

    private String[] verbFormFromString(String str) {
        if (str.indexOf("/") != -1) {
            return str.split("/");
        } else {
            return new String[] {str};
        }
    }

}
