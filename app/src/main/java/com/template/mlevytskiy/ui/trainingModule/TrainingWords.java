package com.template.mlevytskiy.ui.trainingModule;

/**
 * Created by Макс on 23.07.2014.
 */
public class TrainingWords {

    public static final int AMOUNT_20 = 1;
    public static final int AMOUNT_50 = 2;
    public static final int AMOUNT_100 = 3;
    public static final int AMOUNT_NORMAL = 4;
    public static final int AMOUNT_ALL = 5;

    private int amount = AMOUNT_20;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
