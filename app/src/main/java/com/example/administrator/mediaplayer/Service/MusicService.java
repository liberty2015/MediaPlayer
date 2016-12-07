package com.example.administrator.mediaplayer.Service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;

/**
 * Created by Administrator on 2016/11/19.
 */

public class MusicService extends Service
    implements MediaPlayer.OnPreparedListener,
    MediaPlayer.OnErrorListener,
    MediaPlayer.OnCompletionListener{

    private MediaPlayer mediaPlayer;
    private int currentProgress=0;
    private int totalProgress=0;

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    public class MusicBinder extends Binder {

        public void play(String url){
            try {
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void stop(){
            if (mediaPlayer.isPlaying()){
                mediaPlayer.stop();
            }
        }

        public void pause(){
            if (mediaPlayer.isPlaying()){
                currentProgress=mediaPlayer.getCurrentPosition();
                mediaPlayer.pause();
            }else {
                mediaPlayer.seekTo(currentProgress);
                mediaPlayer.start();
            }
        }


        public int getCurrentPosition(){
            return mediaPlayer.getCurrentPosition();
        }

        public int getTotalProgress(){
            return mediaPlayer.getDuration();
        }
    }

    private void initMediaPlayer(){
        mediaPlayer=new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnPreparedListener(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initMediaPlayer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MusicBinder();
    }


    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer=null;
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }
}
