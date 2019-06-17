package com.mobi.ledscreen.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.mobi.ledscreen.base.Constant;
import com.mobi.ledscreen.mode.AttributeMode;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

/**
 * Created by waiarl on 2019/6/12.
 */
public class HandDrawView extends View implements Constant {
    private final Context mContext;
    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private Bitmap maskBitmap;
    private Canvas maskCanvas;
    private Bitmap maskBgBitmap;
    private AttributeMode attributeMode;

    public HandDrawView(Context context) {
        this(context, null);
    }

    public HandDrawView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HandDrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        final int textSize = WORD_SIZE_LIST.get(0).size;
        final int color = COLOR_LIST.get(0).color;
        int speed = SPEED_LIST.get(0).speed;
        attributeMode = new AttributeMode(color, speed, textSize, "");

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
        build();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        saveLayers(canvas);
        drawMask(canvas);
    }

    private void saveLayers(Canvas canvas) {
        canvas.saveLayer(0, 0, mWidth, mHeight, null);
    }


    private void drawMask(Canvas canvas) {
        if(mWidth==0||mHeight==0){
            return;
        }
        if (maskBitmap == null || maskBitmap.isRecycled()) {
            maskBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
            maskCanvas = new Canvas(maskBitmap);

            // final Bitmap maskBgBitmap = getMaskBgBitmap();
            //maskCanvas.drawBitmap(maskBgBitmap, 0, 0, null);
        }
        canvas.drawBitmap(maskBitmap, 0, 0, null);
    }

    private Bitmap getMaskBgBitmap() {
        if (maskBgBitmap == null || maskBgBitmap.isRecycled()) {
        }
        return maskBgBitmap;
    }

    private float startX;
    private float startY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                drawLine(startX, startY, event.getX(), event.getY());
                startX = event.getX();
                startY = event.getY();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                drawLine(startX, startY, event.getX(), event.getY());
                break;
        }


        return true;

    }

    private void drawLine(float startX, float startY, float endX, float endY) {
        if (maskCanvas == null) {
            return;
        }
        maskCanvas.drawLine(startX, startY, endX, endY, mPaint);
        invalidate();
    }


    public HandDrawView initSpeed(int speed) {
        this.attributeMode.speed = speed;
        return this;
    }

    public HandDrawView initTextSize(int textSize) {
        this.attributeMode.textSize = textSize;
        return this;
    }

    public HandDrawView initColor(@ColorInt int color) {
        this.attributeMode.color = color;
        return this;
    }

    public HandDrawView clear() {
        if (maskCanvas == null) {
            return this;
        }
        maskCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        invalidate();
        return this;
    }

    public HandDrawView build() {
        mPaint.setStrokeWidth(attributeMode.textSize / 4);
        mPaint.setColor(attributeMode.color);
        return this;
    }


    public void onDestroy() {
        onPause();
        if (maskBitmap != null) {
            maskBitmap.recycle();
        }
    }

    public void onPause() {
        // animate().setListener(null).cancel();
    }

    public AttributeMode getAttributeMode() {
        return attributeMode;
    }

    public Bitmap getBitmap() {
        if (maskBitmap == null || maskBitmap.isRecycled()) {
            return null;
        }
        return maskBitmap.copy(Bitmap.Config.ARGB_8888, true);
    }
}
