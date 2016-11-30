package com.example.administrator.mediaplayer.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.mediaplayer.R;

/**
 * Created by Administrator on 2016/11/20.
 */

public class CDView extends View {

    private Matrix matrix;

    private BitmapShader bitmapShader;
    private Paint bitmapPaint;

    private int mWidth,mRadius;

    public CDView(Context context, AttributeSet attrs) {
        super(context, attrs);
        matrix=new Matrix();
        bitmapPaint=new Paint();
        bitmapPaint.setStyle(Paint.Style.FILL);
        bitmapPaint.setAntiAlias(true);
        setUpShader();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        mWidth=Math.min(heightSize,widthSize);
        mRadius=mWidth/2;
        setMeasuredDimension(mWidth,mWidth);
    }

    private void setUpShader(){
        Bitmap bitmap=((BitmapDrawable)getResources().getDrawable(R.mipmap.april)).getBitmap();
        bitmapShader=new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale=1.0f;
        int bSize=Math.min(bitmap.getWidth(),bitmap.getHeight());
        scale=mWidth*1.0f/bSize;
        matrix.setScale(scale,scale);
        bitmapShader.setLocalMatrix(matrix);
        bitmapPaint.setShader(bitmapShader);
    }


    private Bitmap drawableToBitmap(Drawable drawable){
        int w=drawable.getIntrinsicWidth();
        int h=drawable.getIntrinsicHeight();
        Bitmap bitmap= Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap);
        drawable.setBounds(0,0,w,h);
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mRadius,mRadius,mRadius,bitmapPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
