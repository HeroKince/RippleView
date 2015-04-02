/**
 * 
 */
package com.kince.rippleview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author kince
 * @category ²¨ÎÆ
 * @since 2014.8.9
 * @version v1.0.0
 * 
 */
public class RippleView extends View {

	private int mScreenWidth;
	private int mScreenHeight;

	private Bitmap mRippleBitmap;
	private Paint mRipplePaint = new Paint();

	private int mBitmapWidth;
	private int mBitmapHeight;

	private boolean isStartRipple;

	private int heightPaddingTop;
	private int heightPaddingBottom;
	private int widthPaddingLeft;
	private int widthPaddingRight;

	private RectF mRect = new RectF();

	private int rippleFirstRadius = 0;
	private int rippleSecendRadius = -33;
	private int rippleThirdRadius = -66;

	private Paint textPaint = new Paint();
    private String mText="µã»÷ÎÒ°É";
    
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			invalidate();
			if (isStartRipple) {
				rippleFirstRadius++;
				if (rippleFirstRadius > 100) {
					rippleFirstRadius = 0;
				}
				rippleSecendRadius++;
				if (rippleSecendRadius > 100) {
					rippleSecendRadius = 0;
				}
				rippleThirdRadius++;
				if (rippleThirdRadius > 100) {
					rippleThirdRadius = 0;
				}
				sendEmptyMessageDelayed(0, 20);
			}
		}
	};

	/**
	 * @param context
	 */
	public RippleView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public RippleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public RippleView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init();
	}

	private void init() {
		mRipplePaint.setColor(4961729);
		mRipplePaint.setAntiAlias(true);
		mRipplePaint.setStyle(Paint.Style.FILL);

		textPaint.setTextSize(30);
		textPaint.setAntiAlias(true);
		textPaint.setStyle(Paint.Style.FILL);
		textPaint.setColor(Color.WHITE);

		mRippleBitmap = BitmapFactory.decodeStream(getResources()
				.openRawResource(R.drawable.easy3d_ic_apply));
		mBitmapWidth = mRippleBitmap.getWidth();
		mBitmapHeight = mRippleBitmap.getHeight();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int mh = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
		int mw = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
		if (mBitmapWidth < 2 * mBitmapHeight) {
			mBitmapWidth = (2 * mBitmapHeight);
		}
		setMeasuredDimension(mBitmapWidth, mBitmapHeight);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		if (isStartRipple) {
			float f1 = 3 * mBitmapHeight / 10;
			mRipplePaint.setAlpha(255);
			canvas.drawCircle(mBitmapWidth / 2, mBitmapHeight,
					7 * mBitmapHeight / 10, mRipplePaint);
			int i1 = (int) (220.0F - (220.0F - 0.0F) / 100.0F
					* rippleFirstRadius);
			mRipplePaint.setAlpha(i1);
			canvas.drawCircle(mBitmapWidth / 2, mBitmapHeight, 7
					* mBitmapHeight / 10 + f1 * rippleFirstRadius / 100.0F,
					mRipplePaint);
			if (rippleSecendRadius >= 0) {
				int i3 = (int) (220.0F - (220.0F - 0.0F) / 100.0F
						* rippleSecendRadius);
				mRipplePaint.setAlpha(i3);
				canvas.drawCircle(mBitmapWidth / 2, mBitmapHeight,
						7 * mBitmapHeight / 10 + f1 * rippleSecendRadius
								/ 100.0F, mRipplePaint);
			}
			if (rippleThirdRadius >= 0) {
				int i2 = (int) (220.0F - (220.0F - 0.0F) / 100.0F
						* rippleThirdRadius);
				mRipplePaint.setAlpha(i2);
				canvas.drawCircle(mBitmapWidth / 2, mBitmapHeight, 7
						* mBitmapHeight / 10 + f1 * rippleThirdRadius / 100.0F,
						mRipplePaint);
			}

		}
		mRipplePaint.setAlpha(30);
		canvas.drawCircle(mBitmapWidth / 2, mBitmapHeight, mBitmapHeight,
				mRipplePaint);
		mRipplePaint.setAlpha(120);
		canvas.drawCircle(mBitmapWidth / 2, mBitmapHeight,
				9 * mBitmapHeight / 10, mRipplePaint);
		mRipplePaint.setAlpha(180);
		canvas.drawCircle(mBitmapWidth / 2, mBitmapHeight,
				8 * mBitmapHeight / 10, mRipplePaint);
		mRipplePaint.setAlpha(255);
		canvas.drawCircle(mBitmapWidth / 2, mBitmapHeight,
				7 * mBitmapHeight / 10, mRipplePaint);
		float length = textPaint.measureText(mText);
		canvas.drawText(mText, (mBitmapWidth - length) / 2,
				mBitmapHeight * 3 / 4, textPaint);

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		mScreenWidth = w;
		mScreenHeight = h;
		confirmSize();
		invalidate();

	}

	private void confirmSize() {
		int minScreenSize = Math.min(mScreenWidth, mScreenHeight);
		int widthOverSize = mScreenWidth - minScreenSize;
		int heightOverSize = mScreenHeight - minScreenSize;
		heightPaddingTop = (getPaddingTop() + heightOverSize / 2);
		heightPaddingBottom = (getPaddingBottom() + heightOverSize / 2);
		widthPaddingLeft = (getPaddingLeft() + widthOverSize / 2);
		widthPaddingRight = (getPaddingRight() + widthOverSize / 2);

		int width = getWidth();
		int height = getHeight();

		mRect = new RectF(widthPaddingLeft, heightPaddingTop, width
				- widthPaddingRight, height * 2 - heightPaddingBottom);

	}

	public void stratRipple() {
		isStartRipple = true;
		handler.sendEmptyMessage(0);
	}
	
        public void stopRipple(){
                isStartRipple = false;
                handler.removeMessages(0);
        }

}
