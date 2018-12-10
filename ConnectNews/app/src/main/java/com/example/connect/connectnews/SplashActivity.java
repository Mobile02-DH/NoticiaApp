package com.example.connect.connectnews;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        printKeyHash();


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

    private void printKeyHash(){
        try{
            PackageInfo info = getPackageManager().getPackageInfo("com.example.connect.connectnews", PackageManager.GET_SIGNATURES);
            for (Signature signature:info.signatures){

                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }
}
