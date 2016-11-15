package com.xyz.xswitch;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import static java.lang.reflect.Array.getInt;

/**
 * Created by xyz on 2016/11/4.
 * xyz@163.com
 */

public class XyzSwitch extends View implements ValueAnimator.AnimatorUpdateListener, ValueAnimator.AnimatorListener {

    private int radian = 75;
    private float ASPECT_RATIO = 0.37f;
    private float SLIDER_RATIO = 0.56f;
    private float mTransitionLength;

    private Paint mPaint;
    private Paint iPaint;

    private int backgroundFromColor;
    private int backgroundToColor;
    private int sliderFromColor;
    private int sliderToColor;
    private int iconWidth = 80;
    private int iconHeight = 80;

    private Bitmap xyzLeftIcon;
    private Bitmap xyzRightIcon;

    private RectF backgroundRectf;
    private RectF sliderRectf;
    private RectF bgRightICon;
    private RectF bgLeftICon;

    private ValueAnimator mValueAnimator;
    private Interpolator mInterpolator = new AccelerateDecelerateInterpolator();
    private long animDuration;
    private float animProgress = 0.0f;
    private boolean isRight = false;
    private boolean animIsUnderway = false;
    private int COLORCHANGEBG = backgroundFromColor;
    private int COLORCHANGESLIDER = sliderFromColor;

    public XyzSwitch(Context context) {
        this(context, null);
    }

    public XyzSwitch(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XyzSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.XyzSwitch);
        radian = (int) ta.getDimension(R.styleable.XyzSwitch_xRadian,45);
        ASPECT_RATIO = ta.getFloat(R.styleable.XyzSwitch_xASPECTRATIO,0.37f);
        SLIDER_RATIO = ta.getFloat(R.styleable.XyzSwitch_xSLIDERRATIO,0.56f);
        backgroundFromColor = ta.getColor(R.styleable.XyzSwitch_xBackgroundFromColor,0xff443c6f);
        backgroundToColor = ta.getColor(R.styleable.XyzSwitch_xBackgroundToColor,0xffff5a73);
        COLORCHANGEBG = ta.getColor(R.styleable.XyzSwitch_xBackgroundFromColor,0xff443c6f);
        sliderFromColor = ta.getColor(R.styleable.XyzSwitch_xSliderFromColor,0xffff5a73);
        sliderToColor = ta.getColor(R.styleable.XyzSwitch_xSliderToColor,0xff443c6f);
        COLORCHANGESLIDER = ta.getColor(R.styleable.XyzSwitch_xSliderFromColor,0xffff5a73);
        iconWidth = (int) ta.getDimension(R.styleable.XyzSwitch_xIconWidth,80);
        iconHeight = (int) ta.getDimension(R.styleable.XyzSwitch_xIconHeight,80);
        animDuration = ta.getInt(R.styleable.XyzSwitch_xChangedTime,300);
        xyzLeftIcon = BitmapFactory.decodeResource(getResources(), ta.getResourceId(R.styleable.XyzSwitch_xLeftIcon,0));
        xyzRightIcon = BitmapFactory.decodeResource(getResources(), ta.getResourceId(R.styleable.XyzSwitch_xRightIcon,0));

