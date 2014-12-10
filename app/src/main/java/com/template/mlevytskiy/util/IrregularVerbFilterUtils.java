package com.template.mlevytskiy.util;

import com.template.mlevytskiy.ui.trainingModule.TrainingWords;
import com.template.mlevytskiy.vo.IrregularVerb;
import com.template.mlevytskiy.vo.MorePopular;
import com.template.mlevytskiy.vo.MorePopularSetting;
import com.template.mlevytskiy.vo.SameIrregularVerb;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Макс on 20.07.2014.
 */
public class IrregularVerbFilterUtils {

    public List<IrregularVerb> filter(List<IrregularVerb> list, int wordsAmount) {
        MorePopularSetting morePopularSetting = new MorePopularSetting();
        if (wordsAmount == TrainingWords.AMOUNT_20) {
            morePopularSetting.morePopularWordAmount = 20;
            morePopularSetting.more = true;
        } else if (wordsAmount == TrainingWords.AMOUNT_50) {
            morePopularSetting.morePopularWordAmount = 50;
            morePopularSetting.more = true;
        } else if (wordsAmount == TrainingWords.AMOUNT_100) {
            morePopularSetting.morePopularWordAmount = 100;
            morePopularSetting.more = true;
        } else if (wordsAmount == TrainingWords.AMOUNT_NORMAL) {
            morePopularSetting.morePopularWordAmount = 100;
            morePopularSetting.more = true;
            morePopularSetting.normal = true;
        } else if (wordsAmount == TrainingWords.AMOUNT_ALL) {
            return list;
        }
        return filter(list, morePopularSetting);
    }

    public List<IrregularVerb> filter(List<IrregularVerb> list, MorePopularSetting morePopularSetting) {
        List<IrregularVerb> other = new ArrayList<IrregularVerb>();
        List<IrregularVerb> result = abstractFilter(list, morePopularSetting, other);

        for (IrregularVerb irregularVerb : result) {
            List<SameIrregularVerb> oldList = irregularVerb.getSameIrregularVerbs();
            List<SameIrregularVerb> newList = filterSame(oldList, morePopularSetting);
            if (newList.size() != oldList.size()) {
                oldList.clear();
                oldList.addAll(newList);
            }
        }

        for (IrregularVerb irregularVerb : other) {
            List<SameIrregularVerb> oldList = irregularVerb.getSameIrregularVerbs();
            List<SameIrregularVerb> newList = filterSame(oldList, morePopularSetting);
            if (!newList.isEmpty()) {
                result.add(irregularVerb);
                if (newList.size() != oldList.size()) {
                    oldList.clear();
                    oldList.addAll(newList);
                }
            }
        }

        return result;
    }

    private List<SameIrregularVerb> filterSame(List<SameIrregularVerb> list, MorePopularSetting morePopularSetting) {
        return abstractFilter(list, morePopularSetting);
    }

    private <T extends MorePopular> List<T> abstractFilter(List<T> list, MorePopularSetting morePopularSetting) {
        return abstractFilter(list, morePopularSetting, new ArrayList<T>());
    }
    
    private <T extends MorePopular> List<T> abstractFilter(List<T> list, MorePopularSetting morePopularSetting, List<T> other) {
        List<T> result = new ArrayList<T>();

        for (T irregularVerb : list) {
            boolean isMorePopular;
            if (morePopularSetting.morePopularWordAmount == 100) {
                isMorePopular = irregularVerb.isMorePopular100();
            } else if (morePopularSetting.morePopularWordAmount == 50) {
                isMorePopular = irregularVerb.isMorePopular50();
            } else {
                isMorePopular = irregularVerb.isMorePopular20();
            }

            if (morePopularSetting.less && irregularVerb.isLessPopular()) {
                result.add(irregularVerb);
                continue;
            }

            if (morePopularSetting.normal && (!irregularVerb.isLessPopular() && !isMorePopular)) {
                result.add(irregularVerb);
                continue;
            }

            if (morePopularSetting.more && isMorePopular) {
                result.add(irregularVerb);
                continue;
            }

            other.add(irregularVerb);

        }
        return result;
    }
}
