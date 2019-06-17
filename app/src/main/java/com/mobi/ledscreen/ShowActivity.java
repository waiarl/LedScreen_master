package com.mobi.ledscreen;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mobi.ledscreen.base.BaseActivity;
import com.mobi.ledscreen.base.LedApp;
import com.mobi.ledscreen.mode.AttributeMode;
import com.mobi.ledscreen.view.ShowView;

import androidx.annotation.Nullable;

import static com.mobi.ledscreen.base.Constant.ShowType.TYPE_SUB;


/**
 * Created by waiarl on 2019/6/13.
 */
public class ShowActivity extends BaseActivity {
    private AttributeMode mode;
    private ShowView show_view;
    private RelativeLayout rel_bg;
    @ShowType
    private int type;
    private ImageView img_led;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        getIntentData();
        findViewById();
        setListener();
        initView();
    }

    private void getIntentData() {
        final Bundle bundle = getIntent().getExtras();
        mode = (AttributeMode) bundle.getSerializable(BUNDLE_KEY_ATTRIBUTE_MODE);
        type = bundle.getInt(BUNDLE_KEY_SHOW_TYPE, TYPE_SUB);
    }

    private void findViewById() {
        rel_bg = findViewById(R.id.rel_bg);
        show_view = findViewById(R.id.show_view);
        img_led = findViewById(R.id.img_led);
    }

    private void initView() {
        // img_led.setVisibility(type == TYPE_LED ? View.VISIBLE : View.GONE);
        img_led.setVisibility(View.GONE);
        show_view.initView(rel_bg, LedApp.getInstance().getShowBitmap(), mode.speed);
        show_view.post(() -> show_view.build());
    }

    private void setListener() {

    }

}
