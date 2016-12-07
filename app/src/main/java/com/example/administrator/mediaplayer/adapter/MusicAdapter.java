package com.example.administrator.mediaplayer.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.example.administrator.mediaplayer.bean.Music;

/**
 * Created by acfun on 2016/12/2.
 */

public class MusicAdapter extends BaseRecyclerAdapter<Music,BaseHolder> {
    public MusicAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseHolder createCustomViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void bindCustomViewHolder(BaseHolder holder, int position) {

    }

    @Override
    protected int getCustomViewType(int position) {
        return 0;
    }
}
