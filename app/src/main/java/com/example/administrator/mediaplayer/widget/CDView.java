package com.example.administrator.mediaplayer.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
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
    private Bitmap mBitmap;
    private boolean hasInit=true;

    private long animTime;
//    private RotateThread thread;

    private int mWidth,mRadius,largeStroke,smallStroke,insideRadius,center,progressStrokeWidth;

    private boolean isPause;

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
//                rotate= (float) valueAnimator.getAnimatedValue();
//                invalidate();
                CDView.this.setRotation((Float) valueAnimator.getAnimatedValue());
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

    public void startRotate(){
//        this.startAnimation(animation);
        rotateAnimator.start();
    }

    public void pauseRotate(){
        animTime=rotateAnimator.getCurrentPlayTime();
        rotateAnimator.cancel();
    }

    public void stopRotate(){
        rotateAnimator.cancel();
    }

    public void resumeRotate(){
        rotateAnimator.start();
        rotateAnimator.setCurrentPlayTime(animTime);
    }


    private Palette.PaletteAsyncListener paletteAsyncListener;

    public void setPaletteAsyncListener(Palette.PaletteAsyncListener paletteAsyncListener) {
        this.paletteAsyncListener = paletteAsyncListener;
    }

    /**
     * 图片裁剪，
     * 将裁剪好的bitmap作为bitmap着色器设置到paint上
     */
    private void setUpShader(){
        if (mBitmap!=null){
            Bitmap bitmap= mBitmap;
            if (paletteAsyncListener!=null){
                Palette.Builder builder=Palette.from(bitmap);
                builder.generate(paletteAsyncListener);
            }
            bitmapShader=new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

            Matrix matrix=new Matrix();
            /**
             * 通过图片和组件大小计算缩放
             */
            float scale=1.0f;
            int size=Math.min(bitmap.getWidth(),bitmap.getHeight());
            float dx=0f,dy=0f;
            scale=mRadius*2.0f/size;
            int drawableWidth=bitmap.getWidth();
            int drawableHeight=bitmap.getHeight();
            int viewWidth=getWidth();
            int viewHeight=getHeight();
            /**
             * 由于缩放是基于组件原点缩放的，所以会导致在bitmapShader上出现拉伸，因此需要计算缩放之后的需要移动的像素值
             */
            dx=(viewWidth-drawableWidth*scale)*0.5f;
            dy=(viewHeight-drawableHeight*scale)*0.5f;
            Log.d("xxxxx","dx="+dx+"  dy="+dy);
            Log.d("xxxxx","mWidth="+mWidth+" radius="+mRadius+"  drawableWidth="+drawableWidth+"  drawableHeight="
                    +drawableHeight+"  viewWidth="+viewWidth+"  viewHeight="+viewHeight);
            Log.d("xxxxx","scale="+scale);
            /**
             * 为matrix设置scale
             */
            matrix.setScale(scale,scale);
            /**
             * matrix后乘侧移值
             */
            matrix.postTranslate((dx+0.5f),(dy+0.5f));
            bitmapShader.setLocalMatrix(matrix);
            bitmapPaint.setShader(bitmapShader);
        }else {
            Bitmap bitmap= drawableToBitmap(getResources().getDrawable(R.mipmap.disk));
            bitmapShader=new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            bitmapPaint.setShader(bitmapShader);
        }

    }

    /**
     * 将drawable转化为bitmap
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
        this.mBitmap= BitmapFactory.decodeResource(getResources(),drawableRes);
        hasInit=true;
        invalidate();
    }

    public void setmBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
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
