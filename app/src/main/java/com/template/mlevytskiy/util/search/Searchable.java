package com.template.mlevytskiy.util.search;

/**
 * Created by Макс on 22.04.2014.
 */
public interface Searchable {
    ContainsInIrregularVerbDetail contains(String str);
    ContainsInIrregularVerbDetail getLastDetail();
    void setLastDetail(ContainsInIrregularVerbDetail lastDetail);
}
