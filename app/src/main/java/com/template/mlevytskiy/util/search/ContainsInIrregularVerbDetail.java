package com.template.mlevytskiy.util.search;

/**
* Created by Макс on 22.04.2014.
*/
public class ContainsInIrregularVerbDetail {

    public final Detail detail;
    public final int arrayNumber;
    public final int startIndex;
    public final int lastIndex;
    public final boolean isEmpty;
    public final boolean isStartWith;
    public final boolean isSame;

    public ContainsInIrregularVerbDetail() {
        isEmpty = true;
        startIndex = 0;
        lastIndex = 0;
        detail = null;
        arrayNumber = 0;
        isStartWith = false;
        isSame = false;
    }

    public ContainsInIrregularVerbDetail(Detail detail, int arrayNumber, int startIndex, int lastIndex, boolean sameLength) {
        this.detail = detail;
        this.startIndex = startIndex;
        this.lastIndex = lastIndex;
        this.arrayNumber = arrayNumber;
        isEmpty = false;
        isStartWith = (startIndex == 0);
        isSame = isStartWith ? sameLength : false;
    }

    public ContainsInIrregularVerbDetail(Detail detail, ContainsInStringArrayDetail containsInStringArrayDetail) {
        this.detail = detail;
        this.startIndex = containsInStringArrayDetail.startIndex;
        this.lastIndex = containsInStringArrayDetail.lastIndex;
        this.arrayNumber = containsInStringArrayDetail.arrayNumber;
        isEmpty = false;
        isStartWith = (startIndex == 0);
        isSame = isStartWith ? containsInStringArrayDetail.isSameLength : false;
    }
}
