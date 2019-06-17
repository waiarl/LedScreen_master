package com.mobi.ledscreen.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.mobi.ledscreen.base.Constant;

import androidx.annotation.Nullable;

/**
 * Created by waiarl on 2019/6/13.
 */
public class ShowView extends View implements Constant {
    private final Context mContext;
    private Bitmap bitmap;
    private int speed;
    private ViewGroup parent;
    private float scale;
    private int mHeight;
    private int mWidth;

    public ShowView(Context context) {
        this(context, null);
    }

    public ShowView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {

    }

    public ShowView initView(ViewGroup parent, Bitmap bitmap, int speed) {
        this.parent = parent;
        this.bitmap = bitmap;
        this.speed = speed;
        return this;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //  super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        mWidth = 0;
        if (bitmap != null) {
            final int bh = bitmap.getHeight();
            scale = mHeight / (float) bh;
            mWidth = (int) (bitmap.getWidth() * scale);
        }
        setMeasuredDimension(mWidth, mHeight);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBitmap(canvas);
    }

    private void drawBitmap(Canvas canvas) {
        if (bitmap == null) {
            return;
        }
        final Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF dst = new RectF(0, 0, mWidth, mHeight);
        canvas.drawBitmap(bitmap, src, dst, null);
    }

    public ShowView build() {
        autoScroll();
        return this;
    }

    public ShowView autoScroll() {
        if (bitmap == null || parent == null || parent.getMeasuredWidth() == 0) {
            return this;
        }
        animate().setListener(null).cancel();
        final int parentWidth = parent.getMeasuredWidth();
        if (speed == 0) {
            setTranslationX(0);
            return this;
        }
        setTranslationX(parentWidth);
        final float currentX = getTranslationX();
        float tw = -mWidth;
        final long du = (long) Math.abs((tw - currentX) / speed) * 1000;
        animate().translationX(tw).setDuration(du)
                .setInterpolator(LINEAR_INTERPOLATOR)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        setTranslationX(parentWidth);
                        autoScroll();
                    }
                }).start();
        return this;
    }
}
