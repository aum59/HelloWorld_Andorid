package com.anongsinbook.helloworld;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by intar on 9/2/2560.
 */

public class CustomView extends View {

    private Boolean isBlue = false;
    private Boolean isDown = false;
    private GestureDetector gesterDetector;

    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initWithAttrs(attrs, 0, 0);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public CustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void init() {
        gesterDetector = new GestureDetector(getContext(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                // Decide: Care or not care?
                getParent().requestDisallowInterceptTouchEvent(true);
                return true;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                // Do whatever you want
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                // Action Up
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2,
                                   float velocityX, float velocityY) {
                isBlue = !isBlue;
                invalidate();
                return true;
            }
        });
        // Enable click Mode
        setClickable(true);

        // After this call, if it's not clickable, it will be clickable
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CustomView,
                defStyleAttr,
                defStyleRes);
        try {
            isBlue = a.getBoolean(R.styleable.CustomView_isBlue, false);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint p = new Paint();
        if (isBlue) {
            p.setColor(0xFF0000FF); //#AARRGGBB
            canvas.drawLine(0, 0,
                    getMeasuredWidth(), getMeasuredHeight(),
                    p);
        } else {
            p.setColor(0xFFFF0000); //#AARRGGBB
            canvas.drawLine(0, 0,
                    getMeasuredWidth(), getMeasuredHeight(),
                    p);
        }

        if (isDown){
            p.setColor(0xFF00FF00); //#AARRGGBB

            //convert dp to px
            float px = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    5,
                    getContext().getResources().getDisplayMetrics());
            p.setStrokeWidth(px);
            canvas.drawLine(getMeasuredWidth(), 0,
                    0, getMeasuredHeight(),
                    p);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Pass event to GestureDetector
        gesterDetector.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isDown = true;
                invalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                isDown = false;
                invalidate();
                return true;
            case MotionEvent.ACTION_CANCEL:
                isDown = false;
                invalidate();
                return true;
            default:
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        BundleSavedState savedState = new BundleSavedState(superState);
        Bundle bundle = new Bundle();
        bundle.putBoolean("isBlue", isBlue);
        savedState.setBundle(bundle);
        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        BundleSavedState savedState = (BundleSavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        Bundle bundle = savedState.getBundle();
        isBlue = bundle.getBoolean("isBlue");
    }
}
