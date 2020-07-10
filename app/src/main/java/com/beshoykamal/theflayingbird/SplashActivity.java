package com.beshoykamal.theflayingbird;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class SplashActivity extends AppCompatActivity {
MediaPlayer sounds ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sounds = MediaPlayer.create(SplashActivity.this, R.raw.splashsound);
        sounds.start();


        /// e3lann ads
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });



        AdView adView =findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.setAdSize(AdSize.SMART_BANNER);
        adView.loadAd(adRequest);

        Thread thread=new Thread(){
            @Override
            public void run(){
                try {
                    sleep(7000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    Intent in=new Intent(SplashActivity.this,MainActivity.class);
                    sounds.stop();
                    startActivity(in);
                }
            }
        };
        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sounds.isPlaying()) {
            sounds.stop();
        }
        finish();
    }
}
