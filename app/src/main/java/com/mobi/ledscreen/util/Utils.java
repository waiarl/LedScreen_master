package com.mobi.ledscreen.util;

import android.content.Context;

import com.mobi.ledscreen.base.Constant;
import com.mobi.ledscreen.base.LedApp;


/**
 * Created by waiarl on 2019/5/23.
 */
public class Utils implements Constant {

    public static int dip2px(Context context, float dpValue) {
        float result = Math.round(context.getResources().getDisplayMetrics().density * dpValue);
        return (int) result;

    }
    public static int dip2px( float dpValue) {
        float result = Math.round(LedApp.getInstance().getResources().getDisplayMetrics().density * dpValue);
        return (int) result;

    }

    public static final String getPackageName(){
        String name= LedApp.getInstance().getPackageName();
        return name;
    }

}
