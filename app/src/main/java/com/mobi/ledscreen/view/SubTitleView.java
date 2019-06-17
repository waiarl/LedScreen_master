package com.mobi.ledscreen.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.mobi.ledscreen.base.Constant;
import com.mobi.ledscreen.mode.AttributeMode;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by waiarl on 2019/6/12.
 */
public class SubTitleView extends View implements Constant {
    private final Context mContext;
    private TextPaint mPaint;
    private ViewGroup parent;
    private int mWidth;
    private int mHeight;
    private AttributeMode attributeMode;
    private int contentHeight;
    private Bitmap mBitmap;

    public SubTitleView(Context context) {
        this(context, null);
    }

    public SubTitleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SubTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        final int textSize = WORD_SIZE_LIST.get(0).size;
        final int color = COLOR_LIST.get(0).color;
        int speed = SPEED_LIST.get(0).speed;
        attributeMode = new AttributeMode(color, speed, textSize, "");

        mPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(textSize);
        mPaint.setColor(color);
    }

    public SubTitleView initView(@NonNull ViewGroup parent) {
        this.parent = parent;
        return this;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final StaticLayout staticLayout = new StaticLayout(attributeMode.text, mPaint, Integer.MAX_VALUE, Layout.Alignment.ALIGN_NORMAL, 1f, 0f, false);
        final int width = (int) staticLayout.getLineWidth(0);
        mHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        contentHeight = staticLayout.getLineBottom(0);
        mWidth = width;
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawText(canvas);
    }

    private void drawText(Canvas canvas) {
        if(mWidth==0||mHeight==0){
            return;
        }
        resetBitmap();
        final Canvas bitmapCanvas = new Canvas(mBitmap);
        final StaticLayout staticLayout = new StaticLayout(attributeMode.text, mPaint, Integer.MAX_VALUE, Layout.Alignment.ALIGN_NORMAL, 1f, 0f, false);
        bitmapCanvas.save();
        bitmapCanvas.translate(0, (mHeight - contentHeight) / 2);
        staticLayout.draw(bitmapCanvas);
        bitmapCanvas.restore();
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    private void resetBitmap() {
        if (mBitmap != null) {
            mBitmap.recycle();
        }
        mBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
    }


    private void autoScroll() {
        animate().setListener(null).cancel();
        if (parent == null || mWidth == 0) {
            return;
        }
        if (attributeMode.speed == 0) {
            setTranslationX(0);
            return;
        }
        final int parentWidth = parent.getMeasuredWidth();
        if (parentWidth == 0) {
            return;
        }
        final float currentX = getTranslationX();
        float tw = -mWidth;
        final long du = (long) Math.abs((tw - currentX) / attributeMode.speed) * 1000;
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

    }

    /**************************************************************************************************************************/

    public SubTitleView initSpeed(int speed) {
        this.attributeMode.speed = speed;
        return this;
    }

    public SubTitleView initTextSize(int textSize) {
        this.attributeMode.textSize = textSize;
        mPaint.setTextSize(textSize);
        return this;
    }

    public SubTitleView initColor(@ColorInt int color) {
        this.attributeMode.color = color;
        mPaint.setColor(color);
        return this;
    }

    public SubTitleView initText(String text) {
        this.attributeMode.text = text;
        return this;
    }

    public SubTitleView build() {
        requestLayout();
        return this;
    }


    public void onDestroy() {
        onPause();
        if (mBitmap != null) {
            mBitmap.recycle();
        }
    }

    public void onPause() {
        animate().setListener(null).cancel();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        autoScroll();
    }

    public AttributeMode getAttributeMode() {
        return attributeMode;
    }

    public Bitmap getBitmap() {
        if (mBitmap == null||mBitmap.isRecycled()) {
            return null;
        }
        return mBitmap.copy(Bitmap.Config.ARGB_8888, true);
    }
}
