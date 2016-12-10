package com.example.administrator.mediaplayer.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by acfun on 2016/12/9.
 */

public class MusicDBHelper extends SQLiteOpenHelper {
    public MusicDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MusicDBHelper(Context context,int version){
        super(context,"music",null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }

    private void createTable(SQLiteDatabase database){
        String sql="create table if not exists `MUSIC`(" +
                "`_id` integer NOT NULL primary key AUTOINCREMENT," +
                "`name` varchar(200) NOT NULL DEFAULT ''," +
                "`url` varchar(200) NOT NULL DEFAULT ''," +
                "`coverImg` varchar(200) NOT NULL DEFAULT ''," +
                "`author` varchar(200) NOT NULL DEFAULT ''"
                +")";
        database.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
