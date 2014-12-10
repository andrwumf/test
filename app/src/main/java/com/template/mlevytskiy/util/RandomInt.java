package com.template.mlevytskiy.util;

import java.util.Random;

/**
 * Created by Макс on 03.08.2014.
 */
public class RandomInt {

    public int get(int min, int max) {
        Random random = new Random();
        return random.nextInt(max + 1 - min) + min;
    }
}
