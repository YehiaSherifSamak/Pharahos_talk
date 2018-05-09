package org.tensorflow.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by yahya on 4/24/2018.
 */

public class GuideActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_activity);
        ImageView mapImageView = (ImageView) findViewById(R.id.mapImageView);
        Intent myIntent = getIntent();
        String start = myIntent.getStringExtra("room");
        String end = myIntent.getStringExtra("target");
        if(start.equals("Entrance Hall"))
        {
            if(end.equals("nefertiti") || end.equals("ikhnaton") || end.equals("hatshepsut"))
            {
                mapImageView.setImageResource(R.drawable.entrance_senario_1);
            }
            else
            {
                Toast.makeText(GuideActivity.this,"ajhdabaskj", Toast.LENGTH_LONG).show();
            }
        }
        else if(start.equals("Exit Hall"))
        {
            if(end.equals("tutankhamun") || end.equals("sphinx") || end.equals("ramses i"))
            {
                mapImageView.setImageResource(R.drawable.exit_senario_1);
            }
            else
            {
                Toast.makeText(GuideActivity.this,"ajhdabaskj", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            if(end.equals("tutankhamun") || end.equals("sphinx") || end.equals("ramses i"))
            {
                mapImageView.setImageResource(R.drawable.corredor_senario_2);
            }
            else
            {
                mapImageView.setImageResource(R.drawable.corredor_senario_1);
            }
        }

    }
}
