package com.wzm.baselib.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class AlphaTextView extends AppCompatTextView {

    public AlphaTextView(Context context) {
        super(context);
    }

    public AlphaTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlphaTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        if (pressed) {
            setAlpha(0.7f);

            setScaleX(0.95f);
            setScaleY(0.95f);
        } else {
            setAlpha(1.0f);

            setScaleX(1.0f);
            setScaleY(1.0f);
        }
    }
}
