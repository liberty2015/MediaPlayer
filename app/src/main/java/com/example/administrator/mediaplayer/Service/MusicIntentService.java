package com.example.administrator.mediaplayer.Service;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.util.Log;

import com.example.administrator.mediaplayer.MainActivity;
import com.example.administrator.mediaplayer.commonEnum;

import java.io.IOException;

import static com.example.administrator.mediaplayer.commonEnum.STOP_STATE;

/**
 * Created by acfun on 2016/12/9.
 */

public class MusicIntentService extends IntentService
        implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener{

    private MediaPlayer mediaPlayer;
    private int currentProgress=0;
    private int totalProgress=0;

    private @commonEnum.MUSICSTATE int state=STOP_STATE;

    public static final String PLAY_ACTION="com.liberty.mediaplayer.PlayAction";
    public static final String PAUSE_ACTION="com.liberty.mediaplayer.PauseAction";
    public static final String STOP_ACTION="com.liberty.mediaplayer.StopAction";

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
        Log.d("xxxxx","-----onCreate-----");
        initMediaPlayer();
        IntentFilter filter=new IntentFilter();
        ServiceReceiver receiver=new ServiceReceiver();
        filter.addAction(PLAY_ACTION);
        filter.addAction(PAUSE_ACTION);
        filter.addAction(STOP_ACTION);
        registerReceiver(receiver,filter);
    }

    private static final String TAG="MusicIntentService";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MusicIntentService() {
        super(TAG);
    }

//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        Log.d("xxxxx","-----onCreate-----");
//        return super.onStartCommand(intent, flags, startId);
//    }

    @Override
    protected void onHandleIntent(Intent intent) {
//        synchronized (this){
        Log.d("xxxxx","-----onHandleIntent-----");
        try {
                while (true){
                    Intent data=new Intent();
                    data.setAction(MainActivity.CURRENTPROGRESS_ACTION);
                    data.putExtra("currentProgress",mediaPlayer.getCurrentPosition());
                    sendBroadcast(data);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        Intent intent=new Intent(MainActivity.COMPLETE_ACTION);
        sendBroadcast(intent);
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {

        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
        Intent intent=new Intent();
        intent.setAction(MainActivity.TOTALPROGRESS_ACTION);
        intent.putExtra("totalProgress",mediaPlayer.getDuration());
        sendBroadcast(intent);
        Log.d("xxxxx","mediaPlayer.getDuration()="+mediaPlayer.getDuration());
    }

    private void play(String url){
        mediaPlayer.reset();
        state=commonEnum.PLAY_STATE;
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pause(){
        state=commonEnum.PAUSE_STATE;
        if (mediaPlayer.isPlaying()){
            currentProgress=mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
        }else {
            mediaPlayer.seekTo(currentProgress);
            mediaPlayer.start();
        }
    }

    public void stop(){
        state=commonEnum.STOP_STATE;
        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer=null;
    }

    public class ServiceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            switch (action){
                case PLAY_ACTION:{
                    String url=intent.getStringExtra("url");
                    if (!TextUtils.isEmpty(url)){
                        play(url);
                    }
                }
                break;
                case PAUSE_ACTION:{
                    pause();
                }
                break;
                case STOP_ACTION:{
                    stop();
                }
                break;
            }
        }
    }
}
