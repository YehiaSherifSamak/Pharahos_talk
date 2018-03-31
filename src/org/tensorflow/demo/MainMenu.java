package org.tensorflow.demo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.TimeUnit;



public class MainMenu extends Activity {

    Button explore;
    GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ImageView mainMenuImageView = (ImageView) findViewById(R.id.mainMenuImageView);
        mainMenuImageView.setImageResource(R.drawable.photo1);
       /* explore=(Button)findViewById(R.id.explore);
        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainMenu.this,Info.class);
                startActivity(intent);

            }
        })*/

       gridLayout=(GridLayout)findViewById(R.id.gridlayout);
       setSingleEvent(gridLayout);


    }

    private void setSingleEvent(GridLayout gridLayout) {

        for(int i=0;i<gridLayout.getChildCount();i++)
        {
            CardView cardView=(CardView)gridLayout.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(finalI ==0) {
                        Intent intent = new Intent(MainMenu.this, ClassifierActivity.class);
                        startActivity(intent);
                    }
                }
            });

        }
    }
    @Override
    public void onBackPressed()
    {
        FirebaseAuth.getInstance().signOut();
        Intent backIntent = new Intent(MainMenu.this, SignIn.class);
        startActivity(backIntent);
    }
}