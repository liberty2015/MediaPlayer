package com.example.administrator.mediaplayer.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Administrator on 2016/9/24.
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] attrs=new int[]{
            android.R.attr.listDivider
    };

    public static final int HORIZONTAL_LIST= LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST= LinearLayoutManager.VERTICAL;

    private Drawable mDivider;

    private int mOrientation;
    private float margin;

    public DividerItemDecoration(Context context, int orientation){
        TypedArray a=context.obtainStyledAttributes(attrs);
        mDivider=a.getDrawable(0);
        a.recycle();
        setOrientation(orientation);

        margin= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,10,context.getResources().getDisplayMetrics());
    }

    public void setOrientation(int orientation){
        if (orientation!=HORIZONTAL_LIST&&orientation!= VERTICAL_LIST){
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation=orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation==VERTICAL_LIST){
            drawVertical(c, parent);
        }else {
            drawHorizontal(c, parent);
        }
    }

    private void drawVertical(Canvas c,RecyclerView parent){
        int left= (int) (margin+parent.getPaddingLeft());
        int right= (int) (parent.getWidth()-parent.getPaddingRight()-margin);
        int childCount=parent.getChildCount();
        Log.d("xxxxx","childCount="+childCount);
//        if (parent instanceof XRecyclerView){
//            for (int i=0;i<childCount-1;i++){
//                final View child=parent.getChildAt(i);
//                if (parent.getChildLayoutPosition(child)>0){
//                    RecyclerView.LayoutParams params= (RecyclerView.LayoutParams) child.getLayoutParams();
//                    int top=child.getBottom()+params.bottomMargin;
//                    int bottom=top+mDivider.getIntrinsicHeight();
//                    mDivider.setBounds(left,top,right,bottom);
//                    mDivider.draw(c);
//                }
//            }
//        }else {
            for (int i=0;i<childCount-1;i++){
                final View child=parent.getChildAt(i);
//                if (parent.getChildLayoutPosition(child)>0){
                    RecyclerView.LayoutParams params= (RecyclerView.LayoutParams) child.getLayoutParams();
                    int top=child.getBottom()+params.bottomMargin;
                    int bottom=top+mDivider.getIntrinsicHeight();
                    mDivider.setBounds(left,top,right,bottom);
                    mDivider.draw(c);
//                }
            }
//        }

    }

    private void drawHorizontal(Canvas c,RecyclerView parent){
        int top=parent.getPaddingTop();
        int bottom=parent.getHeight()-parent.getPaddingBottom();
        int childCount=parent.getChildCount();
        for (int i=0;i<childCount;i++){
            final View child=parent.getChildAt(i);
            RecyclerView.LayoutParams params= (RecyclerView.LayoutParams) child.getLayoutParams();
            int left=child.getRight()+params.rightMargin;
            int right=left+mDivider.getIntrinsicHeight();
            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);
        }
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

    }
}
