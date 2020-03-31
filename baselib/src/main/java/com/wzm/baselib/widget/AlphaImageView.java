package com.wzm.baselib.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class AlphaImageView extends AppCompatImageView {
    public AlphaImageView(Context context) {
        super(context);
    }

    public AlphaImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlphaImageView(Context context, AttributeSet attrs, int defStyleAttr) {
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
