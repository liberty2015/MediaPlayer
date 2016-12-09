package com.example.administrator.mediaplayer;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by acfun on 2016/12/9.
 */

public class commonEnum {
    public static final int PLAY_STATE=100;
    public static final int PAUSE_STATE=101;
    public static final int STOP_STATE=102;

    @IntDef({PLAY_STATE,PAUSE_STATE,STOP_STATE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MUSICSTATE{}
}
