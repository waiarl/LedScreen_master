package com.mobi.ledscreen.mode;

import com.mobi.ledscreen.base.BaseMode;

/**
 * Created by waiarl on 2019/6/13.
 */
public class AttributeMode extends BaseMode {
    public int color;
    public int speed;
    public int textSize;
    public String text;

    public AttributeMode() {
    }

    public AttributeMode(int color, int speed, int textSize, String text) {
        this.color = color;
        this.speed = speed;
        this.textSize = textSize;
        this.text = text;
    }
}
