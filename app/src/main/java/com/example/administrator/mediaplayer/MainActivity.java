package com.example.administrator.mediaplayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.mediaplayer.Base.BaseActivity;
import com.example.administrator.mediaplayer.Service.MusicIntentService;
import com.example.administrator.mediaplayer.adapter.DividerItemDecoration;
import com.example.administrator.mediaplayer.adapter.MusicAdapter;
import com.example.administrator.mediaplayer.adapter.OnRecyclerItemClickListener;
import com.example.administrator.mediaplayer.bean.Music;
import com.example.administrator.mediaplayer.model.MusicDB;
import com.example.administrator.mediaplayer.widget.CDView;
import com.example.administrator.mediaplayer.widget.CircleProgressBar;
import com.getdirectory.FileDirActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.administrator.mediaplayer.commonEnum.PAUSE_STATE;
import static com.example.administrator.mediaplayer.commonEnum.PLAY_STATE;
import static com.example.administrator.mediaplayer.commonEnum.STOP_STATE;

public class MainActivity extends BaseActivity {

    @BindView(R.id.music_list)
    RecyclerView musicList;
    @BindView(R.id.cdView)
    CDView cdView;
    @BindView(R.id.circleProgress)
    CircleProgressBar circleProgress;
    @BindView(R.id.playContainer)
    View playContainer;
    @BindView(R.id.music_folder)
    ImageView music_folder;
    @BindView(R.id.appBar)
    AppBarLayout appBarLayout;
    @BindView(R.id.toolbarLayout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.activity_main)
    CoordinatorLayout activity_main;
    @BindView(R.id.play)
    FloatingActionButton play;
    @BindView(R.id.songName)
    TextView songName;
    @BindView(R.id.author)
    TextView author;
    @BindView(R.id.totalTime)
    TextView totalTime;
    @BindView(R.id.currentTime)
    TextView currentTime;

    private String fileUrl;
    private String name="";

    private boolean firstOut=false;
    private Handler handler=new Handler();

    private MusicAdapter musicAdapter;

    private @commonEnum.MUSICSTATE int state=STOP_STATE;

    private float totalProgress,currentProgress;

    public static final String COMPLETE_ACTION="com.liberty.mediaplayer.completeAction";

    public static final String TOTALPROGRESS_ACTION="com.liberty.mediaplayer.totalProgressAction";

    public static final String CURRENTPROGRESS_ACTION="com.liberty.mediaplayer.currentProgressAction";

//    private ServiceConnection connection=new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            binder= (MusicService.MusicBinder) service;
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//
//        }
//    };

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        startService(new Intent(this, MusicIntentService.class));
        IntentFilter filter=new IntentFilter();
        filter.addAction(COMPLETE_ACTION);
        filter.addAction(TOTALPROGRESS_ACTION);
        filter.addAction(CURRENTPROGRESS_ACTION);
        BroadcastReceiver receiver=new ActivityReceiver();
        registerReceiver(receiver,filter);
        final Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.inflateMenu(R.menu.toolbar_menu);
//        toolbar.setTitle(R.string.app_name);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if (verticalOffset<=-activity_main.getHeight()/2){
//                    toolbarLayout.setTitle(songName.getText());
//                }else {
//                    toolbarLayout.setTitle("");
//                }
                Log.d("xxxxx","toolbar.getY()="+toolbar.getY()+"  toolbarLayout.getHeight()="+toolbarLayout.getHeight()+"  verticalOffset="+verticalOffset
                        +"  appBarLayout.getY()="+appBarLayout.getY());
//                if (Math.abs(verticalOffset)<toolbar.getY()/2){
//                    toolbarLayout.setTitle("");
//                }else {
//                    toolbarLayout.setTitle(songName.getText());
//                }
//                if (Math.abs(verticalOffset)==toolbar.getY()){
                    toolbarLayout.setTitle(songName.getText());
//                }
            }
        });
        musicAdapter=new MusicAdapter(this);
        musicList.setAdapter(musicAdapter);
        musicList.setLayoutManager(new LinearLayoutManager(this));
        musicList.addOnItemTouchListener(new OnRecyclerItemClickListener(musicList) {
            @Override
            public void onItemClick(int position, RecyclerView.ViewHolder holder) {
                super.onItemClick(position, holder);
                Music music=musicAdapter.getItem(position);
                if (fileUrl!=null){
                    if (!fileUrl.equals(music.getUrl())){
                        play(music.getUrl());
                        state=PLAY_STATE;
                    }
                }else {
                    play(music.getUrl());
                    state=PLAY_STATE;
                }
                toolbar.setTitle(songName.getText());
            }
        });
        musicList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
