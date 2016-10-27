package com.zcj.colorfulsystembar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.zcj.colorfulsystembar.R;


/**
 * Created by zcj on 2016/10/25.
 */

public class MaskImageView extends ImageView {
    private int mMaskColor = 0x00000000;
    private int mMaskAlpha = 0;
    private Paint mMaskPaint;

    public MaskImageView(Context context) {
        this(context, null);
    }

    public MaskImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaskImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MaskImageView);

        if (null != a) {
            mMaskAlpha = a.getInt(R.styleable.MaskImageView_maskAlpha, mMaskAlpha);
            mMaskColor = a.getColor(R.styleable.MaskImageView_maskColor, mMaskColor);
        }

        a.recycle();

        mMaskPaint = new Paint();
        mMaskPaint.setAntiAlias(true);
        mMaskPaint.setStyle(Paint.Style.FILL);
        mMaskPaint.setColor(mMaskColor);
        mMaskPaint.setAlpha(mMaskAlpha);
    }

    public void setMask(int alpha, int color) {
        if (alpha > 255) alpha = 255;
        if (alpha < 0) alpha = 0;
        mMaskPaint.setColor(color);
        mMaskPaint.setAlpha(alpha);
        postInvalidate();
    }

    private Rect clipBounds;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        clipBounds = canvas.getClipBounds();
        canvas.drawRect(clipBounds.left + getPaddingLeft(), clipBounds.top + getPaddingTop(), clipBounds.right - getPaddingRight(), clipBounds.bottom - getPaddingBottom(), mMaskPaint);
    }
}
