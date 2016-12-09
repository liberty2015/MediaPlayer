package com.example.administrator.mediaplayer.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.example.administrator.mediaplayer.R;

/**
 * Created by acfun on 2016/12/2.
 */

public class CircleProgressBar extends View {

    private static final int TOTAL_PROGRESS=100;
    private int circleColor;
    private float progressStrokeWidth,insidePadding;
    private int mWidth,center;
    private float currentProgress;
    private float mRadius;
    private Paint backPaint,progressPaint;
    private RectF oval;

    public CircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);
        circleColor=array.getColor(R.styleable.CircleProgressBar_progressColor,getResources().getColor(R.color.progressColor));
        float defRadius= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,20,getResources().getDisplayMetrics());
//        progressRadius=array.getDimension(R.styleable.CircleProgressBar_circleRadius,defRadius);
        progressStrokeWidth=array.getDimension(R.styleable.CircleProgressBar_circleStrokeWidth,defRadius);
        float defPadding=TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,10,getResources().getDisplayMetrics());
        insidePadding=array.getDimension(R.styleable.CircleProgressBar_insidePadding,defPadding);
        currentProgress=array.getInt(R.styleable.CircleProgressBar_currentProgress,0);
        Log.d("xxxxx","progressStrokeWidth="+progressStrokeWidth+"   insidePadding="+insidePadding+"   currentProgress="+currentProgress);
        array.recycle();
        initPaint();
//        currentProgress=0;
    }

    private void initPaint(){
        backPaint=new Paint();
        backPaint.setAntiAlias(true);
        backPaint.setStyle(Paint.Style.STROKE);
        backPaint.setStrokeCap(Paint.Cap.ROUND);
        backPaint.setStrokeWidth(progressStrokeWidth);
        backPaint.setColor(Color.parseColor("#FF7A7B7A"));
        backPaint.setAlpha(50);
        progressPaint=new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        progressPaint.setStrokeWidth(progressStrokeWidth);
        progressPaint.setColor(circleColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        mWidth= Math.min(widthSize,heightSize);
        setMeasuredDimension(mWidth,mWidth);
        mRadius=mWidth/2-progressStrokeWidth/2;
        center=mWidth/2;
        float right=mWidth-progressStrokeWidth/2;
        oval=new RectF(progressStrokeWidth/2,progressStrokeWidth/2,right,right);
        Log.d("xxxxx","radius="+mRadius+"   width="+mWidth);
    }

    public void setCurrentProgress(float currentProgress) {
        if (currentProgress>TOTAL_PROGRESS){
            throw new IllegalArgumentException("进度不能大于100");
        }else {
            this.currentProgress = currentProgress;
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(center,center,mRadius,backPaint);
//        BigDecimal bigDecimal=new BigDecimal((360/100F));
//        bigDecimal.multiply(new BigDecimal(currentProgress));
        float percent=currentProgress/100f;
        float finalDegree=360*percent;
        Log.d("xxxxx","currentProgress="+currentProgress+"  percent="+percent+"  finalDegree="+finalDegree);
        canvas.drawArc(oval,0,finalDegree,false,progressPaint);
    }
}
