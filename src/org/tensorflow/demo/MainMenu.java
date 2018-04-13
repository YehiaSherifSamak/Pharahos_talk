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
import org.tensorflow.demo.features.beaconList.BeaconListActivity;

//import com.bridou_n.beaconscanner.features.beaconList.BeaconListActivity;
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
        mainMenuImageView.setImageResource(R.drawable.pharaohs);
       /* explore=(Button)findViewById(R.id.explore);
        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainMenu.this,Info.class);
                startActivity(intent);

            }
        })*/
        gridLayout=(GridLayout)findViewById(R.id.gridlayout);
       for(int i=0;i<gridLayout.getChildCount();i++) {
            final CardView cardView = (CardView) gridLayout.getChildAt(i);

                        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        }

       setSingleEvent(gridLayout);

     //setToggleEvent(gridLayout);


    }

  /*  private void setToggleEvent(GridLayout gridLayout) {

        for(int i=0;i<gridLayout.getChildCount();i++) {
            final CardView cardView = (CardView) gridLayout.getChildAt(i);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cardView.getCardBackgroundColor().getDefaultColor()==(Color.parseColor("#FFFFFF")))
                        cardView.setCardBackgroundColor(Color.parseColor("#808080"));
                    else
                        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                }
            });
        }
    }
*/
    private void setSingleEvent(GridLayout gridLayout) {

        for(int i=0;i<gridLayout.getChildCount();i++)
        {
           final CardView cardView=(CardView)gridLayout.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(cardView.getCardBackgroundColor().getDefaultColor()==(Color.parseColor("#FFFFFF")))
                        cardView.setCardBackgroundColor(Color.parseColor("#808080"));
                    else
                        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    switch (finalI)
                    {
                        case 0:

                        startActivity(new Intent(MainMenu.this, ClassifierActivity.class));
                        break;
                        case 1:

                         startActivity(new Intent(MainMenu.this, BeaconListActivity.class));
                            break;
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
