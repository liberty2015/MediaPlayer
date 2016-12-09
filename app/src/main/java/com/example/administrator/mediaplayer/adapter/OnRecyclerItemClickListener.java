package com.example.administrator.mediaplayer.adapter;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by LinJinFeng on 2016/10/19.
 */

public abstract class OnRecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

    private GestureDetectorCompat gestureDetectorCompat;
    private RecyclerView recyclerView;
    private static final String TAG="OnRecyclerItemClick";

    public OnRecyclerItemClickListener(RecyclerView recyclerView){
        this.recyclerView=recyclerView;
        gestureDetectorCompat=new GestureDetectorCompat(recyclerView.getContext(),new ItemTouchHelperGestureListener());
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetectorCompat.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetectorCompat.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//        recyclerView.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    public void onItemClick(int position,RecyclerView.ViewHolder holder){

    }

    public void onLongClick(int position,RecyclerView.ViewHolder holder){

    }

    class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener{

        @Override
        public boolean onDown(MotionEvent e) {
            Log.d(TAG,"---onDown---");
            return super.onDown(e);
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.d(TAG,"---onShowPress---");
            super.onShowPress(e);

        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d(TAG,"---onScroll---");
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.d(TAG,"---onSingleTapUp---");
            View child=recyclerView.findChildViewUnder(e.getX(),e.getY());
            if (child!=null){
                RecyclerView.ViewHolder holder=recyclerView.getChildViewHolder(child);
                int position=holder.getAdapterPosition();
                onItemClick(position,holder);
            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.d(TAG,"---onLongPress---");
            View child=recyclerView.findChildViewUnder(e.getX(),e.getY());
            if (child!=null){
                RecyclerView.ViewHolder holder=recyclerView.getChildViewHolder(child);
                int position=holder.getAdapterPosition();
                onLongClick(position,holder);
            }
        }
    }
}
