package com.mobi.ledscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.mobi.ledscreen.base.BaseActivity;

public class MainActivity extends BaseActivity {

    private TextView txt_sub;
    private TextView txt_led;
    private TextView txt_hand;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
        setListener();
    }

    private void findViewById() {
        txt_sub = findViewById(R.id.txt_sub);
        txt_led = findViewById(R.id.txt_led);
        txt_hand = findViewById(R.id.txt_hand);

    }

    private void setListener() {
        txt_sub.setOnClickListener(v -> gotoSub());
        txt_led.setOnClickListener(v -> gotoLed());
        txt_hand.setOnClickListener(v -> gotoHand());
    }


    private void gotoHand() {
        gotoActivity(HandPaintActivity.class);
    }

    private void gotoLed() {
        gotoActivity(LedActivity.class);
    }

    private void gotoSub() {
        gotoActivity(SubTitleActivity.class);
    }

    private <T extends Activity> void gotoActivity(Class<T> activityClass) {
        final Intent intent = new Intent(this, activityClass);
        startActivityForResult(intent, REQUEST_CODE_ADD);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        moveTaskToBack(false);
    }
}
