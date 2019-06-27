package com.homerent.bd;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_splash_screen);
        overridePendingTransition(R.anim.slide_left,R.anim.slide_right);

        Thread background = new Thread() {
            public void run() {

                try {
                    sleep(3000);
                    Intent i=new Intent(getBaseContext(),MainActivity.class);
                    startActivity(i);
                    finish();
                } catch (Exception e) {

                }
            }
        };
        background.start();

        ImageView img = (ImageView) findViewById(R.id.lloggo);

        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        img.setAnimation(myanim);

    }
}