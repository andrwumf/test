package com.template.mlevytskiy.vo;

/**
 * Created by Макс on 17.07.2014.
 */
public abstract class MorePopular {

    private boolean morePopular20 = false;
    private boolean morePopular50 = false;
    private boolean morePopular100 = false;
    private boolean lessPopular = false;

    public boolean isMorePopular20() {
        return morePopular20;
    }

    public void setMorePopular20(boolean morePopular20) {
        this.morePopular20 = morePopular20;
    }

    public boolean isMorePopular50() {
        return morePopular50;
    }

    public void setMorePopular50(boolean morePopular50) {
        this.morePopular50 = morePopular50;
    }

    public boolean isMorePopular100() {
        return morePopular100;
    }

    public void setMorePopular100(boolean morePopular100) {
        this.morePopular100 = morePopular100;
    }

    public boolean isLessPopular() {
        return lessPopular;
    }

    public void setLessPopular(boolean lessPopular) {
        this.lessPopular = lessPopular;
    }

    public abstract String getForm1SimpleStr();
}
