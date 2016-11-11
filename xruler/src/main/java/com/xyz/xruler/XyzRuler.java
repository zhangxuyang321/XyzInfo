package com.xyz.xruler;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by paike on 2016/11/9.
 * xyz@163.com
 */

public class XyzRuler extends View implements ValueAnimator.AnimatorListener {
    private Paint bPaint; //边框画笔
    private Paint lPaint; //线画笔
    private Paint tPaint; //字画笔
    private RectF borderRectF;
    private VelocityTracker velocityTracker;
    private Scroller scroller;
    private Interpolator mInterpolator = new AccelerateDecelerateInterpolator();
    private int mWidth;
    private int mHeight;
    private int startY;  //刻度开始的Y轴高度
    private int sumMoveX = 0;
    private int downX = 0;
    private int lastMoveX = 0;
    private int halfWidth;
    private int sumPixel;
    private boolean isAnim = false;
    private boolean isLeft;
    private boolean isDrag = true;

    private float lineWidth;        //刻度线宽
    private int borderColor;        //边框颜色
    private int lineColor;          //线的颜色
    private int trigonSize;         //三角边长
    private int pixel;              //基本刻度之间间隔像素
    private int step;               //一个基本刻度代表的大小
    private int lineHeight;         //刻度之间高度差值
    private int lineToText;         //文字与最高刻度之间的距离
    private int begin;              //起始值
    private int end;                //终止值
    private int minVelocity;        //惯性滑动的最小速度
    private int indicateHeight;     //改变指示线的高度,数值越大高度越小
    private boolean isRect;         //是否是边框指示器(默认true)
    private boolean isTop;          //刻度是否在上边


    public XyzRuler(Context context) {
        this(context, null);
    }

    public XyzRuler(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XyzRuler(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.XyzRuler);
        lineWidth = ta.getFloat(R.styleable.XyzRuler_rLineWidth,2.0f);
        borderColor = ta.getColor(R.styleable.XyzRuler_rBorderColor, Color.BLUE);
        lineColor  = ta.getColor(R.styleable.XyzRuler_rLineColor,Color.WHITE);
        trigonSize = (int) ta.getDimension(R.styleable.XyzRuler_rTextSize,20);
        pixel = ta.getInt(R.styleable.XyzRuler_rPixel,15);
        step = ta.getInt(R.styleable.XyzRuler_rStep,1);
        int textSize = (int) ta.getDimension(R.styleable.XyzRuler_rTextSize, 30);
        int textColor = ta.getColor(R.styleable.XyzRuler_rTextColor, Color.WHITE);
        lineHeight = (int) ta.getDimension(R.styleable.XyzRuler_rLineHeight,25);
        lineToText = (int) ta.getDimension(R.styleable.XyzRuler_rLineToText,35);
        begin = ta.getInt(R.styleable.XyzRuler_rBegin,0);
        end = ta.getInt(R.styleable.XyzRuler_rEnd,1000);
        minVelocity = ta.getInt(R.styleable.XyzRuler_rMinVelocity,500);
        indicateHeight = (int) ta.getDimension(R.styleable.XyzRuler_rIndicateHeight,0);
        isRect = ta.getBoolean(R.styleable.XyzRuler_rIsRect,true);
        isTop = ta.getBoolean(R.styleable.XyzRuler_rIsTop,true);
        ta.recycle();
        scroller = new Scroller(context);
        setOverScrollMode(OVER_SCROLL_ALWAYS);

        lPaint = new Paint();
        lPaint.setAntiAlias(true);
        lPaint.setColor(lineColor);
        lPaint.setStrokeWidth(lineWidth);

        bPaint = new Paint();
        bPaint.setAntiAlias(true);
        bPaint.setStyle(Paint.Style.STROKE);
        bPaint.setStrokeWidth(8.0f);
        bPaint.setColor(borderColor);

        tPaint = new Paint();
        tPaint.setAntiAlias(true);
        tPaint.setTextAlign(Paint.Align.CENTER);
        tPaint.setColor(textColor);
        tPaint.setTextSize(textSize);
        tPaint.setStyle(Paint.Style.FILL);

        sumPixel = ((end - begin) / step) * pixel;

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        startY = 0;
        borderRectF = new RectF(0, 0, w, h);
        halfWidth = mWidth / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawScale(canvas);
        if (isRect) {
            drawBoarder(canvas);
        } else {
            drawLineIndicate(canvas);
        }
    }

    private void drawScale(Canvas canvas) {
        lPaint.setColor(lineColor);
        lPaint.setStrokeWidth(lineWidth);
        if(onRulerValueChangeListener!=null && !isAnim){
            onRulerValueChangeListener.value((-sumMoveX+halfWidth)/pixel*step+begin);
        }
        for (int x = 0; x < mWidth; x++) {
            int y = startY + lineHeight;
            boolean isDrawText = false;
            if ((-sumMoveX + x) % (pixel * 5) == 0) {
                y += lineHeight;
            }
            if ((-sumMoveX + x) % (pixel * 10) == 0) {
                y += lineHeight;
                isDrawText = true;
            }
            int text = (-sumMoveX + x) / pixel * step + begin;
            if (text >= begin && text <= end && ((-sumMoveX + x) % pixel) == 0) {
                canvas.drawLine(x, isTop?startY:mHeight, x, isTop?y:mHeight-y, lPaint);
            }
            if (isDrawText) {
                if (text >= begin && text <= end) {
                    canvas.drawText(String.valueOf(text), x, isTop?y + lineToText:mHeight-y-lineToText, tPaint);
                }
            }
        }
    }

