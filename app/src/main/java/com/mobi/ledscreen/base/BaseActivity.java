package com.mobi.ledscreen.base;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by waiarl on 2019/5/23.
 */
public class BaseActivity extends AppCompatActivity implements Constant{
    protected String TAG=getClass().getSimpleName();


    private List<Object> adsList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adsList = new ArrayList<>();
    }


    protected void adAds(Object ads) {
    }


}
