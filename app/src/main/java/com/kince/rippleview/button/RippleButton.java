package com.kince.rippleview.button;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Kince183 on 2017/4/6.
 *
 */
public class RippleButton extends Button{

    public RippleButton(Context context) {
        super(context);
    }

    public RippleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RippleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }



}
