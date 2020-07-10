package com.beshoykamal.theflayingbird;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class GameOverActivity extends AppCompatActivity {

    TextView scor;
    AdView adView;
    AdRequest adRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        scor = findViewById(R.id.scor);

        String score = getIntent().getExtras().get("score").toString();
        scor.setText(score);


        /// e3lan ADS

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        adView =findViewById(R.id.adView1);
//       adView.setAdSize(AdSize.SMART_BANNER);
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);


///// e3lan
//        adView =findViewById(R.id.adView1);
////       adView.setAdSize(AdSize.SMART_BANNER);
//        adRequest = new AdRequest.Builder().build();
//
//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//                adView.loadAd(adRequest);
//            }
//        });
    }

    public void startgame(View view) {
        Intent in=new Intent(GameOverActivity.this,SplashActivity.class);
        startActivity(in);
    }
}
