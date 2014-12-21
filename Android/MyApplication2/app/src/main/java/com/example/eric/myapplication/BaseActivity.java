package com.example.eric.myapplication;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import debug.LogManager;

/**
 * Created by Eric on 2014/12/20.
 */
public abstract class BaseActivity extends ActionBarActivity {
    protected LogManager log = null;
    protected boolean isLogEnable = true;

    protected abstract void initUIComponent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.log = LogManager.getLogger(this.getClass(), this.isLogEnable);
        log.d("onCreate() -----------------");
    }

    @Override
    protected void onStart() {
        log.d("onStart() -----------------");
        super.onStart();
    }

    @Override
    protected void onResume() {
        log.d("onResume()");
        super.onResume();
    }

    @Override
    protected void onPause() {
        log.d("onPause() -----------------");
        super.onResume();
    }

    @Override
    protected void onStop() {
        log.d("onStop() -----------------");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        log.d("onDestroy() -----------------");
        super.onDestroy();
    }


}
