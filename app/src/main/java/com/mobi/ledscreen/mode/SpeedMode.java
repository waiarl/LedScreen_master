package com.mobi.ledscreen.mode;

import com.mobi.ledscreen.base.BaseMode;

/**
 * Created by waiarl on 2019/6/12.
 */
public class SpeedMode extends BaseMode implements Cloneable {
    public int id;
    public int speed;
    public String text;

    public boolean selected = false;

    public SpeedMode(int speed, String text) {
        this.speed = speed;
        this.text = text;
    }

    @Override
    public SpeedMode clone() {
        try {
            return (SpeedMode) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return new SpeedMode(speed, text);
    }
}
