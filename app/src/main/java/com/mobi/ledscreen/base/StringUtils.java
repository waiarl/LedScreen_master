package com.mobi.ledscreen.base;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

/**
 * Created by waiarl on 2019/5/17.
 */
public class StringUtils {
    public static String getString(@StringRes int res) {
        return LedApp.getInstance().getString(res);
    }

    @NonNull
    public static String getString(@StringRes int resId, Object... formatArgs) {
        return LedApp.getInstance().getString(resId, formatArgs);
    }
}
