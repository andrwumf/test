package com.template.mlevytskiy.util.search;

import android.support.v7.widget.SearchView;

/**
 * Project: app
 * User: kitapps1
 * Date: 25.07.13 14:56
 */

public abstract class SearchQuery {
    public SearchQuery(SearchView paramSearchView) {
        paramSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String paramAnonymousString) {
                return SearchQuery.this.onQuery(paramAnonymousString);
            }

            public boolean onQueryTextSubmit(String paramAnonymousString) {
                SearchQuery.this.doNothing();
                return true;
            }
        });
        paramSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            public boolean onClose() {
                SearchQuery.this.doNothing();
                return false;
            }
        });
    }

    private void doNothing() {
    }

    public abstract boolean onQuery(String paramString);

}
