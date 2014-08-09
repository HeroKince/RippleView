package com.kince.rippleview;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	private RippleView mRippleView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRippleView=(RippleView) findViewById(R.id.rippleview);
        mRippleView.stratRipple();
    }


   

}
