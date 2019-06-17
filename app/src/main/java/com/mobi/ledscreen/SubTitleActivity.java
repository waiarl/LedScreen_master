package com.mobi.ledscreen;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mobi.ledscreen.base.BaseActivity;
import com.mobi.ledscreen.base.BaseObserver;
import com.mobi.ledscreen.base.LedApp;
import com.mobi.ledscreen.view.AttributeView;
import com.mobi.ledscreen.view.SubTitleView;

import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.mobi.ledscreen.base.Constant.ShowType.TYPE_SUB;

/**
 * Created by waiarl on 2019/6/12.
 */
public class SubTitleActivity extends BaseActivity {
    private LinearLayout lin_frame;
    private SubTitleView auto_scroll_view;
    private EditText et_txt;
    private AttributeView attribute_view;
    private ImageView img_start;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_title);
        findViewById();
        setListener();
        init();

    }

    private void findViewById() {
        lin_frame = findViewById(R.id.lin_frame);
        auto_scroll_view = findViewById(R.id.auto_scroll_view);
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
                auto_scroll_view.initText(text).build();
            }
        });

        attribute_view
                .setOnColorChoosedListener((m) -> auto_scroll_view.initColor(m.color).build())
                .setOnSpeedChoosedListener((m) -> auto_scroll_view.initSpeed(m.speed).build())
                .setOnWordSizeChoosedListener((m) -> auto_scroll_view.initTextSize(m.size).build());
        img_start.setOnClickListener(v -> gotoShowActivity());
    }

    private void gotoShowActivity() {
        final Intent intent = new Intent(this, ShowActivity.class);
        final Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_KEY_ATTRIBUTE_MODE, auto_scroll_view.getAttributeMode());
        bundle.putInt(BUNDLE_KEY_SHOW_TYPE,TYPE_SUB);
        intent.putExtras(bundle);
        LedApp.getInstance().setShowBitmap(auto_scroll_view.getBitmap());
        startActivityForResult(intent, REQUEST_CODE_ADD);
    }

    private void init() {
        auto_scroll_view.initView(lin_frame);
        // auto_scroll_view.post(()->auto_scroll_view.build());
        Observable.timer(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        super.onNext(aLong);
                        auto_scroll_view.initText(et_txt.getText().toString()).build();
                    }
                });
    }

    @Override
    public void onDestroy() {
        auto_scroll_view.onDestroy();
        super.onDestroy();
    }
}
