package com.framgia.diary.customview;

import com.framgia.diary.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

public class SecondLineEditText extends EditText {
    private Bitmap bitmap;
    private boolean editable;
    private float padLeft = 0;
    private float padTop = 0;
    private Resources resources;
    private Rect mRect;
    private Paint mPaint;

    public SecondLineEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initData(context);
    }

    public SecondLineEditText(Context context) {
        super(context);
        initData(context);
    }

    public SecondLineEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
    }

    private void initData(Context context) {
        mRect = new Rect();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(Color.BLUE);
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.food_diary_row_1);
        resources = context.getResources();
        padLeft = resources.getDimension(R.dimen.size_30dip);
        padTop = resources.getDimension(R.dimen.size_3dip);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Rect r = mRect;
        Paint paint = mPaint;
        int height = getHeight();
        int line_height = getLineHeight();
        int count = height / line_height;
        if (getLineCount() > count)
            count = getLineCount();
        int baseline = getLineBounds(0, r);
        for (int i = 0; i < count; i++) {
            paint.setColor(Color.parseColor("#cbcbcb"));
            canvas.drawLine(r.left - padLeft, baseline + padTop - 2, r.right, baseline + padTop - 2,
                paint);
            canvas.drawBitmap(bitmap, r.left - padLeft, baseline - (getLineHeight() / 2) - 1,
                paint);
            baseline += getLineHeight();
            canvas.save();
        }
        super.onDraw(canvas);
        canvas.restore();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
    }

    @Override
    protected void onScrollChanged(int horiz, int vert, int oldHoriz, int oldVert) {
        super.onScrollChanged(horiz, vert, oldHoriz, oldVert);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return isEditable();
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
}