    private void drawBoarder(Canvas canvas) {
        canvas.drawRect(borderRectF, bPaint);

        Path path = new Path();
        path.moveTo(halfWidth - trigonSize / 2, isTop?0:mHeight);
        path.lineTo(halfWidth + trigonSize / 2, isTop?0:mHeight);
        path.lineTo(halfWidth, isTop?trigonSize / 2:mHeight-trigonSize / 2);
        path.close();
        canvas.drawPath(path, bPaint);
    }

    private void drawLineIndicate(Canvas canvas) {
        lPaint.setColor(lineColor);
        lPaint.setStrokeWidth(lineWidth);
        canvas.drawLine(0, isTop ? 0 : mHeight, mWidth, isTop ? 0 : mHeight, lPaint);
        lPaint.setColor(borderColor);
        lPaint.setStrokeWidth(lineWidth * 1.5f);
        canvas.drawLine(halfWidth, isTop ? 0 : mHeight, halfWidth, isTop ? mHeight-indicateHeight : indicateHeight, lPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                isDrag = true;
                int moveX = (int) (event.getX() - downX);
                if (lastMoveX == moveX) {
                    return true;
                }
                sumMoveX += moveX;
                if (moveX < 0) {
                    //向左滑动
                    isLeft = true;
                    if (-sumMoveX > sumPixel) {
                        correct();
                        return true;
                    }
                } else {
                    //向右滑动
                    isLeft = false;
                    if (sumMoveX >= mWidth) {
                        correct();
                        return true;
                    }
                }
                lastMoveX = moveX;
                downX = (int) event.getX();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                velocityTracker.computeCurrentVelocity(1000);
                float xVelocity = velocityTracker.getXVelocity();
                if (Math.abs(xVelocity) < minVelocity) {
                    correct();
                    isDrag = true;
                    return true;
                }
                isDrag = false;
                int startx = pixel * 10;
                int velocityX = (int) -(Math.abs(xVelocity) + 0.5);
                int maxX = end * pixel - mWidth;
                scroller.fling(startx, 0, velocityX, 0, 0, maxX, 0, 0);
                recycleVelocityTracker();
                break;
        }
        return true;
    }


    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            int currX = scroller.getCurrX();
            if (sumMoveX >= mWidth) {
                correct();
                scroller.abortAnimation();
                return;
            }
            if (-sumMoveX >= sumPixel) {
                correct();
                scroller.abortAnimation();
                return;
            }
            if (isLeft) {
                sumMoveX -= currX;
            } else {
                sumMoveX += currX;
            }
            invalidate();
        } else {
            if(!isDrag) {
                if (sumMoveX > halfWidth || -sumMoveX + halfWidth > sumPixel) {
                    correct();
                    scroller.abortAnimation();
                }
            }
        }
    }

    private void correct() {
        if (sumMoveX > halfWidth) {
            if (isAnim) {
                return;
            }
            ValueAnimator valueAnimator = ValueAnimator.ofInt(sumMoveX, halfWidth);
            valueAnimator.setDuration(300 * (sumMoveX - halfWidth) / (halfWidth));
            valueAnimator.setInterpolator(mInterpolator);
            valueAnimator.addListener(this);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    sumMoveX = (int) animation.getAnimatedValue();
                    invalidate();
                }
            });
            valueAnimator.start();
        }
        if (-sumMoveX + halfWidth > sumPixel) {
            if (isAnim) {
                return;
            }
            float diff = ((-sumMoveX + halfWidth) - sumPixel);
            float time = diff / halfWidth;
            ValueAnimator valueAnimator = ValueAnimator.ofInt(-sumMoveX + halfWidth, sumPixel - halfWidth);
            valueAnimator.setDuration((long) (300 * time));
            valueAnimator.addListener(this);
            valueAnimator.setInterpolator(mInterpolator);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    sumMoveX = -(int) animation.getAnimatedValue();
                    invalidate();
                }
            });
            valueAnimator.start();
        }
    }

    private void recycleVelocityTracker() {
        if (velocityTracker != null) {
            velocityTracker.recycle();
            velocityTracker = null;
        }
    }

    @Override
    public void onAnimationStart(Animator animation) {
        isAnim = true;
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        isAnim = false;
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        isAnim = false;
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        isAnim = true;
    }

    /**
     * 监听ruler值的变话
     * @param onRulerValueChangeListener 监听器
     */
    public void setOnRulerValueChangeListener(RulerValue onRulerValueChangeListener) {
        this.onRulerValueChangeListener = onRulerValueChangeListener;
    }

    private RulerValue onRulerValueChangeListener;
    public interface RulerValue{
        void value(int value);
    }
}
