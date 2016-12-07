package com.example.administrator.mediaplayer;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.mediaplayer.Base.BaseActivity;
import com.example.administrator.mediaplayer.Service.MusicService;
import com.example.administrator.mediaplayer.widget.CDView;
import com.getdirectory.FileDirActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.music_list)
    RecyclerView musicList;
    @BindView(R.id.cdView)
    CDView cdView;
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

    private String fileUrl;

    private boolean firstOut=false;
    private Handler handler=new Handler();
    private MusicService.MusicBinder binder;
    private boolean isPlay;

    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder= (MusicService.MusicBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.inflateMenu(R.menu.toolbar_menu);
//        toolbar.setTitle(R.string.app_name);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset<=-activity_main.getHeight()/2){
                    toolbarLayout.setTitle(songName.getText());
                }else {
                    toolbarLayout.setTitle("");
                }
            }
        });
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
        bindService(new Intent(this,MusicService.class),connection,BIND_AUTO_CREATE);
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
                binder.pause();
                if (isPlay){
                    play.setImageResource(R.mipmap.pause);
                    isPlay=false;
                }else {
                    play.setImageResource(R.mipmap.play);
//                    cdView.startRotate();
                    isPlay=true;
                }
            }
            break;
            case R.id.music_folder:{
                startActivityForResult(new Intent(MainActivity.this,FileDirActivity.class),101);
            }
            break;
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 101:{
                if (data!=null){
                    fileUrl=data.getStringExtra("file");
                    if (!TextUtils.isEmpty(fileUrl)){
                        binder.play(fileUrl);
                        play.setImageResource(R.mipmap.pause);
                        cdView.startRotate();
                        author.setText(fileUrl);
                        songName.setText(fileUrl.substring(fileUrl.lastIndexOf("/")+1));
                    }
                }
            }
            break;
        }
    }

}
