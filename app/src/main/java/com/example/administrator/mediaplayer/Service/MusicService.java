//package com.example.administrator.mediaplayer.Service;
//
//import android.app.Service;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.media.AudioManager;
//import android.media.MediaPlayer;
//import android.os.IBinder;
//import android.support.annotation.Nullable;
//import android.text.TextUtils;
//import android.util.Log;
//
//import com.example.administrator.mediaplayer.MainActivity;
//
//import java.io.IOException;
//
///**
// * Created by Administrator on 2016/11/19.
// */
//
//public class MusicService extends Service
//    implements MediaPlayer.OnPreparedListener,
//    MediaPlayer.OnErrorListener,
//    MediaPlayer.OnCompletionListener{
//
//    private MediaPlayer mediaPlayer;
//    private int currentProgress=0;
//    private int totalProgress=0;
//
//
//    public static final String PLAY_ACTION="com.liberty.mediaplayer.PlayAction";
//    public static final String PAUSE_ACTION="com.liberty.mediaplayer.PauseAction";
//    public static final String STOP_ACTION="com.liberty.mediaplayer.StopAction";
//
//
//    private ServiceReceiver receiver;
//
//    @Override
//    public void onCompletion(MediaPlayer mediaPlayer) {
//        Intent intent=new Intent(MainActivity.COMPLETE_ACTION);
//        sendBroadcast(intent);
//    }
//
////    public class MusicBinder extends Binder {
////
////        public void play(String url){
//////            if (mediaPlayer.isPlaying()){
//////                mediaPlayer.stop();
////                mediaPlayer.reset();
//////            }
////            try {
////                mediaPlayer.setDataSource(url);
////                mediaPlayer.prepareAsync();
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////        }
////
////        public void stop(){
////            if (mediaPlayer.isPlaying()){
////                mediaPlayer.stop();
////            }
////        }
////
////        public void pause(){
////            if (mediaPlayer.isPlaying()){
////                currentProgress=mediaPlayer.getCurrentPosition();
////                mediaPlayer.pause();
////            }else {
////                mediaPlayer.seekTo(currentProgress);
////                mediaPlayer.start();
////            }
////        }
////
////
////        public int getCurrentPosition(){
////            return mediaPlayer.getCurrentPosition();
////        }
////
////        public int getTotalProgress(){
////            return mediaPlayer.getDuration();
////        }
////
////
////    }
//
//    private void initMediaPlayer(){
//        mediaPlayer=new MediaPlayer();
//        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//        mediaPlayer.setOnCompletionListener(this);
//        mediaPlayer.setOnErrorListener(this);
//        mediaPlayer.setOnPreparedListener(this);
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        initMediaPlayer();
//        IntentFilter filter=new IntentFilter();
//        receiver=new ServiceReceiver();
//        filter.addAction(PLAY_ACTION);
//        filter.addAction(PAUSE_ACTION);
//        filter.addAction(STOP_ACTION);
//        registerReceiver(receiver,filter);
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
////        return new MusicBinder();
//        return null;
//    }
//
//
//    @Override
//    public void onPrepared(MediaPlayer mediaPlayer) {
//        mediaPlayer.start();
//        Intent intent=new Intent();
//        intent.setAction(MainActivity.TOTALPROGRESS_ACTION);
//        intent.putExtra("totalProgress",mediaPlayer.getDuration());
//        sendBroadcast(intent);
//        Log.d("xxxxx","mediaPlayer.getDuration()="+mediaPlayer.getDuration());
//    }
//
//    private void play(String url){
//        mediaPlayer.reset();
//        try {
//            mediaPlayer.setDataSource(url);
//            mediaPlayer.prepareAsync();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void pause(){
//        if (mediaPlayer.isPlaying()){
//            currentProgress=mediaPlayer.getCurrentPosition();
//            mediaPlayer.pause();
//        }else {
//            mediaPlayer.seekTo(currentProgress);
//            mediaPlayer.start();
//        }
//        Log.d("xxxxx","mediaPlayer.getCurrentPosition()="+mediaPlayer.getCurrentPosition());
//    }
//
//    public void stop(){
//        if (mediaPlayer.isPlaying()){
//            mediaPlayer.stop();
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mediaPlayer.stop();
//        mediaPlayer.release();
//        mediaPlayer=null;
//    }
//
//    @Override
//    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
//        return false;
//    }
//
//    public class ServiceReceiver extends BroadcastReceiver{
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action=intent.getAction();
//            switch (action){
//                case PLAY_ACTION:{
//                    String url=intent.getStringExtra("url");
//                    if (!TextUtils.isEmpty(url)){
//                        play(url);
//                    }
//                }
//                break;
//                case PAUSE_ACTION:{
//                    pause();
//                }
//                break;
//                case STOP_ACTION:{
//                    stop();
//                }
//                break;
//            }
//        }
//    }
//}
