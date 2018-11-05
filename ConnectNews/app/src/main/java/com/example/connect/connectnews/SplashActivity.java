package com.example.connect.connectnews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                jump(null);
            }
        },5000);
        ImageView image = (ImageView) findViewById(R.id.imageSplash);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.animation_blink);
        image.startAnimation(animation);
    }

    public void  jump (View view){
        startActivity(new Intent(SplashActivity.this,HomeActivity.class));
        finish();
    }
}
