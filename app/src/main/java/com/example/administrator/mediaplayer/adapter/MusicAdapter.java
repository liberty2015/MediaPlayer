package com.example.administrator.mediaplayer.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.mediaplayer.R;
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
        return new BaseHolder(parent, R.layout.music_item);
    }

    @Override
    public void bindCustomViewHolder(BaseHolder holder, int position) {
        Music music=getItem(position);
        ((TextView)holder.getView(R.id.songName)).setText(music.getName());
        ((TextView)holder.getView(R.id.author)).setText(music.getUrl());
    }

    @Override
    protected int getCustomViewType(int position) {
        return 0;
    }
}
