package com.kince.rippleview.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Kince183 on 2017/4/6.
 */
public class BasicRippleView extends View {

    private Context mContext;
    private Paint mCirclePaint;// 圆圈画笔

    private int mStartDiameter;//波纹出现时的直径
    private int mColorDiameter;//波纹颜色最深时的直径
    private int mEndDiameter;//波纹消失时的直径
    private int mRippleWidth;//波纹的宽度

    private int mRippleColor = Color.parseColor("#ffffff");//波纹的颜色
    private int maxPaintAlpha;

    private int mCircleRadius;
    private List<Integer> mCircleRadiusList = new ArrayList<>();//放置绘制出来的波

    private boolean isLoop;// 是否循环动画
    private long mCurrentTime = System.currentTimeMillis();//获得当前时间

    //开启handle进行重复绘制
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {

        @Override
        public void run() {
            invalidate();// 刷新界面
            controlCircle();
            if (isLoop) {
                mHandler.postDelayed(mRunnable, 50);
            }
        }
    };

    enum Mode{
        STYLE_STROKE,
        STYLE_FILL;
    }

    public BasicRippleView(Context context) {
        this(context, null);
    }

    public BasicRippleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public BasicRippleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        mStartDiameter = dp2px(25);
        mRippleWidth = dp2px(2);
        mEndDiameter = dp2px(112);
        maxPaintAlpha = 255;

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStrokeWidth(mRippleWidth);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setColor(mRippleColor);

        mCircleRadius = mStartDiameter / 2;
        mCircleRadiusList.add(mCircleRadius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawCircle(canvas);
        super.onDraw(canvas);
    }

    /**
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        mEndDiameter = Math.min(canvas.getWidth(), canvas.getHeight());
        mColorDiameter = mStartDiameter + (mEndDiameter - mStartDiameter) / 2;

        //绘制波纹，同时改变透明度
        for (int i = 0; i < mCircleRadiusList.size(); i++) {
            int r = mCircleRadiusList.get(i);//绘制的半径

            if (r < mColorDiameter / 2) {
                mCirclePaint.setAlpha(maxPaintAlpha * (r - mStartDiameter / 2) / ((mEndDiameter - mStartDiameter) / 2));//根据半径来让透明度变化
            } else {
                mCirclePaint.setAlpha(maxPaintAlpha - maxPaintAlpha * (r - mStartDiameter / 2) / ((mEndDiameter - mStartDiameter) / 2));//根据半径来让透明度变化
            }

            canvas.drawCircle(mEndDiameter / 2, mEndDiameter / 2, r, mCirclePaint);
        }
    }

    /**
     * 控制圆圈的显示逻辑
     */
    private void controlCircle() {
        //隔一段时间添加一个波
        if (System.currentTimeMillis() - mCurrentTime > 600) {
            mCircleRadiusList.add(mStartDiameter / 2);
            mCurrentTime = System.currentTimeMillis();//当前时间重新赋值刷新
        }

        //改变半径的值
        for (int i = 0; i < mCircleRadiusList.size(); i++) {
            mCircleRadiusList.set(i, mCircleRadiusList.get(i) + 1);//不断的改变半径
        }

        //性能优化，控制波的数量(迭代器)
        Iterator<Integer> iterator = mCircleRadiusList.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();//获取这个值
            if (next >= mEndDiameter / 2) {//大于结束时半径并且包含则移除
                if (mCircleRadiusList.contains(next)) {
                    iterator.remove();//使用迭代器来移除（否则有异常）
                }
            }
        }
    }

    /**
     * 开始动画
     */
    public void onStart() {
        mHandler.post(mRunnable);
    }

    /**
     * 开始动画
     *
     * @param loop
     */
    public void onStart(boolean loop) {
        isLoop = loop;
        mHandler.post(mRunnable);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private int dp2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 设置RippleView开始显示时的直径
     *
     * @param startDiameterDp 直径（单位：dp）
     */
    public void setStartDiameter(int startDiameterDp) {
        mStartDiameter = dp2px(startDiameterDp);
        mCircleRadius = mStartDiameter / 2;
        mCircleRadiusList.clear();
        mCircleRadiusList.add(mCircleRadius);
    }

    /**
     * 设置RippleView结束显示时的直径
     *
     * @param endDiameter 直径（单位：px）
     */
    public void setEndDiameter(int endDiameter) {
        this.mRippleWidth = endDiameter;
    }

    /**
     * 设置RippleView线条的颜色
     *
     * @param rippleColor 颜色
     */
    public void setRippleColor(int rippleColor) {
        mCirclePaint.setColor(rippleColor);
    }

    /**
     * 设置是否循环显示动画
     *
     * @param loop
     */
    public void setLoop(boolean loop) {
        isLoop = loop;
    }

    public void setMode(Mode mode){

    }

}
