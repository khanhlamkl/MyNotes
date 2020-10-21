package com.khanhlam.mynotes.Activity;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.khanhlam.mynotes.R;

public class SplashScreenActivity extends AppCompatActivity {

    Handler handler;
    TextView tvTitleLogo;
    ImageView imgLogo;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        init();
    }

    private void init() {



        tvTitleLogo = findViewById(R.id.tvTitleLogo);
        imgLogo = findViewById(R.id.imgLogo);
        progressBar = findViewById(R.id.progressBar);

        Animation animation = AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.effect_splash_srreen);
        progressBar.setAnimation(animation);
        tvTitleLogo.setAnimation(animation);
        imgLogo.setAnimation(animation);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.effect_activity_in, R.anim.effect_activity_out);
                finish();
            }
        },2000);
    }
}
