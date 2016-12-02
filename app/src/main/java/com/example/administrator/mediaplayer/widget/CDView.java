package com.example.administrator.mediaplayer.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v7.graphics.Palette;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import com.example.administrator.mediaplayer.R;

/**
 * Created by Administrator on 2016/11/20.
 */

public class CDView extends View {

    /**
     * 图形分析
     *
     *  观察图片可以发现：
     *  设内圆半径为radius，则：
     *  外环是2radius
     *  内环是radius
     *  整个圆的半径等于mWidth/2=2radius-2(内外环间距)+radius-5(内环和内圆间距)+radius
     */

    private BitmapShader bitmapShader;
    private Paint bitmapPaint,progressPaint,progressBackPaint,shadowPaint;
    private float rotate=0f;
    private Animation animation;
    private ObjectAnimator rotateAnimator;
    private int drawableRes;
    private boolean hasInit=true;
//    private RotateThread thread;

    private int mWidth,mRadius,largeStroke,smallStroke,insideRadius,center,progressStrokeWidth;

    public CDView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array=context.obtainStyledAttributes(R.styleable.CDView);
        drawableRes=array.getResourceId(R.styleable.CDView_circleImg,0);
        Log.d("xxxxx","drawableRes="+drawableRes+"  R.mipmap.april="+R.mipmap.april);
        array.recycle();
        initPaint();
    }


    private void initPaint(){
        bitmapPaint=new Paint();
        bitmapPaint.setStyle(Paint.Style.STROKE);
        bitmapPaint.setStrokeWidth(200);
        bitmapPaint.setAntiAlias(true);
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        animation= AnimationUtils.loadAnimation(getContext(),R.anim.rotate);
        animation.setInterpolator(new LinearInterpolator());
        progressPaint=new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setColor(getResources().getColor(R.color.progressColor));
        progressStrokeWidth=25;
        progressPaint.setStrokeWidth(progressStrokeWidth);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        progressBackPaint=new Paint();
        progressBackPaint.setAntiAlias(true);
        progressBackPaint.setStyle(Paint.Style.STROKE);
        progressBackPaint.setStrokeCap(Paint.Cap.ROUND);
        progressBackPaint.setColor(Color.parseColor("#FF7E7C7C"));
        progressBackPaint.setAlpha(50);
        progressBackPaint.setStrokeWidth(progressStrokeWidth);
        shadowPaint=new Paint();
        shadowPaint.setStyle(Paint.Style.STROKE);
        shadowPaint.setColor(Color.BLACK);
        float radius= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,5,getResources().getDisplayMetrics());
        BlurMaskFilter filter=new BlurMaskFilter(radius, BlurMaskFilter.Blur.SOLID);
        shadowPaint.setMaskFilter(filter);
//        bitmapPaint.setMaskFilter(filter);
//        setLayerType(LAYER_TYPE_SOFTWARE,shadowPaint);
        rotateAnimator= ObjectAnimator.ofFloat(this,"rotate",0f,360f);
        rotateAnimator.setDuration(10000);
        rotateAnimator.setInterpolator(new LinearInterpolator());
        rotateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                rotate= (float) valueAnimator.getAnimatedValue();
                invalidate();
//                CDView.this.setRotation((Float) valueAnimator.getAnimatedValue());
            }
        });
        rotateAnimator.setRepeatCount(ValueAnimator.INFINITE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        mWidth=Math.min(heightSize,widthSize);
        mRadius=mWidth/2-10-progressStrokeWidth;
        center=mWidth/2;
        insideRadius=(mRadius-7)/4;
        largeStroke= 2*insideRadius;
        smallStroke= insideRadius;
        setMeasuredDimension(mWidth,mWidth);
//        setUpShader();
    }



    /**
     * 将裁剪好的bitmap作为bitmap着色器设置到paint上
     */
    private void setUpShader(){
        if (drawableRes!=0){
            Bitmap bitmap= drawableToBitmap(getResources().getDrawable(drawableRes));
            if (paletteAsyncListener!=null){
                Palette.Builder builder=Palette.from(bitmap);
                builder.generate(paletteAsyncListener);
            }
            bitmapShader=new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            bitmapPaint.setShader(bitmapShader);
        }else {
            LinearGradient linearGradient=
                    new LinearGradient(0,0,mWidth,mWidth,
                            new int[]{Color.WHITE,Color.BLACK,Color.WHITE},
                            new float[]{0,0.6f,1.0f},
                            Shader.TileMode.REPEAT);
            bitmapPaint.setShader(linearGradient);
        }

    }

    public void startRotate(){
        this.startAnimation(animation);
    }

    private Palette.PaletteAsyncListener paletteAsyncListener;

    public void setPaletteAsyncListener(Palette.PaletteAsyncListener paletteAsyncListener) {
        this.paletteAsyncListener = paletteAsyncListener;
    }

    /**
     * 图片裁剪，生成新的bitmap
     * @param drawable
     * @return
     */
    private Bitmap drawableToBitmap(Drawable drawable){
        int w=drawable.getIntrinsicWidth();
        int h=drawable.getIntrinsicHeight();
        Bitmap bitmap= Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap);
        drawable.setBounds(0,0,mWidth,mWidth);
        drawable.draw(canvas);
//        Math.toDegrees()
        return bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (hasInit){
            setUpShader();
            hasInit=false;
        }
        float largeRadius=mRadius-largeStroke/2;
        float smallRadius=mRadius-2-largeStroke-smallStroke/2;
        shadowPaint.setStrokeWidth(20);
        canvas.drawCircle(center,center,mRadius-10,shadowPaint);
        canvas.drawCircle(center,center,insideRadius+20,shadowPaint);
        canvas.save();
        canvas.rotate(rotate,center,center);
        bitmapPaint.setStrokeWidth(largeStroke);
        canvas.drawCircle(center,center, largeRadius,bitmapPaint);
        bitmapPaint.setStrokeWidth(smallStroke);
        canvas.drawCircle(center,center, smallRadius,bitmapPaint);
        canvas.restore();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public void setDrawableRes(int drawableRes) {
        this.drawableRes = drawableRes;
        hasInit=true;
        invalidate();
    }



    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable=super.onSaveInstanceState();
        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
//        this.startAnimation(animation);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
