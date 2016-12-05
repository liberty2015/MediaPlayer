package com.example.administrator.mediaplayer.Base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;

/**
 * Created by acfun on 2016/12/4.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected abstract @LayoutRes int setLayoutId();

    protected abstract void initView();

    protected abstract  void initData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        @LayoutRes int layoutId=setLayoutId();
        if (layoutId!=0){
            setContentView(layoutId);
            ButterKnife.bind(this);
            initView();
            initData();
        }
    }

    public void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    public void showToast(@StringRes int resId){
        Toast.makeText(this,resId,Toast.LENGTH_SHORT).show();
    }

    public void startOtherActivity(Class newActivity){
        Intent intent=new Intent(this,newActivity);
        startActivity(intent);
    }

}
