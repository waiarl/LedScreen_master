package com.mobi.ledscreen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.mobi.ledscreen.base.BaseActivity;
import com.mobi.ledscreen.base.BaseObserver;

import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by waiarl on 2019/5/24.
 */
public class Welcome extends BaseActivity {
    private ImageView img_bg;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        findViewById();
        init();
    }




    private void findViewById() {
        img_bg = findViewById(R.id.img_bg);
    }

    private void init() {
        Observable.timer(2500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        super.onNext(aLong);
                        gotoMain();
                    }
                });

    }

    private void gotoMain() {
        final Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
    }
}
