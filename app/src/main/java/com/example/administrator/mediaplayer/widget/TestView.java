package com.example.administrator.mediaplayer.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by acfun on 2016/12/1.
 */

public class TestView extends View {
    private Paint linePaint,bitmapPaint;
    private RectF rectF,rectF1;

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint(){
        linePaint=new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(10);
        rectF=new RectF(100,100,400,400);
        rectF1=new RectF(200,200,600,600);
        bitmapPaint=new Paint();
        bitmapPaint.setAntiAlias(true);
        bitmapPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        /**
//         * drawArc(RectF oval, float startAngle, float sweepAngle, boolean useCenter, Paint paint)
//         * oval:通过RectF对象来定义大小和界限
//         * startAngle:开始角度
//         * endAngle:结束角度
//         * useCenter:如果为true，则会从中心开始，又从中心结束。（不知道怎么解释，看运行结果吧）
//         * paint:画笔对象
//         */
////        canvas.drawArc(rectF,0,270,true,linePaint);
////        canvas.drawArc(rectF1,0,180,false,linePaint);
//
//        Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.mipmap.april);
//        BitmapShader bitmapShader=new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
//
//        bitmapPaint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
//        canvas.drawCircle(600,600,100,bitmapPaint);
////        bitmapPaint.setStyle(Paint.Style.STROKE);
//        linePaint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
//        canvas.drawCircle(600,600,120,linePaint);

//        Paint paint1=new Paint();
//        paint1.setStyle(Paint.Style.FILL);
//        paint1.setColor(Color.RED);
//        canvas.drawCircle(200,200,150,paint1);
//
//        Paint paint=new Paint();
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setColor(Color.BLUE);
//        paint.setStrokeWidth(100);
//        canvas.drawCircle(200,500,100,paint);
//        paint.setStrokeWidth(20);
//        paint.setColor(Color.GREEN);
//        canvas.drawCircle(200,500,40,paint);
        Paint paint2=new Paint();
        paint2.setStrokeCap(Paint.Cap.ROUND);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeWidth(20);
//        canvas.drawCircle(300,300,400,paint2);
        RectF oval=new RectF(300,300,400,400);
        canvas.drawArc(oval,0F,200F,false,paint2);
    }
}
