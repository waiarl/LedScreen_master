package com.mobi.ledscreen.base;

import android.graphics.Color;
import android.view.animation.LinearInterpolator;

import com.mobi.ledscreen.R;
import com.mobi.ledscreen.mode.ColorMode;
import com.mobi.ledscreen.mode.SpeedMode;
import com.mobi.ledscreen.mode.WordSizeMode;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.IntDef;

import static com.mobi.ledscreen.base.Constant.ShowType.TYPE_HAND;
import static com.mobi.ledscreen.base.Constant.ShowType.TYPE_LED;
import static com.mobi.ledscreen.base.Constant.ShowType.TYPE_SUB;

/**
 * Created by waiarl on 2019/5/23.
 */
public interface Constant {
    Random mRandom = new Random();
    LinearInterpolator LINEAR_INTERPOLATOR = new LinearInterpolator();

    String BUNDLE_KEY_ATTRIBUTE_MODE = "bundle_key_attribute_mode";
    String BUNDLE_KEY_SHOW_TYPE = "bundle_key_show_type";

    int REQUEST_CODE_ADD = 10001;
    int RESULT_CODE_ADD = 20001;


    List<SpeedMode> SPEED_LIST = new ArrayList<SpeedMode>() {
        {
            add(new SpeedMode(0, 0 + ""));
            add(new SpeedMode(300, 0.5 + "X"));
            add(new SpeedMode(600, 1 + "X"));
            add(new SpeedMode(900, 1.5 + "x"));
        }
    };

    List<WordSizeMode> WORD_SIZE_LIST = new ArrayList<WordSizeMode>() {
        {
            add(new WordSizeMode(24, 24 + ""));
            add(new WordSizeMode(36, 36 + ""));
            add(new WordSizeMode(48, 48 + ""));
            add(new WordSizeMode(64, 64 + ""));
            add(new WordSizeMode(72, 72 + ""));
        }
    };


    List<ColorMode> COLOR_LIST = new ArrayList<ColorMode>() {
        {
            add(new ColorMode(Color.WHITE, R.drawable.bg_attr_white));
            add(new ColorMode(Color.BLUE, R.drawable.bg_attr_blue));
            add(new ColorMode(Color.GREEN, R.drawable.bg_attr_green));
            add(new ColorMode(Color.CYAN, R.drawable.bg_attr_cyan));
            add(new ColorMode(Color.YELLOW, R.drawable.bg_attr_yellow));
            add(new ColorMode(Color.RED, R.drawable.bg_attr_red));
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TYPE_SUB, TYPE_LED, TYPE_HAND})
    @interface ShowType {
        int TYPE_SUB = 0;
        int TYPE_LED = 1;
        int TYPE_HAND = 2;
    }
}
