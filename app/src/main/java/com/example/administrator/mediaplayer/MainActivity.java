package com.example.administrator.mediaplayer;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Window;

import com.example.administrator.mediaplayer.widget.CDView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView musicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.toolbar_menu);
//        setSupportActionBar(toolbar);
        musicList= (RecyclerView) findViewById(R.id.music_list);
        CDView cdView= (CDView) findViewById(R.id.cdView);
        cdView.setPaletteAsyncListener(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                findViewById(R.id.playContainer).setBackgroundColor(palette.getVibrantSwatch().getRgb());
                if (Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){
                    Window window=getWindow();
                    window.setStatusBarColor(palette.getDarkVibrantSwatch().getRgb());
                    window.setNavigationBarColor(palette.getDarkVibrantSwatch().getRgb());
                }
            }
        });
//        cdView.setDrawableRes(R.mipmap.pic);
//        cdView.startRotate();
    }
}
