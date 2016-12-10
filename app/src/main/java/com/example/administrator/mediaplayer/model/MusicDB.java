package com.example.administrator.mediaplayer.model;

import android.content.Context;
import android.database.Cursor;

import com.example.administrator.mediaplayer.bean.Music;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acfun on 2016/12/10.
 */

public class MusicDB {
    private static MusicDBHelper musicDBHelper;
    private static MusicDB instance;

    public static MusicDB getInstance(Context context) {
        if (instance==null){
            musicDBHelper=new MusicDBHelper(context.getApplicationContext(),1);
            instance=new MusicDB();
        }
        return instance;
    }

    private MusicDB(){

    }

    public void insertBean(Music music){
//        musicDBHelper.getWritableDatabase().execSQL("insert into `music`(`name`,`url`,`coverImg`,`author`) values(?,?,?)"
//                ,new String[]{music.getName(),music.getUrl(),music.getCoverImg(),music.getAuthor()});
        musicDBHelper.getWritableDatabase().execSQL("insert into `music`(`name`,`url`) values(?,?)"
                ,new String[]{music.getName(),music.getUrl()});
    }

    public void deleteBean(Music music){
        musicDBHelper.getWritableDatabase().execSQL("delete from `music` where `_id`="+music.get_id());
    }

    public List<Music> selectBean(){
        Cursor cursor=musicDBHelper.getReadableDatabase().rawQuery("select * from `music`",null);
        List<Music> musics=new ArrayList<>();
        if (cursor!=null&&cursor.moveToFirst()){
            do {
                Music music=new Music();
                music.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
                music.setName(cursor.getString(cursor.getColumnIndex("name")));
                music.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                music.setCoverImg(cursor.getString(cursor.getColumnIndex("coverImg")));
                music.setAuthor(cursor.getString(cursor.getColumnIndex("author")));
                musics.add(music);
            }while (cursor.moveToNext());
            cursor.close();
        }
        return musics;
    }

    public boolean hasBean(Music music){
//        Cursor cursor=musicDBHelper.getReadableDatabase()
//                .rawQuery("select count(`_id`) `count` from `music` where `name`=? and `url`=? and `coverImg`=? and `author`=?",
//                        new String[]{music.getName(),music.getUrl(),music.getCoverImg(),music.getAuthor()});
        Cursor cursor=musicDBHelper.getReadableDatabase()
                .rawQuery("select count(`_id`) `count` from `music` where `name`=? and `url`=?",
                        new String[]{music.getName(),music.getUrl()});
        cursor.moveToFirst();
        int count=cursor.getInt(cursor.getColumnIndex("count"));
        cursor.close();
        return count>0;
    }
}
