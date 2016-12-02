package com.example.administrator.mediaplayer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.mediaplayer.widget.CDView;

/**
 * Created by acfun on 2016/12/1.
 */

public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        CDView cdView= (CDView) findViewById(R.id.cdView);
        cdView.setDrawableRes(R.mipmap.pic);
        cdView.startRotate();
    }
}
