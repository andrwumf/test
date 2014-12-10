package com.template.mlevytskiy.util.search;

import android.text.Spannable;

import org.apache.commons.lang.StringUtils;

/**
* Created by Макс on 22.04.2014.
*/
public class ContainsInStringArrayDetail {
    public final int startIndex;
    public final int lastIndex;
    public final boolean isEmpty;
    public final int arrayNumber;
    public final boolean isSameLength;

    public ContainsInStringArrayDetail() {
        startIndex = 0;
        lastIndex = 0;
        arrayNumber = 0;
        isEmpty = true;
        isSameLength = false;
    }

    public ContainsInStringArrayDetail(int arrayNumber, int startIndex, int lastIndex, boolean isSameLength) {
        this.arrayNumber = arrayNumber;
        this.startIndex = startIndex;
        this.lastIndex = lastIndex;
        isEmpty = false;
        this.isSameLength = isSameLength;
    }

    public static ContainsInStringArrayDetail contains(Spannable[] args, String str) {
        String[] strs = new String[args.length];
        for(int i = 0; i < strs.length; i++) {
            strs[i] = args[i].toString();
        }
        return contains(strs, str);
    }

    public static ContainsInStringArrayDetail contains(String[] args, String str) {
        for (int i = 0; i < args.length; i++) {
            String item = args[i];
            if (StringUtils.containsIgnoreCase(item, str)) {
                int startIndex = indexOfIgnoreCase(item, str);
                int lastIndex = startIndex + str.length();
                return new ContainsInStringArrayDetail(i, startIndex, lastIndex, item.length() == str.length());
            }
        }
        return new ContainsInStringArrayDetail();
    }

    private static int indexOfIgnoreCase(String str, String searchStr) {
        String[] searchStrArray = new String[] { searchStr.toLowerCase(), searchStr.toUpperCase() };
        return StringUtils.indexOfAny(str, searchStrArray);
    }
}
