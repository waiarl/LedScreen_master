package com.mobi.ledscreen;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mobi.ledscreen.base.BaseActivity;
import com.mobi.ledscreen.base.BaseObserver;
import com.mobi.ledscreen.base.LedApp;
import com.mobi.ledscreen.view.AttributeView;
import com.mobi.ledscreen.view.LedView;

import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.mobi.ledscreen.base.Constant.ShowType.TYPE_LED;

/**
 * Created by waiarl on 2019/6/12.
 */
public class LedActivity extends BaseActivity {
    private RelativeLayout rel_frame;
    private LedView led_view;
    private EditText et_txt;
    private AttributeView attribute_view;
    private ImageView img_start;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_led);
        findViewById();
        setListener();
        init();

    }

    private void findViewById() {
        rel_frame = findViewById(R.id.rel_frame);
        led_view = findViewById(R.id.led_view);
        et_txt = findViewById(R.id.et_txt);
        attribute_view = findViewById(R.id.attribute_view);
        img_start = findViewById(R.id.img_start);

    }

    private void setListener() {
        et_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                final String text = s.toString();
                led_view.initText(text).build();
            }
        });

        attribute_view
                .setOnColorChoosedListener((m) -> led_view.initColor(m.color).build())
                .setOnSpeedChoosedListener((m) -> led_view.initSpeed(m.speed).build())
                .setOnWordSizeChoosedListener((m) -> led_view.initTextSize(m.size).build());
        img_start.setOnClickListener(v -> gotoShowActivity());

    }

    private void gotoShowActivity() {
        final Intent intent = new Intent(this, ShowActivity.class);
        final Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_KEY_ATTRIBUTE_MODE, led_view.getAttributeMode());
        bundle.putInt(BUNDLE_KEY_SHOW_TYPE,TYPE_LED);
        intent.putExtras(bundle);
        LedApp.getInstance().setShowBitmap(led_view.getBitmap());
        startActivityForResult(intent, REQUEST_CODE_ADD);
    }

    private void init() {
        led_view.initView(rel_frame);
        // led_view.post(()->led_view.build());
        Observable.timer(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        super.onNext(aLong);
                        led_view.initText(et_txt.getText().toString()).build();
                    }
                });

    }

    @Override
    public void onDestroy() {
        led_view.onDestroy();
        super.onDestroy();
    }

}
