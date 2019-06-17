package com.mobi.ledscreen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobi.ledscreen.base.BaseActivity;
import com.mobi.ledscreen.base.LedApp;
import com.mobi.ledscreen.view.AttributeView;
import com.mobi.ledscreen.view.HandDrawView;

import androidx.annotation.Nullable;

import static com.mobi.ledscreen.base.Constant.ShowType.TYPE_SUB;

/**
 * Created by waiarl on 2019/6/12.
 */
public class HandPaintActivity extends BaseActivity {
    private RelativeLayout rel_frame;
    private HandDrawView hand_draw_view;
    private EditText et_txt;
    private AttributeView attribute_view;
    private ImageView img_start;
    private TextView txt_clear;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hand_paint);
        findViewById();
        setListener();
        init();

    }

    private void findViewById() {
        rel_frame = findViewById(R.id.rel_frame);
        hand_draw_view = findViewById(R.id.hand_draw_view);
        et_txt = findViewById(R.id.et_txt);
        attribute_view = findViewById(R.id.attribute_view);
        img_start = findViewById(R.id.img_start);
        txt_clear=findViewById(R.id.txt_clear);

    }

    private void setListener() {
        attribute_view
                .setOnColorChoosedListener((m) -> hand_draw_view.initColor(m.color).build())
                .setOnSpeedChoosedListener((m) -> hand_draw_view.initSpeed(m.speed).build())
                .setOnWordSizeChoosedListener((m) -> hand_draw_view.initTextSize(m.size).build());
        img_start.setOnClickListener(v -> gotoShowActivity());
        txt_clear.setOnClickListener(v->hand_draw_view.clear());

    }

    private void gotoShowActivity() {
        final Intent intent = new Intent(this, ShowActivity.class);
        final Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_KEY_ATTRIBUTE_MODE, hand_draw_view.getAttributeMode());
        bundle.putInt(BUNDLE_KEY_SHOW_TYPE, TYPE_SUB);
        intent.putExtras(bundle);
        LedApp.getInstance().setShowBitmap(hand_draw_view.getBitmap());
        startActivityForResult(intent, REQUEST_CODE_ADD);
    }

    private void init() {

    }

    @Override
    public void onDestroy() {
        hand_draw_view.onDestroy();
        super.onDestroy();
    }

}
