package com.mobi.ledscreen.base;

import android.app.Application;
import android.graphics.Bitmap;


/**
 * Created by waiarl on 2019/5/30.
 */
public class LedApp extends Application {

    private static LedApp instance;
    private Bitmap showBitmap;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initSdk();
    }

    private void initSdk() {

    }

    public static LedApp getInstance() {
        return instance;
    }



    public void setShowBitmap(Bitmap bitmap) {
        if (showBitmap != null) {
            showBitmap.recycle();
        }
        this.showBitmap = bitmap;
    }

    public Bitmap getShowBitmap() {
        return showBitmap;
    }


}
