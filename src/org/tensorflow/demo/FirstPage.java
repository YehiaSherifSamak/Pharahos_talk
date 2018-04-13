package org.tensorflow.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

/**
 * Created by yahya on 4/7/2018.
 */

public class FirstPage extends Activity{
    private static int SPLASH_TIME_OUT=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpage);
        ImageView launcher = (ImageView) findViewById(R.id.launcherImageView);
        launcher.setImageResource(R.drawable.pharaohs);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(FirstPage.this,SignIn.class);
                startActivity(intent);
            }
        },SPLASH_TIME_OUT);
}}
