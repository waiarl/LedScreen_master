package com.mobi.ledscreen.base;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

/**
 * Created by waiarl on 2019/5/28.
 */
public class ColorUtils {
    @ColorInt
    public static int getColor(@ColorRes int color){
        return ContextCompat.getColor(LedApp.getInstance(),color);
    }
}