        ta.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setFilterBitmap(true);
        iPaint = new Paint();
        iPaint.setAntiAlias(true);
        iPaint.setFilterBitmap(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width;
        int height;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            width = MeasureSpec.getSize(widthMeasureSpec);
            height = (int) (width * ASPECT_RATIO);
        } else {
            width = 186;
            height = (int) (width * ASPECT_RATIO);
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float left = 0;
        float top = 0;
        float right = w;
        float bottom = h;
        backgroundRectf = new RectF(left, top, right, bottom);
        float sliderRight = right * SLIDER_RATIO;
        sliderRectf = new RectF(left, top, sliderRight, bottom);
        mTransitionLength = right - sliderRight;

        float iconLadyLeft = w / 4 - iconWidth / 2 + w * 0.04f;
        float iconLadyRight = w / 4 + iconWidth / 2 + w * 0.04f;
        float iconTop = h / 2 - iconHeight / 2;
        float iconBottom = h / 2 + iconHeight / 2;

        bgLeftICon = new RectF(iconLadyLeft, iconTop, iconLadyRight, iconBottom);

        float iconManLeft = 3 * w / 4 - iconWidth / 2 - w * 0.04f;
        float iconManRight = 3 * w / 4 + iconWidth / 2 - w * 0.04f;

        bgRightICon = new RectF(iconManLeft, iconTop, iconManRight, iconBottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBg(canvas);
        drawSlider(canvas);
        drawBgIcon(canvas);
    }

    private void drawBgIcon(Canvas canvas) {
        canvas.drawBitmap(xyzLeftIcon, null, bgLeftICon, mPaint);
        iPaint.setColorFilter(getColorMatrixColorFilter());
        canvas.drawBitmap(xyzLeftIcon, null, bgLeftICon, isRight ? mPaint : iPaint);
        canvas.drawBitmap(xyzRightIcon, null, bgRightICon, isRight ? iPaint : mPaint);
    }


    //去色
    private ColorMatrixColorFilter getColorMatrixColorFilter() {
        ColorMatrix cm = new ColorMatrix(
                new float[]{
                        1.5F, 1.5F, 1.5F, 0, -1,
                        1.5F, 1.5F, 1.5F, 0, -1,
                        1.5F, 1.5F, 1.5F, 0, -1,
                        0, 0, 0, 1, 0
                });
        return new ColorMatrixColorFilter(cm);
    }


    private void drawSlider(Canvas canvas) {
        //移动画布
        canvas.save();
        canvas.translate(getSliderTransitionValue(), 0);
        mPaint.setColor(COLORCHANGESLIDER);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(sliderRectf, radian, radian, mPaint);
        canvas.restore();
    }

    private float getSliderTransitionValue() {

        return mTransitionLength * animProgress;
    }

    private void drawBg(Canvas canvas) {
        mPaint.setColor(COLORCHANGEBG);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(backgroundRectf, radian, radian, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:
                if (animIsUnderway) {
                    return true;
                }
                if (isRight) {
                    close();
                    isRight = false;
                } else {
                    open();
                    isRight = true;
                }
                return true;
        }
        return super.onTouchEvent(event);
    }

    private void close() {
        mValueAnimator = ValueAnimator.ofFloat(1.0f, 0);
        mValueAnimator.setDuration(animDuration);
        mValueAnimator.addUpdateListener(this);
        mValueAnimator.addListener(this);
        mValueAnimator.setInterpolator(mInterpolator);
        mValueAnimator.start();
        colorChangeBg();
        colorChangeSlider();
    }

    private void open() {
        mValueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        mValueAnimator.setDuration(animDuration);
        mValueAnimator.addUpdateListener(this);
        mValueAnimator.addListener(this);
        mValueAnimator.setInterpolator(mInterpolator);
        mValueAnimator.start();
        colorChangeBg();
        colorChangeSlider();
    }

    public void colorChangeBg() {
        int colorFrom = isRight ? backgroundToColor : backgroundFromColor;
        int colorTo = isRight ? backgroundFromColor : backgroundToColor;
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setInterpolator(mInterpolator);
        colorAnimation.setDuration(animDuration);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                COLORCHANGEBG = (int) valueAnimator.getAnimatedValue();
            }
        });
        colorAnimation.start();
    }

    public void colorChangeSlider() {
        int colorFrom = isRight ? sliderToColor : sliderFromColor;
        int colorTo = isRight ? sliderFromColor : sliderToColor;
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setInterpolator(mInterpolator);
        colorAnimation.setDuration(animDuration);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                COLORCHANGESLIDER = (int) valueAnimator.getAnimatedValue();
            }
        });
        colorAnimation.start();
    }

    @Override
    public void onAnimationStart(Animator animator) {
        animIsUnderway = true;
    }

    @Override
    public void onAnimationEnd(Animator animator) {
        animIsUnderway = false;
        if (null != onXyzSwitchChangeListener) {
            onXyzSwitchChangeListener.changed(isRight);
        }
    }

    @Override
    public void onAnimationCancel(Animator animator) {
        animIsUnderway = false;
    }

    @Override
    public void onAnimationRepeat(Animator animator) {
        animIsUnderway = true;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        animProgress = (float) valueAnimator.getAnimatedValue();
        invalidate();
    }

    private XyzSwitchChange onXyzSwitchChangeListener;

    public void setOnXyzSwitchChangeListener(XyzSwitchChange onXyzSwitchChangeListener) {
        this.onXyzSwitchChangeListener = onXyzSwitchChangeListener;
    }

    public interface XyzSwitchChange {
        void changed(boolean isRight);
    }

    public void setASPECT_RATIO(float ASPECT_RATIO) {
        this.ASPECT_RATIO = ASPECT_RATIO;
    }

    public void setSLIDER_RATIO(float SLIDER_RATIO) {
        this.SLIDER_RATIO = SLIDER_RATIO;
    }

    public void setBackgroundFromColor(int backgroundFromColor) {
        this.backgroundFromColor = backgroundFromColor;
    }

    public void setBackgroundToColor(int backgroundToColor) {
        this.backgroundToColor = backgroundToColor;
    }

    public void setSliderFromColor(int sliderFromColor) {
        this.sliderFromColor = sliderFromColor;
    }

    public void setSliderToColor(int sliderToColor) {
        this.sliderToColor = sliderToColor;
    }

    public void setIconWidth(int iconWidth) {
        this.iconWidth = iconWidth;
    }

    public void setIconHeight(int iconHeight) {
        this.iconHeight = iconHeight;
    }

    public void setAnimDuration(long animDuration) {
        this.animDuration = animDuration;
    }

    public void setRadian(int radian) {
        this.radian = radian;
    }
}
