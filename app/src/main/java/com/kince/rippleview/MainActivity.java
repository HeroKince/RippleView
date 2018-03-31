package com.kince.rippleview;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;

import com.kince.rippleview.draw.HalfRippleView;
import com.kince.rippleview.draw.ObjectRippleView;
import com.kince.rippleview.draw.BasicRippleView;

public class MainActivity extends Activity {

	private HalfRippleView mHalfRippleView;
    private ObjectRippleView mObjectRippleView;
    private BasicRippleView mBasicRippleView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initObjectRippleView();
    }

    private void initBasicRippleView(){
        mBasicRippleView = (BasicRippleView) findViewById(R.id.rippleview);
        mBasicRippleView.setRippleColor(Color.RED);//设置波纹的颜色
        mBasicRippleView.onStart(true);//开始绘制
    }

    private void initObjectRippleView(){
        mObjectRippleView = (ObjectRippleView) findViewById(R.id.rippleview);
        mObjectRippleView.setDuration(3000);
        mObjectRippleView.setStyle(Paint.Style.FILL);
        mObjectRippleView.setColor(Color.RED);
        mObjectRippleView.setInterpolator(new DecelerateInterpolator());
        mObjectRippleView.start();
    }

    private void initHalfRippleView(){
        mHalfRippleView =(HalfRippleView) findViewById(R.id.rippleview);
        mHalfRippleView.stratRipple();
    }

}
