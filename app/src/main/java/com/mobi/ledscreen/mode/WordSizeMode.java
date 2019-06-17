package com.mobi.ledscreen.mode;

import com.mobi.ledscreen.base.BaseMode;
import com.mobi.ledscreen.util.Utils;

/**
 * Created by waiarl on 2019/6/12.
 */
public class WordSizeMode extends BaseMode implements Cloneable {
    public int id;
    public int size;
    public String text;
    public boolean selected = false;

    public WordSizeMode(float size, String text) {
        this.size = Utils.dip2px(size);
        this.text = text;
    }


    @Override
    public WordSizeMode clone() {
        try {
            return (WordSizeMode) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return new WordSizeMode(size, text);
    }
}