//        cdView.setPaletteAsyncListener(new Palette.PaletteAsyncListener() {
//            @Override
//            public void onGenerated(Palette palette) {
//                playContainer.setBackgroundColor(palette.getVibrantSwatch().getRgb());
//                if (Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){
//                    Window window=getWindow();
//                    window.setStatusBarColor(palette.getDarkVibrantSwatch().getRgb());
//                    window.setNavigationBarColor(palette.getDarkVibrantSwatch().getRgb());
//                }
//            }
//        });
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.search:
//                    {
//
//                    }
//                    break;
//                }
//                return false;
//            }
//        });
//        cdView.setDrawableRes(R.mipmap.pic);
//        cdView.startRotate();
//        bindService(new Intent(this,MusicService.class),connection,BIND_AUTO_CREATE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (firstOut){
            finish();
        }else {
            firstOut=true;
            showToast("再按一次退出应用！");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    firstOut=false;
                }
            },2000);
        }
    }

    @OnClick({
            R.id.play,
            R.id.music_folder
    })
    public void btnClick(View v){
        switch (v.getId()){
            case R.id.play:{
                if (state==STOP_STATE){
                    if (!TextUtils.isEmpty(fileUrl)){
                        play(fileUrl);
                        state=PLAY_STATE;
                    }else {
                        if (musicAdapter.getItemCount()>0){
                            Music music=musicAdapter.getItem(0);
                            fileUrl=music.getUrl();
                            play(fileUrl);
                            state=PLAY_STATE;
                        }
                    }
                }else if (state==PLAY_STATE){
                    cdView.pauseRotate();
                    play.setImageResource(R.mipmap.play);
                    pause();
                    state=PAUSE_STATE;
                }else if (state==PAUSE_STATE){
                    cdView.resumeRotate();
                    play.setImageResource(R.mipmap.pause);
                    pause();
                    state=PLAY_STATE;
                }
            }
            break;
            case R.id.music_folder:{
                startActivityForResult(new Intent(MainActivity.this,FileDirActivity.class),101);
            }
            break;
            case R.id.prev:{

            }
            break;
            case R.id.next:{

            }
            break;
        }
    }

    private void play(String url){
        play.setImageResource(R.mipmap.pause);
        cdView.startRotate();
        fileUrl=url;
        author.setText(url);
        int lastIndex=url.lastIndexOf(".");
        String name=url.substring(url.lastIndexOf("/")+1,lastIndex);
        songName.setText(name);
        Intent intent=new Intent(MusicIntentService.PLAY_ACTION);
        intent.putExtra("url",url);
        sendBroadcast(intent);
    }

    private void pause(){
        Intent intent=new Intent(MusicIntentService.PAUSE_ACTION);
        sendBroadcast(intent);
    }

    private void stop(){
        Intent intent=new Intent(MusicIntentService.STOP_ACTION);
        sendBroadcast(intent);
    }

    @Override
    protected void initData() {
        List<Music> musics=MusicDB.getInstance(this).selectBean();
        musicAdapter.fillDataList(musics);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 101:{
                if (data!=null){
                    String url=data.getStringExtra("file");
                    if (!TextUtils.isEmpty(url)){
                        play(url);
                        state=PLAY_STATE;
                        play.setImageResource(R.mipmap.pause);
                        cdView.startRotate();
                        int lastIndex=url.lastIndexOf(".");
                        String name=url.substring(url.lastIndexOf("/")+1,lastIndex);
                        Music music=new Music();
                        music.setName(name);
                        music.setUrl(url);
                        musicAdapter.addData(music);
                        if (!MusicDB.getInstance(this).hasBean(music)){
                            MusicDB.getInstance(this).insertBean(music);
                        }
                    }
                }
            }
            break;
        }
    }

    public class ActivityReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            switch (action){
                case COMPLETE_ACTION:{
                    state=STOP_STATE;
                    cdView.stopRotate();
                    play.setImageResource(R.mipmap.play);
                }
                break;
                case TOTALPROGRESS_ACTION:{
                    totalProgress=intent.getIntExtra("totalProgress",0);
                    Log.d("xxxxx","totalProgress="+totalProgress);
                    totalTime.setText(calculateProgress(totalProgress));
                }
                break;
                case CURRENTPROGRESS_ACTION:{
                    currentProgress=intent.getIntExtra("currentProgress",0)+1;
                    Log.d("xxxxx","currentProgress="+currentProgress);
                    if (totalProgress>0){
                        float percent=currentProgress/totalProgress*100;
                        Log.d("xxxxx","percent="+percent+"  totalProgress="+totalProgress);
                        circleProgress.setCurrentProgress(percent);
                    }
                    currentTime.setText(calculateProgress((int) currentProgress));
                }
                break;
            }
        }
    }

    /**
     * 计算时间进度
     * @param progress
     * @return
     */
    private String calculateProgress(float progress){
        float dprogress=progress/1000;
        int minute= (int) (dprogress/60);
        int second= (int) (dprogress%60);
        String progressStr=Integer.toString(minute);
        if (second<10){
            progressStr=progressStr+":0"+second;
        }else {
            progressStr=progressStr+":"+second;
        }
        Log.d("xxxxx","progressStr="+progressStr);
        return progressStr;
    }

}
