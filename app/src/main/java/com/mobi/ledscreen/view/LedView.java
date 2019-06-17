package com.mobi.ledscreen.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.mobi.ledscreen.base.Constant;
import com.mobi.ledscreen.mode.AttributeMode;

import java.util.Arrays;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by waiarl on 2019/6/12.
 */
public class LedView extends View implements Constant {
    private final Context mContext;
    private TextPaint mPaint;
    private ViewGroup parent;
    private int mWidth;
    private int mHeight;
    private AttributeMode attributeMode;
    private int contentHeight;
    private Bitmap mBitmap;
    private int ledRadius;
    private float ledDivider;
    private int contentWidth;
    private Bitmap contentBitmap;

    public LedView(Context context) {
        this(context, null);
    }

    public LedView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        final int textSize = WORD_SIZE_LIST.get(0).size;
        final int color = COLOR_LIST.get(0).color;
        int speed = SPEED_LIST.get(0).speed;
        attributeMode = new AttributeMode(color, speed, textSize, "");
        ledRadius = 5;
        ledDivider = ledRadius * 0.2f;

        mPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(textSize);
        mPaint.setColor(color);
        mPaint.setFakeBoldText(true);
    }

    public LedView initView(@NonNull ViewGroup parent) {
        this.parent = parent;
        return this;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final StaticLayout staticLayout = new StaticLayout(attributeMode.text, mPaint, Integer.MAX_VALUE, Layout.Alignment.ALIGN_NORMAL, 1f, 0f, false);
        contentWidth = (int) staticLayout.getLineWidth(0);
        mHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        contentHeight = staticLayout.getLineBottom(0);
        mWidth = contentWidth + 2 * ledRadius;
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawText(canvas);
    }

    private void drawText(Canvas canvas) {
        if (mHeight == 0 || mWidth == 0 || contentWidth == 0 || contentHeight == 0) {
            return;
        }
        resetBitmap();
        final Canvas bitmapCanvas = new Canvas(mBitmap);
        final Canvas contentCanvas = new Canvas(contentBitmap);
        final StaticLayout staticLayout = new StaticLayout(attributeMode.text, mPaint, Integer.MAX_VALUE, Layout.Alignment.ALIGN_NORMAL, 1f, 0f, false);
        contentCanvas.save();
        contentCanvas.translate(0, (mHeight - contentHeight) / 2);
        staticLayout.draw(contentCanvas);
        contentCanvas.restore();
        resetLedFrame(bitmapCanvas);
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    private void resetLedFrame(Canvas bitmapCanvas) {
        final int width = contentBitmap.getWidth();
        final int height = contentBitmap.getHeight();
        LedFrame ledFrame = new LedFrame(height, width);
        contentBitmap.getPixels(ledFrame.pixels, 0, width, 0, 0, width, height);
        for (int i = 0; i < height; i++) {
            ledFrame.frame[i] = Arrays.copyOfRange(ledFrame.pixels, i * width, (i + 1) * width);
        }
        drawLedBitmap(bitmapCanvas, ledFrame);
    }

    public void drawLedBitmap(Canvas canvas, LedFrame led) {
        if (led.frame.length == 0 || led.frame[0].length == 0) {
            return;
        }
        float xOffset = ledRadius;
        float yOffset = ledRadius;
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        int w = led.frame[0].length;
        int h = led.frame.length;
        int p = 0;
        int q = 0;
        mPaint.setColor(attributeMode.color);
        while (p < h) {
            q=0;
            while (q < w) {
                if (led.frame[p][q] != 0) {
                     canvas.drawCircle(q+xOffset,p+yOffset,ledRadius,mPaint);
                }
                q+= 2*ledRadius+ledDivider;
            }
            p += 2*ledRadius+ledDivider;
        }
    }

    private void resetBitmap() {
        if (mBitmap != null) {
            mBitmap.recycle();
        }
        mBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        if (contentBitmap != null) {
            contentBitmap.recycle();
        }
        contentBitmap = Bitmap.createBitmap(contentWidth, mHeight, Bitmap.Config.ARGB_8888);
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

    public LedView initSpeed(int speed) {
        this.attributeMode.speed = speed;
        return this;
    }

    public LedView initTextSize(int textSize) {
        this.attributeMode.textSize = textSize;
        mPaint.setTextSize(textSize);
        return this;
    }

    public LedView initColor(@ColorInt int color) {
        this.attributeMode.color = color;
        mPaint.setColor(color);
        return this;
    }

    public LedView initText(String text) {
        this.attributeMode.text = text;
        return this;
    }

    public LedView build() {
        requestLayout();
        return this;
    }


    public void onDestroy() {
        onPause();
        if (mBitmap != null) {
            mBitmap.recycle();
        }
        if(contentBitmap!=null){
            contentBitmap.recycle();
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
        if (mBitmap == null || mBitmap.isRecycled()) {
            return null;
        }
        return mBitmap.copy(Bitmap.Config.ARGB_8888, true);
    }


    static class LedFrame {
        @ColorInt
        int[] pixels;
        int[][] frame;

        public LedFrame(int width, int height) {
            frame = new int[width][height];
            pixels = new int[width * height];
        }

        public void setData(int x, int y, int data) {
            frame[x][y] = data;
        }

    }


}
