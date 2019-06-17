package com.mobi.ledscreen.mode;

import com.mobi.ledscreen.base.BaseMode;

import androidx.annotation.ColorInt;

/**
 * Created by waiarl on 2019/6/12.
 */
public class ColorMode extends BaseMode implements Cloneable {
     public int drawable;
    public int id;
    @ColorInt
    public int color;
    public boolean selected = false;


    public ColorMode(int color) {
        this.color = color;
    }
    public ColorMode(int color,int drawable) {
        this.color = color;
        this.drawable=drawable;
    }

    @Override
    public ColorMode clone() {
        try {
            return (ColorMode) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return new ColorMode(color,drawable);
    }
}
