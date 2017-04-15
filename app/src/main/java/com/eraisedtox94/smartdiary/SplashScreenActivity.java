package com.eraisedtox94.smartdiary;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import static java.lang.Thread.sleep;

/**
 * Created by spraful on 3/30/2017.
 */

public class SplashScreenActivity extends AppCompatActivity {

    private Thread tempThread;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        tempThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Log.d("log","Inside thread execution");
                } catch(Throwable e){
                    Log.d("log","exception");
                }
            }
        });
        tempThread.start();

        Intent intentToStartMainActivity = new Intent(SplashScreenActivity.this,MainActivity.class);
        finish();
        startActivity(intentToStartMainActivity);
    }
}
